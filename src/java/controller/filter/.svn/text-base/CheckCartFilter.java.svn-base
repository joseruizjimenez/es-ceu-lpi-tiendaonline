package controller.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.Cart;
import org.apache.log4j.Logger;

/**
 * Intercepta las peticiones para comprobar si el usuario tiene una cookie
 * de carro de compra, si es asi la parsea y lo copia en un carro en la sesion.
 * Esto solo se realiza si el usuario no tiene una sesion ya activa,
 */
@WebFilter(filterName = "CheckCartFilter", urlPatterns = {"/*"})
public class CheckCartFilter implements Filter{
    
    public CheckCartFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);
        if(session == null) {
            Cookie[] cookies = httpRequest.getCookies();
            Cookie cart = getCartCookie(cookies);
            if(cart != null && !"".equals(cart.getValue())) {
                session = httpRequest.getSession();
                session.setAttribute("cart",new Cart(cart.getValue()));
                Logger.getLogger(CheckCartFilter.class.getName()).trace(
                        "Cargada cookie: "+cart.getValue()+" de "+request.getRemoteAddr());
            }
        }
        chain.doFilter(request, response);
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
    
    /**
     * Busca una cookie referente al carro de la compra
     * @param cookies del usuario
     * @return null si no existe esa cookie
     */
    private Cookie getCartCookie(Cookie[] cookies) {
        if(cookies != null) {
            for(int i=0;i<cookies.length;i++) {
                if("cart".equals(cookies[i].getName()) && !"".equals(cookies[i].getValue()))
                    return cookies[i];
            }
        }
        return null;
    }

}
