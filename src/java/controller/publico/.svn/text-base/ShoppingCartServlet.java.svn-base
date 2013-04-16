package controller.publico;

import controller.BasicUtilitiesServlet;
import java.io.IOException;
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
import org.apache.log4j.Logger;

/**
 * Inserta o Elimina productos del carro de compra de la sesion, ademas
 * envia el nuevo carro actualizado en forma de cookie al usuario
 */
@WebServlet(name="ShoppingCartServlet")
public class ShoppingCartServlet extends BasicUtilitiesServlet {
    Logger logger = Logger.getLogger(ShoppingCartServlet.class.getName());
   
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if(!isShoppingCartFormSanitized(request)) {
            request.setAttribute("cartTotal",getShoppingCartTotal(cart,request));
            gotoURL(shoppingCart,request,response);
        } else {
            Cart updatedCart = generateUpdatedShoppingCart(cart,request);
            if(updatedCart == null) {
                request.setAttribute("info","No se ha podido agregar el articulo al carro");
                request.setAttribute("cartTotal",getShoppingCartTotal(cart,request));
                gotoURL(shoppingCart,request,response);
            }  else {       
                session.setAttribute("cart",updatedCart);
                Cookie cartCookie = new Cookie("cart",updatedCart.getFootprint());
                response.addCookie(cartCookie);
                request.setAttribute("info","Carro actualizado con exito!");
                request.setAttribute("cartTotal",getShoppingCartTotal(updatedCart,request));
                logger.trace("Anhadido producto: "+request.getParameter("id")+" al carro del usuario"
                        +"con IP "+request.getRemoteAddr());
                gotoURL(shoppingCart,request,response);
            }
        }
    }
    
    /**
     * Comprueba todos los campos del formulario en busca de caracteres
     * no validos. Ademas comprueba que los tamanyos sean  correctos
     * @param request formulario
     * @return true si correcto, false en caso contrario
     */
    private boolean isShoppingCartFormSanitized(HttpServletRequest request) {
        String id = request.getParameter("id");
        if(!validateFreeText(id,36,36)) {System.out.println(id.length());
            return false;
        }
        String quantityString = request.getParameter("n");
        if(quantityString != null) {
            quantityString = quantityString.trim();
            try {
                Integer.parseInt(quantityString);
            } catch(NumberFormatException ex) {
                //esto no es muy elegante!
                return false;
            }
        }
        return true;
    }
    
    /**
     * Actualiza el carro con la peticion del usuario
     * @param request con el formulario
     * @return carro actualizado, o null si no se puede realizar la operacion
     */
    private Cart generateUpdatedShoppingCart(Cart cart, HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        Catalog catalog = (Catalog) context.getAttribute("catalog");
        Cart updatedCart = cart;
        String id = request.getParameter("id");
        String quantityString = request.getParameter("n").trim();
        int quantity = Integer.parseInt(quantityString);
        if(catalog.getRecord(id) != null) {
            updatedCart.deleteRecord(UUID.fromString(id));
            updatedCart.addRecord(UUID.fromString(id),quantity);
            return updatedCart;
        }
        return null;
    }
    
    /**
     * Devuelve el costo total de todos los productos del carro
     * @param cart
     * @param request
     * @return 
     */
    private String getShoppingCartTotal(Cart cart, HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        Catalog catalog = (Catalog) context.getAttribute("catalog");
        return catalog.getTotalPriceAsFormatedString(cart.getCart());
    }

}