package controller.admin;

import controller.BasicUtilitiesServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 * Servlet para desvincular una cuenta de administrador de una sesion. Logout.
 */
@WebServlet(name="AdminLogoutServlet")
public class AdminLogoutServlet extends BasicUtilitiesServlet {
    Logger logger = Logger.getLogger(AdminLogoutServlet.class.getName());
   
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("admin","false");
        logger.info("Deslogueado el admin desde la IP"+request.getRemoteAddr());
        request.setAttribute("loginError","Administracion terminada");
        gotoURL(frontPage,request,response);      
    }

}