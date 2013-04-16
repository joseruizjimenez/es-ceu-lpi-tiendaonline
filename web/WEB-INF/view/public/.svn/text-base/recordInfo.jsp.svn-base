<%-- 
    Document   : recordInfo
    Created on : 25-abr-2011, 21:16:33
    Author     : JOSE RUIZ JIMENEZ
--%>

<%@page import="model.Record"%>
<%@page import="model.Comment"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Catalog"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" title="standar" type="text/css" href="/TiendaOnline/style.css">
        <title>Tienda Online</title>
        <script src="javascripts/1286136086-jquery.js"></script>
        <script src="javascripts/1291523190-jpaginate.js"></script>
        <script>
            $(document).ready(function() {
                $("#comments").jPaginate({previous:"Anterior", next:"Siguente"});
            });
        </script>
    </head>
    <body>
        <jsp:useBean id="cart" scope="session" class="model.Cart" />
        <jsp:useBean id="user" scope="session" class="model.User" />
        <jsp:useBean id="authenticated" scope="session" class="java.lang.String" />
        <jsp:useBean id="loginError" scope="request" class="java.lang.String" />
        <jsp:useBean id="categories" scope="application" class="java.util.HashMap" />
        <jsp:useBean id="catalog" scope="application" class="model.Catalog" />
        <jsp:useBean id="info" scope="request" class="java.lang.String" />
        <jsp:useBean id="record" scope="request" class="model.Record" />
        <jsp:useBean id="admin" scope="session" class="java.lang.String" />
        
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
            <div id="center" class="column"><br />
                <div id="content">
                    <%ArrayList<Comment> commentList = catalog.getRecordComments(record.getIdAsString()); %>
                    <div id="about">
                      <%if(info!=null && !info.equals("")) {%>
                        <p class="tree"><b><%=info%></b></p>
                      <%}%>
                        <p class="tree"><a href="/TiendaOnline/go?to=search&type=<%=record.getType()%>"><%=record.getType()%></a>&nbsp;&nbsp;>&nbsp;&nbsp;<a href="/TiendaOnline/go?to=search&artist=<%=record.getArtist()%>"><%=record.getArtist()%></a>&nbsp;&nbsp;>&nbsp;&nbsp;<b><%=record.getName()%></b></p>
                        <div class="photos">
                            <pre>
                            </pre>
                            <img src="recordImages/recordBig.jpg" alt="" width="215" height="215" /><br />
                            <a href="#comment" class="comments">Ver comentarios (<%=commentList.size()%>)</a>
                        </div>
                        <div class="description">																																																																																																																																																			<div class="inner_copy"><a href="http://www.bestfreetemplates.org/">free templates</a><a href="http://www.bannermoz.com/">banner templates</a></div>
                            <a href="/TiendaOnline/go?to=product&id=<%=record.getIdAsString()%>" class="name"><%=record.getName()%></a>
                            <pre>
                                
                            </pre>
                            <a href="/TiendaOnline/go?to=search&artist=<%=record.getArtist()%>" class="artist"><%=record.getArtist()%></a>
                            <p> <span class="price"><%=record.getPriceAsFormattedString()%></span></p>
                            <p><%=record.getFullComment()%></p>
                            <p><strong>Otros datos:</strong></p>
                            <ul id="features">
                            <li class="color"><span><b>Sello Discografico:</b></span><a href="/TiendaOnline/go?to=search&recordLabel=<%=record.getRecordLabel()%>"><%=record.getRecordLabel() %></a></li>
                            <li><span><b>Estilo:</b></span><%=record.getType() %></li>
                            </ul><pre>
                            </pre>
                            <form method="post" action="/TiendaOnline/go">
                                <input name="to" value="shoppingcart" type="hidden">
                                <input name="id" value="<%=record.getIdAsString()%>" type="hidden">
                                <p class="line"><span>Unidades</span><input name="n" value="1" type="text" size="3" maxlength="3" /></p>
                                <button>Al carro</button><img src="cssimages/carts.gif" alt="" width="16" height="24" class="carts" />
                            </form>
                            <%if(admin!=null && admin.equals("true")) {%>
                                <a href="/TiendaOnline/go?to=admin&action=deleterecord&id=<%=record.getIdAsString()%>" onclick="return confirm('El disco se borrará, OK?');"><img src="cssimages/delete.gif" alt="" width="24" height="24" /></a><a href="/TiendaOnline/go?to=admin&action=updaterecord&id=<%=record.getIdAsString()%>" onclick="return confirm('¿Editar el disco?');"><img src="cssimages/edit.gif" alt="" width="24" height="24" /></a>
                            <%}%>
                        </div>
                    </div>
                    <img src="cssimages/title6.gif" alt="" width="537" height="23" class="pad25" />
                    <%if(authenticated != null && authenticated.equals("true")) {%>
                    <form method="post" action="/TiendaOnline/go">
                        <input name="to" value="comment" type="hidden">
                        <input name="action" value="new" type="hidden">
                        <input name="id" value="<%=record.getIdAsString()%>" type="hidden">
                        <input name="nick" value="<%=user.getNick()%>" type="hidden">
                        <textarea name="comment" cols="65" rows="3"></textarea>
                        <p class="liner"><button>Comentar</button></p>
                    </form>
                    <%} else {%>
                    <pre>
                    Solo los usuarios logueados pueden escribir comentarios.
                    </pre>
                    <%}%>
                    <a name="comment"></a>
                    <div id="content">
                    <div id="about">
                    <div id="comments">
                        <%for(Comment comment : commentList) {%>
                            <pre>
                                <p class="line"><span><b>Nick: </b><%=comment.getNickname()%></span></p><br />
                                <p class="line"><span><%=comment.getComment()%></span></p><br />
                            </pre>
                            <%if((user!=null && comment.getNickname().equals(user.getNick())) || (admin!=null && admin.equals("true"))) {%>
                                <a href="/TiendaOnline/go?to=comment&action=delete&id=<%=comment.getIdAsString()%>" onclick="return confirm('El comentario se borrará, OK?');"><img src="cssimages/delete.gif" alt="" width="24" height="24" /></a>
                            <%}%>
                        <%} %>
                    </div>
                    </div>
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
                                <p class"line centerred"><%if(loginError != null && !loginError.equals("")) {%><%=loginError%><%} %></p>
                                <p class="line center pad20"><button>Loguearse</button></p>
                            </form>
                        <%}%>
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