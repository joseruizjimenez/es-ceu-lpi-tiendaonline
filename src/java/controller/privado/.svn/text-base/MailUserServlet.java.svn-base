package controller.privado;

import controller.BasicUtilitiesServlet;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
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
 * Servlet para enviar un correo electronico con los datos del usuario
 */
@WebServlet(name="MailUserServlet")
public class MailUserServlet extends BasicUtilitiesServlet {
    Logger logger = Logger.getLogger(MailUserServlet.class.getName());
   
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String authenticated = (String) session.getAttribute("authenticated");
        User user = (User) session.getAttribute("user");
        if(user == null || "false".equals(authenticated)) {
            String nick = request.getParameter("nick");
            if(nick == null) {
                gotoURL(frontPage,request,response);
            } else {
                UserDAO userDAO = UserPersistFactory.getUserDAO(persistenceMechanism);
                user = userDAO.readUserByNick(nick);
                if(user == null) {
                    request.setAttribute("info","Ese nick no esta registrado");
                    gotoURL(userLogin,request,response);
                }
            }
        }
        ServletContext context = session.getServletContext();
        InputStream is = context.getResourceAsStream(emailConfigFile);
        Properties emailConfig = new Properties();
        try {
            emailConfig.load(is);
            Session mailSession = Session.getDefaultInstance(emailConfig);
            mailSession.setDebug(true);
            Message mail = new MimeMessage(mailSession);
            mail.setSentDate( new java.util.Date( System.currentTimeMillis()));
            mail.addHeader( "Content-Type", "text/html" );
            mail.setFrom( new InternetAddress(emailConfig.getProperty("mail.smtp.user")));
            mail.setRecipient( Message.RecipientType.TO,new InternetAddress(user.getEmail()));
            
            mail.setSubject("Sus datos de usuario en TiendaOnline");
            mail.setText("Sus datos de usuario en TiendaOnline:\n"
                    + "Nombre: "+user.getFirstName()+"\n"
                    + "Apellidos: "+user.getLastName()+"\n"
                    + "Nick: "+user.getNick()+"\n"
                    + "Password: "+user.getPassword()+"\n"
                    + "Fecha Nacimiento: "+user.getBirthDateAsString()+"\n"
                    + "Direccion: "+user.getAddress()+"\n"
                    + "\n Gracias por estar con nosotros. TiendaOnline");
            
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(emailConfig.getProperty("mail.smtp.user"),
                    emailConfig.getProperty("password"));
            transport.sendMessage(mail,mail.getAllRecipients());
            transport.close();
            request.setAttribute("info","El email con los datos ha sido enviado");
        } catch (IOException ex) {
            request.setAttribute("info","Error enviando el email");
            logger.error("Error cargando los parametros del fichero de email", ex);
        } catch (AddressException ex) {
            request.setAttribute("info","Error enviando el email");
            logger.warn("Error enviando email", ex);
        } catch (MessagingException ex) {
            request.setAttribute("info","Error enviando el email");
            logger.warn("Error enviando email", ex);
        } finally {
            if("true".equals(authenticated)) {
                gotoURL(userSettings,request,response);
            } else {
                gotoURL(userLogin,request,response);
            }
        }     
    }

}