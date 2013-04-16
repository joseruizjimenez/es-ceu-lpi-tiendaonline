package controller.publico;

import controller.BasicUtilitiesServlet;
import java.io.IOException;
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
 * Busca el disco dado un id en la peticion, y lo pasa como atributo de esta
 */
@WebServlet(name="RecordInfoServlet")
public class RecordInfoServlet extends BasicUtilitiesServlet {
    Logger logger = Logger.getLogger(RecordInfoServlet.class.getName());
   
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {                           
        Record record = generateRecordFromRequest(request);
        if(record == null) {
            request.setAttribute("info","El disco solicitado no existe!");
        } else {
            logger.trace("Solicitado el disco: "+record.getIdAsString());
        }
        request.setAttribute("record",record);
        gotoURL(recordInfo,request,response);
    }
    
    /**
     * Busca el disco dado un id en la peticion
     * @param request con el formulario
     * @return record generado
     */
    private Record generateRecordFromRequest(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        Catalog catalog = (Catalog) context.getAttribute("catalog");
        
        String recordId = (String) request.getAttribute("commented");
        if(recordId == null) {
            recordId = request.getParameter("id");
        }
        if(!validateFreeText(recordId,36,36)) {
            return null;
        }
        
        return catalog.getRecord(recordId);
    }

}