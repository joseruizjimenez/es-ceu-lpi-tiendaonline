package controller.privado;

import controller.BasicUtilitiesServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;
import org.apache.log4j.Logger;

/**
 * Servlet para desvincular una cuenta de usuario de una sesion. Logout.
 */
@WebServlet(name="UserLogoutServlet")
public class UserLogoutServlet extends BasicUtilitiesServlet {
    Logger logger = Logger.getLogger(UserLogoutServlet.class.getName());
   
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String id = user.getIdAsString();
        session.setAttribute("authenticated","false");
        session.setAttribute("user",null);
        logger.info("Deslogueado el usuario: "+id+" desde "+request.getRemoteAddr());
        request.setAttribute("loginError","Has salido de tu cuenta");
        gotoURL(frontPage,request,response);      
    }

}