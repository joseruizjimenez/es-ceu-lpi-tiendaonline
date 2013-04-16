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

/**
 * Servlet para enviar un correo electronico con la factura de la compra.
 */
@WebServlet(name="MailSaleServlet")
public class MailSaleServlet extends BasicUtilitiesServlet {
    Logger logger = Logger.getLogger(MailSaleServlet.class.getName());
   
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String bill = (String) session.getAttribute("emailBill");
        if(bill == null) {
            gotoURL(frontPage,request,response);
        } else {
            String sendTo = "";
            User user = (User) session.getAttribute("user");
            if("true".equals(session.getAttribute("authenticated")) && user != null) {
                sendTo = user.getEmail();
            } else {
                String emailForm = request.getParameter("email");
                if(!validateEmail(emailForm)) {
                    request.setAttribute("info","El formato del email es erroneo");
                    gotoURL(checkoutSuccess,request,response);
                }
                sendTo = emailForm;
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
                mail.setRecipient( Message.RecipientType.TO,new InternetAddress(sendTo));
                
                mail.setSubject("Factura de compra de TiendaOnline");
                mail.setText("Ha realizado con exito su compra, esta es su factura:\n"+bill);
                
                Transport transport = mailSession.getTransport("smtp");
                transport.connect(emailConfig.getProperty("mail.smtp.user"),
                        emailConfig.getProperty("password"));
                transport.sendMessage(mail,mail.getAllRecipients());
                transport.close();
                request.setAttribute("info","El email con la factura ha sido enviado");
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
                gotoURL(checkoutSuccess,request,response);
            }     
        }
    }

}