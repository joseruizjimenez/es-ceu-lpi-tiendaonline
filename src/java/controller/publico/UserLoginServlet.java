package controller.publico;

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
 * Comprueba si el usuario tiene cuenta en la base de datos, y si es asi
 * carga sus datos en la sesion. Lo loguea.
 */
@WebServlet(name="UserLoginServlet")
public class UserLoginServlet extends BasicUtilitiesServlet {
    Logger logger = Logger.getLogger(UserLoginServlet.class.getName());
   
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = getUserFromRequest(request);
        if(user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("authenticated","true");
            session.setAttribute("user",user);
            request.setAttribute("info","Logueado con exito!");
            logger.info("Logueado el usuario: "+user.getIdAsString()+" desde "+request.getRemoteAddr());
        } else {
            request.setAttribute("loginError","Usuario/Pass no validos");
            request.setAttribute("info","Usuario/Pass no validos");
        }
        String from = request.getParameter("from");
        if("login".equals(from)) {
            if(user != null) {
                gotoURL(userSettings,request,response);
            } else {
                gotoURL(userLogin,request,response);
            }
        } else if("checkout".equals(from)) {
            gotoURL(checkoutLogin,request,response);
        } else {
            gotoURL(frontPage,request,response); 
        }      
    }

    private User getUserFromRequest(HttpServletRequest request) {
        User userFound = null;
        UserDAO userDAO = UserPersistFactory.getUserDAO(persistenceMechanism);
        String nick = request.getParameter("user");
        String pass = request.getParameter("pass");
        if(nick != null && pass != null) {
            User userNickMatched = userDAO.readUserByNick(nick);
            if(userNickMatched != null && userNickMatched.getPassword().equals(pass))
                userFound = userNickMatched;
        }       
        return userFound;
    }

}