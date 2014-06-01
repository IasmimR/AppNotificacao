<%-- 
    Document   : index
    Created on : 27/05/2014, 23:50:30
    Author     : Iasmim Ribeiro
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="org.gcm.server.transport.GcmUser"%>
<%@page import="org.gcm.server.db.DbFunctions"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GCM Index</title>
        <jsp:include page="config_styles.jsp" />
        <link rel="stylesheet" type="text/css" href="css/gcm-index.css"/>
        <script type="text/javascript">
            $(document).ready(function() {
                $("textarea").on({
                    keypress: function(){
                        var textarea = $(this);
                        var spanContador = $($(textarea).parent().parent().find("span.badge")).find("span#contador");
                        if($(textarea).val().length < 1024){
                            $(spanContador).text( $(textarea).val().length);
                        }
                        else if ($(textarea).val().length < 2048){
                            $(spanContador).text( $(textarea).val().length);
                        }
                        else{
                            window.block = true;
                            $(textarea).parent().parent().find("span.badge").css('background','red');
                            $(spanContador).text( $(textarea).val().length);
                        }
                    },
                    
                    keyup: function(){
                        var textarea = $(this);
                        var spanContador = $($(textarea).parent().parent().find("span.badge")).find("span#contador");
                       $(spanContador).text( $(textarea).val().length); 
                       if($(textarea).val().length < 2048){
                           $(textarea).parent().parent().find("span.badge").css('background','#999');
                       }
                    }
                });
            });
            function sendPushNotification(id, campo) {
                var data = $('form#' + id).serialize();
                
                if( $(campo).find("textarea").val().trim().length === 0 ){ 
                    alert("Por favor insira a mensagem que deseja enviar."); 
                    return; 
                } 
            
                if(window.block){
                    alert("Limite de 2kb excedido.");
                }
            
                $('form#' + id).unbind('submit');
                $.ajax({
                    url: "AjaxRequestHandler",
                    type: 'GET',
                    data: data,
                    beforeSend: function() {

                    },
                    success: function(data, textStatus, xhr) {
                        $('.txt_message').val("");
                        console.log(data);
                    },
                    error: function(xhr, textStatus, errorThrown) {

                    }
                });
                return false;
            }
        </script>
    </head>
    <body>
        <%
            DbFunctions dbFunc = new DbFunctions();

            ArrayList<GcmUser> listOfUsers = dbFunc.getAllUsers();
            int no_of_users = listOfUsers.size();
        %>
        <div class="container">
            <h1>Nº de smartphones registrados:
                <%= no_of_users%>
                <a href="android-apk/gcm-app.apk">Download app</a> 
                <span style="margin-left: 10px; margin-right: 10px; float:right">|</span>
                <a href="group_manager.jsp"> Grupos </a>
            </h1>
            <hr/>
            <div class='download'>

            </div>
            <ul class="devices">
                <%
                    if (no_of_users > 0) {
                %>
                <%
                    for (GcmUser user : listOfUsers) {
                %>
                <li>
                    <form id="<%=  user.getId()%>" name="" method="post" onsubmit="return sendPushNotification('<%= user.getId()%>', $(this))">
                        <label>Nome: </label> <span><%= user.getName()%></span>
                        <div class="clear"></div>
                        <label>Email:</label> <span><%= user.getEmail()%></span>
                        <span class="badge" style="float: right;"><span id="contador">0</span><span id="peso">b</span></span>
                        <div class="clear"></div>
                        <div class="send_container">                                
                            <textarea rows="3" name="message" cols="25" class="txt_message" placeholder="Insira sua mensagem aqui"></textarea>
                            <input type="hidden" name="regId" value="<%= user.getGcm_regid()%>"/>
                            <input type="submit" class="btn btn-primary" value="Enviar" onclick=""/>
                        </div>
                    </form>
                </li>
                <% }
                } else {
                %> 
                <li>
                    Sem usuários registrados!
                </li>
                <% }%>
            </ul>
        </div>
    </body>
</html>
