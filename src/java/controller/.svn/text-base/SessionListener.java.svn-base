package controller;

import java.util.Date;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import model.Cart;
import org.apache.log4j.Logger;

/**
 * Listener encargado asignar a la nueva sesion un carro de la compra.
 * Ademas asigna a la sesion atributos de autenticacion, uno para autenticarse 
 * como usuario, y otro para hacerlo como administrador.
 */
@WebListener
public class SessionListener implements HttpSessionListener{

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute("cart", new Cart());
        session.setAttribute("authenticated", "false");
        session.setAttribute("admin", "false");
        Logger.getLogger(StartUpListener.class.getName()).trace(
                "Creada sesion: "+session.getId()+" a las: "+getTime());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        Logger.getLogger(StartUpListener.class.getName()).trace(
                "Destruida sesion: "+session.getId()+" a las: "+getTime());
    }

    private String getTime() {
        return new Date(System.currentTimeMillis()).toString();
    }

}
