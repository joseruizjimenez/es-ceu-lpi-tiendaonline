package controller.admin;

import controller.BasicUtilitiesServlet;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import org.apache.log4j.Logger;
import persistence.user.UserDAO;
import persistence.user.UserPersistFactory;

/**
 * Busca los usuarios que coinciden con los campos suministrados
 */
@WebServlet(name="UserSearchServlet")
public class UserSearchServlet extends BasicUtilitiesServlet {
    Logger logger = Logger.getLogger(UserSearchServlet.class.getName());
   
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {                           
        ArrayList<User> userList = generateUserListFromRequest(request);
        if(userList.isEmpty()) {
            request.setAttribute("info","No se han encontrado resultados para su busqueda");
        } else {
            request.setAttribute("info","Se han encontrado: "+userList.size()+" coincidencias");
        }
        request.setAttribute("list",userList);
        gotoURL(adminUserSearch,request,response);
    }
    
    /**
     * Genera un listado resultado de la busqueda empleando los parametros de request
     * @param request con el formulario
     * @return userList generada
     */
    private ArrayList generateUserListFromRequest(HttpServletRequest request) {
        UserDAO userDAO = UserPersistFactory.getUserDAO(persistenceMechanism);
        
        String nick = request.getParameter("nick");
        if(!validateAlphaNumeric(nick,1,15)) {
            nick = null;
        }
        String firstName = request.getParameter("firstName");
        if(!validateName(firstName,1,15)) {
            firstName = null;
        }
        String lastName = request.getParameter("lastName");
        if(!validateName(lastName,1,35)) {
            lastName = null;
        }
        String address = request.getParameter("address");
        if(!validateFreeText(address,1,100)) {
            address = null;
        }
        String email = request.getParameter("email");
        if(!validateEmail(email)) {
            email = null;
        }
        int day = 0, month = 0, year = 0;
        String birthDay = request.getParameter("birthDay");
        if(birthDay != null)
            birthDay = birthDay.trim();
        String birthMonth = request.getParameter("birthMonth");
        if(birthMonth != null)
            birthMonth = birthMonth.trim();
        String birthYear = request.getParameter("birthYear");
        if(birthYear != null)
            birthYear = birthYear.trim();
        if (validateDate(birthYear,birthMonth,birthDay)) {
            day = Integer.parseInt(birthDay);
            month = Integer.parseInt(birthMonth);
            year = Integer.parseInt(birthYear);
        }
        
        return userDAO.listUser(nick, firstName, lastName, address, email, day, month, year);
    }

}