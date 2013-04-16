<%-- 
    Document   : index
    Created on : 20-abr-2011, 13:02:05
    Author     : JOSE RUIZ JIEMENZ
--%>

<%@page import="model.Record"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" title="standar" type="text/css" href="/TiendaOnline/style.css">
        <title>Tienda Online</title>
    </head>
    <body>
        <jsp:useBean id="cart" scope="session" class="model.Cart" />
        <jsp:useBean id="user" scope="session" class="model.User" />
        <jsp:useBean id="authenticated" scope="session" class="java.lang.String" />
        <jsp:useBean id="loginError" scope="request" class="java.lang.String" />
        <jsp:useBean id="categories" scope="application" class="java.util.HashMap" />
        <jsp:useBean id="catalog" scope="application" class="model.Catalog" />
        <%int maxItemsDisplay = 4; %>
        
        <div id="header">
            <a href="/TiendaOnline/" class="float"><img src="cssimages/logo.jpg" alt="" width="171" height="73" /></a>																																																		<div class="inner_copy"><a href="http://www.greatdirectories.org/">web directories</a><a href="http://www.bestfreetemplates.info/">free CSS templates</a></div>
            <div class="topblock1">
                Moneda:<br /><select><option>EUR €</option></select>
            </div>
	    <div class="topblock2">
                Idioma:<br />
		<a href="#"><img src="cssimages/flag1.gif" alt="" width="19" height="11" /></a>																																		
		<a href="#"><img src="cssimages/flag2.gif" alt="" width="19" height="11" /></a>
		<a href="#"><img src="cssimages/flag3.gif" alt="" width="19" height="11" /></a>
		<a href="#"><img src="cssimages/flag4.gif" alt="" width="19" height="11" /></a>
		<a href="#"><img src="cssimages/flag5.gif" alt="" width="19" height="11" /></a>
		<a href="#"><img src="cssimages/flag6.gif" alt="" width="19" height="11" /></a>
            </div>
            <div class="topblock2">
                <a href="/TiendaOnline/go?to=shoppingcart"><img src="cssimages/shopping.gif" alt="" width="24" height="24" class="shopping" /></a>																																																																									<div class="inner_copy"><a href="http://www.bestfreetemplates.org/">free templates</a><a href="http://www.bannermoz.com/">banner templates</a></div>
                <p>Carro de compra</p>
                <p><strong><%if(cart == null) {%>0<%}else{ %><jsp:getProperty name="cart" property="numberOfItems" /><%} %></strong><span> articulos</span></p>
            </div>
            <ul id="menu">
                <li><img src="cssimages/li.gif" alt="" width="19" height="29" /></li>
		<li><a href="/TiendaOnline/"><img src="cssimages/but1_a.gif" alt="" width="90" height="29" /></a></li>
		<li><a href="/TiendaOnline/go?to=new"><img src="cssimages/but2.gif" alt="" width="129" height="29" /></a></li>
		<li><a href="/TiendaOnline/go?to=featured"><img src="cssimages/but3.gif" alt="" width="127" height="29" /></a></li>
		<li><a href="/TiendaOnline/go?to=user"><img src="cssimages/but4.gif" alt="" width="113" height="29" /></a></li>
		<li><a href="/TiendaOnline/go?to=search"><img src="cssimages/but5.gif" alt="" width="105" height="29" /></a></li>
                <li><a href="/TiendaOnline/go?to=shoppingcart"><img src="cssimages/but6.gif" alt="" width="132" height="29" /></a></li>
		<li><a href="#"><img src="cssimages/but7.gif" alt="" width="82" height="29" /></a></li>
		<li><a href="#"><img src="cssimages/but8.gif" alt="" width="112" height="29" /></a></li>
		<li><a href="/TiendaOnline/go?to=admin"><img src="cssimages/but9.gif" alt="" width="71" height="29" /></a></li>
            </ul>
	</div>
	
	<div id="container">
            <div id="center" class="column">
                <a href="/TiendaOnline/go?to=featured" class="banner"><img src="cssimages/bigbanner.jpg" alt="" width="572" height="236" /></a>
	  	<div id="content">
                    <a href="/TiendaOnline/go?to=new" class="banner"><img src="cssimages/title3.gif" alt="" width="540" height="26" class="pad25" /></a>
                    <div class="stuff">
                        <%Record[] newRecords = catalog.getNewRecords();
                          for(int i=0;i<newRecords.length && i<maxItemsDisplay;i++) {
                              if(newRecords[i] != null) {%>
                                <div class="item">
                                    <img src="recordImages/recordSmall.jpg" alt="" width="124" height="90" />
                                    <%--<img src="recordImages/<%=newRecords[i].getIdAsString() %>.jpg" alt="" width="124" height="90" />--%>
                                    <a href="/TiendaOnline/go?to=product&id=<%=newRecords[i].getIdAsString() %>" class="name"><%=newRecords[i].getName() %></a>
                                    <a href="/TiendaOnline/go?to=search&artist=<%=newRecords[i].getArtist() %>" class="artist"><%=newRecords[i].getArtist() %></a>
                                    <span><%=newRecords[i].getPriceAsFormattedString() %></span>
                                    <a href="/TiendaOnline/go?to=product&id=<%=newRecords[i].getIdAsString() %>"><img src="cssimages/zoom.gif" alt="" width="53" height="19" /></a><a href="/TiendaOnline/go?to=shoppingcart&id=<%=newRecords[i].getIdAsString() %>&n=1"><img src="cssimages/cart.gif" alt="" width="71" height="19" /></a>
                                </div>
                            <%}
                          }%>                       
                    </div>
		</div>
            </div>
            <div id="left" class="column">
                <div class="block">
                    <img src="cssimages/title1.gif" alt="" width="168" height="42" /><br />
                    <ul id="navigation">
                        <%int count = 0; 
                          for(Object tagObject : categories.keySet()) {
                            String tag = (String) tagObject; 
                            if(count%2==0) {%>
                                <li class="color"><a href="/TiendaOnline/go?to=search&type=<%=categories.get(tag)%>"><%=tag%></a></li>
                            <%} else { %>
                                <li><a href="/TiendaOnline/go?to=search&type=<%=categories.get(tag)%>"><%=tag%></a></li>
                            <%}
                            count++;
                          }%>
                    </ul>
		</div>
		<img src="cssimages/banner1.jpg" alt="" width="172" height="200" />
            </div>
            <div id="right" class="column">
                <a href="/TiendaOnline/go?to=search&type=electronica"><img src="cssimages/banner2.jpg" alt="" width="237" height="216" /></a><br />
		<div class="rightblock">
                    <%--<img src="cssimages/title4.gif" alt="" width="223" height="29" /><br />--%>
                    <div class="blocks">
                        <img src="cssimages/top_bg.gif" alt="" width="218" height="12" />
                        <% if(authenticated != null && authenticated.equals("true")) { %>
                            <p class="line center">Bienvenido, <jsp:getProperty name="user" property="nick" />!</p>
                            <p class="line center"><a href="/TiendaOnline/go?to=shoppingcart" class="reg">Mi carro</a> | <a href="/TiendaOnline/go?to=user" class="reg">Mi cuenta</a> | <a href="/TiendaOnline/go?to=logout" class="reg">Logout</a></p>
                        <%}else { %>
                            <form method="post" action="/TiendaOnline/go">
                                <input name="to" value="user" type="hidden">
                                <p class="line"><span>Usuario:</span> <input name="user" value="" type="text" /></p>
                                <p class="line"><span>Password:</span> <input name="pass" value="" type="password" /></p>
                                <p class="line center"><a href="/TiendaOnline/go?to=register" class="reg">Registro</a> | <a href="/TiendaOnline/go?to=login" class="reg">Recordar password</a></p>
                                <p class="line centerred"><%if(loginError != null && !loginError.equals("")) {%><%=loginError%><%} %></p>
                                <p class="line center pad20"><button>Loguearse</button></p>
                            </form>
                        <%}%>
			<img src="cssimages/bot_bg.gif" alt="" width="218" height="10" /><br />
                    </div>
                    <div class="blocks">
                        <img src="cssimages/top_bg.gif" alt="" width="218" height="12" />
			<div id="news">
                            <img src="cssimages/title5.gif" alt="" width="201" height="28" />
                            <span class="date">Kurt D. Cobain</span>
                            <p>La música es sinónimo de libertad, de tocar lo que quieras y como quieras, siempre que sea bueno y tenga pasión, que la música sea el alimento del amor.</p>
                            <a href="http://www.google.com/search?q=citas+celebres+musica" class="more">Lee mas</a>
			</div>
			<img src="cssimages/bot_bg.gif" alt="" width="218" height="10" /><br />
                    </div>
		</div>
            </div>
	</div>
	
	<div id="footer">
            <a href="/TiendaOnline/">Home</a>  |  <a href="/TiendaOnline/go?to=new">Novedades</a>  |  <a href="/TiendaOnline/go?to=featured">Destacados</a>  |  <a href="/TiendaOnline/go?to=user">Mi Cuenta</a>  |  <a href="/TiendaOnline/go?to=shoppingcart">Carrito de Compra</a>  |  <a href="/TiendaOnline/go?to=admin" class="terms">Admin</a>
            <p>Ejemplo javaEE por Jose Ruiz Jimenez. Template diseñado por: <a href="http://www.bestfreetemplates.info" target="_blank" id="bft" title="Best Free Templates">BFT</a>     </p>																																																																																																																																									<div class="inner_copy"><a href="http://www.beautifullife.info/">beautiful</a><a href="http://www.grungemagazine.com/">grunge</a></div>
	</div>
    </body>
</html>