package controller.publico;

import controller.BasicUtilitiesServlet;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 * Concede permiso de administrador si los datos proporcionados son validos,
 * coincidiendo con los del fichero de propiedades
 */
@WebServlet(name="AdminLoginServlet")
public class AdminLoginServlet extends BasicUtilitiesServlet {
    Logger logger = Logger.getLogger(AdminLoginServlet.class.getName());
   
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        if(user == null || pass == null) {
            gotoURL(frontPage,request,response);
        }
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        InputStream is = context.getResourceAsStream(adminConfigFile);
        Properties adminConfig = new Properties();
        try {
            adminConfig.load(is);
            String adminUser = adminConfig.getProperty("adminUser");
            String adminPass = adminConfig.getProperty("adminPass");
            if(user.equals(adminUser) && pass.equals(adminPass)) {
                session.setAttribute("admin","true");
                logger.info("Logeado admin desde la IP: "+request.getRemoteAddr());
                gotoURL(adminSettings,request,response);
            } else {
                request.setAttribute("info","Los datos facilitados no son correctos");
                logger.info("Intento fallido de logueo admin desde la IP: "+request.getRemoteAddr());
                gotoURL(adminLogin,request,response);
            }
        } catch (IOException ex) {
            request.setAttribute("info","Error cargando los parametros del fichero");
            logger.error("Error cargando los parametros del fichero de Administrador", ex);
            gotoURL(adminLogin,request,response);
        }        
    }

}