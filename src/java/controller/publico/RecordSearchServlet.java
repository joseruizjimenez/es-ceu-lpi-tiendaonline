package controller.publico;

import controller.BasicUtilitiesServlet;
import java.io.IOException;
import java.util.ArrayList;
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
 * Busca los discos que coinciden con los campos suministrados
 */
@WebServlet(name="RecordSearchServlet")
public class RecordSearchServlet extends BasicUtilitiesServlet {
    Logger logger = Logger.getLogger(RecordSearchServlet.class.getName());
   
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {                           
        ArrayList<Record> recordList = generateRecordListFromRequest(request);
        if(recordList.isEmpty()) {
            request.setAttribute("info","No se han encontrado resultados para su busqueda");
        } else {
            request.setAttribute("info","Se han encontrado: "+recordList.size()+" coincidencias");
        }
        request.setAttribute("list",recordList);
        gotoURL(recordSearch,request,response);
    }
    
    /**
     * Genera un listado resultado de la busqueda empleando los parametros de request
     * @param request con el formulario
     * @return recordList generada
     */
    private ArrayList generateRecordListFromRequest(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        Catalog catalog = (Catalog) context.getAttribute("catalog");
        
        String name = request.getParameter("name");
        if(!validateFreeText(name,1,30)) {
            name = null;
        }
        String artist = request.getParameter("artist");
        if(!validateFreeText(artist,1,30)) {
            artist = null;
        }
        String recordLabel = request.getParameter("recordLabel");
        if(!validateFreeText(recordLabel,1,20)) {
            recordLabel = null;
        }
        String type = request.getParameter("type");
        if(!validateAlphaNumeric(type,1,15)) {
            type = null;
        }
        
        return catalog.searchRecord(name, artist, recordLabel, type);
    }

}