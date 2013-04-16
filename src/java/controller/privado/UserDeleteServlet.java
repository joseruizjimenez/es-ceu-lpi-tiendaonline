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
import persistence.user.UserDAO;
import persistence.user.UserPersistFactory;

/**
 * Borra la cuenta del usuario
 */
@WebServlet(name="UserDeleteServlet")
public class UserDeleteServlet extends BasicUtilitiesServlet {
    Logger logger = Logger.getLogger(UserDeleteServlet.class.getName());
   
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO userDAO = UserPersistFactory.getUserDAO(persistenceMechanism);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(userDAO.deleteUser(user.getIdAsString())) {
            session.setAttribute("authenticated","false");
            session.setAttribute("user",null);
            request.setAttribute("info","Su cuenta ha sido borrada con exito :-(");
            logger.info("Eliminada la cuenta de usuario: "+user.getIdAsString());
            gotoURL(userDeleted,request,response);
        } else {
            request.setAttribute("info","Algo falla en nuestro sistema! Intentalo mas tarde...");
            logger.warn("Error borrando la cuenta de usuario: "+user.getIdAsString());
            gotoURL(userSettings,request,response);
        }
    }
        
}