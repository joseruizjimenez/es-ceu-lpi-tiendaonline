package controller.admin;

import controller.BasicUtilitiesServlet;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Sale;
import org.apache.log4j.Logger;
import persistence.sale.SaleDAO;
import persistence.sale.SalePersistFactory;

/**
 * Busca las ventas que coinciden con los campos suministrados
 */
@WebServlet(name="SalesSearchServlet")
public class SalesSearchServlet extends BasicUtilitiesServlet {
    Logger logger = Logger.getLogger(SalesSearchServlet.class.getName());
   
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {                           
        ArrayList<Sale> saleList = generateSaleListFromRequest(request);
        if(saleList.isEmpty()) {
            request.setAttribute("info","No se han encontrado resultados para su busqueda");
        } else {
            request.setAttribute("info","Se han encontrado: "+saleList.size()+" coincidencias");
        }
        request.setAttribute("list",saleList);
        gotoURL(adminSalesSearch,request,response);
    }
    
    /**
     * Genera un listado resultado de la busqueda empleando los parametros de request
     * @param request con el formulario
     * @return salesList generada
     */
    private ArrayList generateSaleListFromRequest(HttpServletRequest request) {
        SaleDAO saleDAO = SalePersistFactory.getSaleDAO(persistenceMechanism);
        
        String id = request.getParameter("id");
        if(!validateFreeText(id,36,36)) {
            id = null;
        }
        String customerId = request.getParameter("customerId");
        if(!validateFreeText(customerId,36,36)) {
            customerId = null;
        }
        String customerName = request.getParameter("customerName");
        if(!validateName(customerName,1,50)) {
            customerName = null;
        }
        String address = request.getParameter("address");
        if(!validateFreeText(address,1,100)) {
            address = null;
        }
        String paymentForm = request.getParameter("paymentForm");
        if(!validateFreeText(paymentForm,1,20)) {
            paymentForm = null;
        }
        String cartFootprint = request.getParameter("cartFootprint");
        if(!validateFreeText(cartFootprint,1,410)) {
            cartFootprint = null;
        }
        String recordId = request.getParameter("recordId");
        if(!validateFreeText(recordId,36,36)) {
            recordId = null;
        }
        
        return saleDAO.listSale(customerId, customerName, address, paymentForm, cartFootprint, recordId);
    }

}