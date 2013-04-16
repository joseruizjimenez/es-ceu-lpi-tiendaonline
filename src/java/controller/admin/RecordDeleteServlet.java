package controller.admin;

import controller.BasicUtilitiesServlet;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Catalog;
import org.apache.log4j.Logger;

/**
 * Borra un disco ya existente
 */
@WebServlet(name="RecordDeleteServlet")
public class RecordDeleteServlet extends BasicUtilitiesServlet {
    Logger logger = Logger.getLogger(RecordDeleteServlet.class.getName());
   
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        Catalog catalog = (Catalog) context.getAttribute("catalog");
        String id = request.getParameter("id");
        if(id!=null && catalog.getRecord(id)!=null && catalog.removeRecord(id)) {
            request.setAttribute("info","¡El record: "+id+"ha sido borrado con exito!");
            logger.info("Borrado el disco: "+id);
            gotoURL(adminSettings,request,response);
        } else {
            request.setAttribute("info","Algo falla en nuestro sistema! Intentalo mas tarde...");
            logger.warn("Error borrando el disco: "+id);
            gotoURL(adminSettings,request,response);
        }
    }
        
}