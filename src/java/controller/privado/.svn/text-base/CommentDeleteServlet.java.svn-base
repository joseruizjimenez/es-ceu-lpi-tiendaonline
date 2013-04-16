package controller.privado;

import controller.BasicUtilitiesServlet;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Catalog;
import model.Comment;
import model.User;
import org.apache.log4j.Logger;

/**
 * Borra un comentario ya existente, siendo su creador el usuario logueado
 */
@WebServlet(name="CommentDeleteServlet")
public class CommentDeleteServlet extends BasicUtilitiesServlet {
    Logger logger = Logger.getLogger(CommentDeleteServlet.class.getName());
   
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        Catalog catalog = (Catalog) context.getAttribute("catalog");
        String admin = (String) session.getAttribute("admin");
        User user = (User) session.getAttribute("user");
        String id = request.getParameter("id");
        String userNick = "";
        if(user != null) {
            userNick = user.getNick();
        }
        if(id!=null && catalog.getComment(id)!=null) {
            Comment comment = catalog.getComment(id);
            if((comment.getNickname().equals(userNick) || "true".equals(admin))
                    && catalog.removeComment(id)) {
                request.setAttribute("info","¡El comentario ha sido borrado con exito!");
                logger.info("Borrado el comentario: "+id+" del disco "+comment.getRecordIdAsString());
                request.setAttribute("commented",comment.getRecordIdAsString());
                gotoNamedResource(recordInfoServlet,request,response);
            } else {
                request.setAttribute("info","Algo falla en nuestro sistema! Intentalo mas tarde...");
                logger.warn("Error borrando el comentario: "+id+" del disco "+comment.getRecordIdAsString());
                String recordUrl = request.getRequestURI()+"?to=product&id="+comment.getRecordIdAsString();
                gotoURL(recordUrl,request,response);
            }
        } else {
            gotoURL(frontPage,request,response);
        }
    }
        
}