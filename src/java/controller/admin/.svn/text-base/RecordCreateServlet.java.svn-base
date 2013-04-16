package controller.admin;

import controller.BasicUtilitiesServlet;
import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Catalog;
import model.Record;
import org.apache.log4j.Logger;

/**
 * Crea un nuevo disco, anhadiendolo a la persistencia
 */
@WebServlet(name="RecordCreateServlet")
public class RecordCreateServlet extends BasicUtilitiesServlet {
    Logger logger = Logger.getLogger(RecordCreateServlet.class.getName());
   
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (isRecordFormSanitized(request)) {
            HttpSession session = request.getSession();
            ServletContext context = session.getServletContext();
            Catalog catalog = (Catalog) context.getAttribute("catalog");
            Record record = generateRecordFromRequest(request);
            int stock = Integer.parseInt(request.getParameter("stock").trim());
            if(catalog.addRecord(record,stock)) {
                request.setAttribute("info","¡El record: "+record.getName()+" ("+stock+" unidades) se ha insertado con exito!");
                logger.info("Creado el disco: "+record.getIdAsString()+" ("+stock+" unidades)");
                gotoURL(adminSettings,request,response);
            } else {
                request.setAttribute("registerError","Algo falla en nuestro sistema! Intentalo mas tarde...");
                logger.warn("Error insertando un nuevo disco: "+record.getIdAsString());
            }
        } else {
            gotoURL(adminRecordForm,request,response);
        }
    }
    
    /**
     * Comprueba todos los campos del formulario en busca de caracteres
     * no validos. Ademas comprueba que los tamanyos sean  correctos
     * @param request formulario
     * @return true si correcto, false en caso contrario
     */
    private boolean isRecordFormSanitized(HttpServletRequest request) {
        String name = request.getParameter("name");
        if(!validateFreeText(name,1,30)) {
            request.setAttribute("registerError",
                "El nombre solo puede contener maximo 30 caracteres");
            return false;
        }
        String artist = request.getParameter("artist");
        if(!validateFreeText(artist,1,30)) {
            request.setAttribute("registerError",
                "El artista solo puede contener maximo 30");
            return false;
        }
        String recordLabel = request.getParameter("recordLabel");
        if(!validateFreeText(recordLabel,1,20)) {
            request.setAttribute("registerError",
                "La discografica solo puede contener maximo 20");
            return false;
        }
        String shortComment = request.getParameter("shortComment");
        if(!validateFreeText(shortComment,1,140)) {
            request.setAttribute("registerError",
                "La descripcion corta tiene de maximo 140 caracteres");
            return false;
        }
        String fullComment = request.getParameter("fullComment");
        if(!validateFreeText(fullComment,1,300)) {
            request.setAttribute("registerError",
                "La descripcion larga tiene de maximo 300 caracteres");
            return false;
        }
        String type = request.getParameter("type");
        if(!validateAlphaNumeric(type,1,15)) {
            request.setAttribute("registerError",
                "La categoria musical tiene maximo 15 caracteres alfanumericos");
            return false;
        }
        String price = request.getParameter("price");
        String stock = request.getParameter("stock");
        if(price != null && stock != null) {
            price = price.trim();
            try {
                Double.parseDouble(price);
                Integer.parseInt(stock);
            } catch(NumberFormatException ex) {
                request.setAttribute("registerError",
                    "Revise el precio y el stock, no son valores validos");
                return false;
            }
        } else {
            request.setAttribute("registerError",
                "Revise el precio y el stock, ha de darles valor");
            return false;
        }
        return true;
    }
    
    /**
     * Genera un disco a partir de un formulario con parametros validos
     * @param request con el formulario
     * @return record generado
     */
    private Record generateRecordFromRequest(HttpServletRequest request) {
        String name = request.getParameter("name");
        String artist = request.getParameter("artist");
        String recordLabel = request.getParameter("recordLabel");
        String shortComment = request.getParameter("shortComment");
        String fullComment = request.getParameter("fullComment");
        String type = request.getParameter("type");
        String priceString = request.getParameter("price").trim();
        BigDecimal price = new BigDecimal(priceString);
        
        return new Record(name,artist,recordLabel,shortComment,fullComment,type,price);
    }

}