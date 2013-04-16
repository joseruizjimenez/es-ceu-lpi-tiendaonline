package controller.publico;

import controller.BasicUtilitiesServlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Cart;
import model.Catalog;
import model.Record;
import model.Sale;
import model.User;
import org.apache.log4j.Logger;
import persistence.sale.SaleDAO;
import persistence.sale.SalePersistFactory;

/**
 * Gestiona la venta de los productos alojados en el carro de la compra.
 * Comprobara si existen existencias, reservara estos mientras realiza los tramites.
 * Se generara una factura de venta (Sale) si todo ha ido correctamente.
 * El carro de sesion y de la cookie del usuario se vaciaran.
 */
@WebServlet(name="CheckoutCartServlet")
public class CheckoutCartServlet extends BasicUtilitiesServlet {
    Logger logger = Logger.getLogger(CheckoutCartServlet.class.getName());
   
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {                           
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart == null || cart.isEmpty()) {
            gotoURL(frontPage,request,response);
        }
        
        SaleDAO saleDAO = SalePersistFactory.getSaleDAO(persistenceMechanism);
        String authenticated = (String) session.getAttribute("authenticated");
        Sale saleOK = null;
        if("true".equals(authenticated)) {
            
            User user = (User) session.getAttribute("user");
            saleOK = checkoutCartFromUser(user,cart,request);
        } else {
            saleOK = checkoutCartFromForm(cart,request);
        }
        if(saleOK != null) {
            if(saleDAO.createSale(saleOK)) {
                logger.info("Venta: "+saleOK.getIdAsString()+" por: "+saleOK.getCustomerName());
                session.setAttribute("emailBill",generateBillAsString(saleOK,request));
                cart.clear();
                session.setAttribute("cart",cart);
                response.addCookie(new Cookie("cart",""));
                request.setAttribute("sale",saleOK);
                gotoURL(checkoutSuccess,request,response);
            } else {
                request.setAttribute("info","Algo falla en nuestro sistema! Intentalo mas tarde...");
                logger.warn("Error persistiendo una nueva venta empleando "+persistenceMechanism);
                gotoURL(checkoutError,request,response);
            }
        } else {
            gotoURL(checkoutLogin,request,response);
        }        
    }
    
    /**
     * Si se han podido retirar los productos y cobrar por ellos, del usuario
     * se genera una factura (sale), sino esta sera null
     * @param user cliente del carro
     * @param cart carro con los productos
     * @param request peticion para especificar errores en al venta
     * @return sale (factura) si ha sido un exito, sino null
     */
    private Sale checkoutCartFromUser(User user, Cart cart, HttpServletRequest request) {
        Sale saleOK = null;        
        if(checkoutCart(cart,request)) {
            HttpSession session = request.getSession();
            ServletContext context = session.getServletContext();
            Catalog catalog = (Catalog) context.getAttribute("catalog");
            saleOK = new Sale(user.getIdAsString(),user.getFirstName()+" "+user.getLastName(),
                user.getAddress(),user.getPaymentForm(),catalog.getTotalPrice(cart.getCart()),
                cart);
        }
        return saleOK;
    }
    
    /**
     * Si se han podido retirar los productos y cobrar por ellos, del formulario
     * se genera una factura (sale), sino esta sera null.
     * Nota que el ID de usuario generado para estos sera el UUID nulo
     * @param cart carro con los productos
     * @param request peticion para especificar errores en al venta
     * @return sale (factura) si ha sido un exito, sino null
     */
    private Sale checkoutCartFromForm(Cart cart, HttpServletRequest request) {
        Sale saleOK = null;        
        if(checkoutCart(cart,request)) {
            String customerName = request.getParameter("customerName");
            if(!validateName(customerName,1,50)) {
                request.setAttribute("info","Su nombre solo puede contener maximo"
                        + "50 caracteres alfabeticos");
                return null;
            }
            String address = request.getParameter("address");
            if(!validateFreeText(address,1,100)) {
                request.setAttribute("info","La direccion solo puede contener maximo"
                        + "100 caracteres");
                return null;
            }   
            String paymentForm = request.getParameter("paymentForm");
            if(!validateFreeText(paymentForm,1,20)) {
                return null;
            }
            
            HttpSession session = request.getSession();
            ServletContext context = session.getServletContext();
            Catalog catalog = (Catalog) context.getAttribute("catalog");
            saleOK = new Sale("00000000-0000-0000-0000-000000000000",customerName,
                address,paymentForm,catalog.getTotalPrice(cart.getCart()),cart);
        }
        return saleOK;
    }
    
    /**
     * Va retirando productos del stock de la tienda hasta completar la venta.
     * Si hay falta de stock de algun producto se repondran los productos retirados.
     * 
     * Aqui se efectuaria un posible pago si fuese implementado.
     * 
     * @param cart carro con los productos
     * @param request peticion para especificar errores en al venta
     * @return true si ha podido retirar todos los productos, false en caso contrario
     */
    private boolean checkoutCart(Cart cart, HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        Catalog catalog = (Catalog) context.getAttribute("catalog");
        HashMap<UUID,Integer> itemsToDecrement = cart.getCart();
        boolean checkoutOK = true;
        for(UUID id : itemsToDecrement.keySet()) {          
            if(checkoutOK && catalog.decrementStock(id,itemsToDecrement.get(id))) {
                itemsToDecrement.put(id,0);
            } else {
                checkoutOK = false;
                request.setAttribute("info","Lo sentimos, solo disponemos de "+
                        catalog.getStock(id)+" unidades de: "+catalog.getRecord(id).getName());
            }
        }
        if(!checkoutOK) {
            HashMap<UUID,Integer> originalCart = cart.getCart();
            for(UUID id : itemsToDecrement.keySet()) {
                if(itemsToDecrement.get(id) == 0) {
                    catalog.incrementStock(id,originalCart.get(id));
                }
            }
        } // else cobrarDeLaFormaDePago();
        
        return checkoutOK;
    }

    private String generateBillAsString(Sale sale, HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        Catalog catalog = (Catalog) context.getAttribute("catalog");
        StringBuilder bill = new StringBuilder("");
        bill.append("Factura de compra: ");
        bill.append(sale.getIdAsString());
        bill.append("\n");
        bill.append("A nombre de: ");
        bill.append(sale.getCustomerName());
        bill.append("\n");
        bill.append("Fecha: ");
        bill.append(sale.getTransactionDateAsString());
        bill.append("\n");
        bill.append("Forma de pago: ");
        bill.append(sale.getPaymentForm());
        bill.append("\n");
        bill.append("Productos adquiridos: \n");
        for(UUID recordId : sale.getItems().getCart().keySet()) {
            Record record = catalog.getRecord(recordId);
            bill.append(record.getName());
            bill.append(" (");
            bill.append(record.getArtist());
            bill.append(") - Cantidad: ");
            bill.append(sale.getItems().getCart().get(recordId));
            bill.append(" -> ");
            bill.append(record.getPriceAsFormattedString());
            bill.append("\n");
        }
        bill.append("Direccion de envio: \n");
        bill.append(sale.getAddress());
        bill.append("\n");
        bill.append("Total: ");
        bill.append(sale.getTotalAsFormattedString());
        
        return bill.toString();
    }

}