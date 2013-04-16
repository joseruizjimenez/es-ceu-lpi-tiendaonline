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
import org.apache.log4j.Logger;

/**
 * Crea un nuevo comentario, anhadiendolo a la persistencia
 * Antes de insertarlo se comprobara que no contiene ningun Script oculto
 */
@WebServlet(name="CommentCreateServlet")
public class CommentCreateServlet extends BasicUtilitiesServlet {
    Logger logger = Logger.getLogger(CommentCreateServlet.class.getName());
   
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (isCommentFormSanitized(request) && isStringXSSSecured(request.getParameter("comment"))) {
            HttpSession session = request.getSession();
            ServletContext context = session.getServletContext();
            Catalog catalog = (Catalog) context.getAttribute("catalog");
            Comment comment = generateCommentFromRequest(request);
            if(catalog.addComment(comment)) {
                logger.info("Comentado el articulo: "+comment.getRecordIdAsString()+" por: "+comment.getNickname());
                request.setAttribute("info","Comentario insertado con exito!");
                request.setAttribute("commented",comment.getRecordIdAsString());
                gotoNamedResource(recordInfoServlet,request,response);
            } else {
                request.setAttribute("info","Algo falla en nuestro sistema! Intentalo mas tarde...");
                logger.warn("Error insertando un nuevo comentario: "+comment.getIdAsString());
            }
        } else {
            gotoURL(frontPage,request,response);     
        }
    }
    
    /**
     * Comprueba todos los campos del formulario en busca de caracteres
     * no validos. Ademas comprueba que los tamanyos sean  correctos
     * @param request formulario
     * @return true si correcto, false en caso contrario
     */
    private boolean isCommentFormSanitized(HttpServletRequest request) {
        String recordId = request.getParameter("id");
        if(!validateFreeText(recordId,36,36)) {
            recordId = null;
        }
        String nick = request.getParameter("nick");
        if(!validateAlphaNumeric(nick,1,15)) {
            request.setAttribute("info",
                "El nick solo puede contener maximo 15 caracteres alfanumericos");
            return false;
        }
        String fullComment = request.getParameter("comment");
        if(!validateFreeText(fullComment,1,500)) {
            request.setAttribute("info",
                "El comentario tiene de maximo 500 caracteres");
            return false;
        }
        return true;
    }
   
    /**
     * Genera un comentario a partir de un formulario con parametros validos
     * @param request con el formulario
     * @return comment generado
     */
    private Comment generateCommentFromRequest(HttpServletRequest request) {
        String recordId = request.getParameter("id");
        String nick = request.getParameter("nick");
        String fullComment = request.getParameter("comment");
        
        return new Comment(recordId,nick,fullComment);
    }

}