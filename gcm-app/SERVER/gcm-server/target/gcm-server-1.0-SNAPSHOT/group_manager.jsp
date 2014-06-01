<%-- 
    Document   : index
    Created on : 27/05/2014, 20:35:47
    Author     : Iasmim Ribeiro
--%>

<%@page import="org.gcm.server.dto.DtoGcmUser"%>
<%@page import="org.gcm.server.transport.GcmGroup"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.gcm.server.db.DbFunctions"%>
<%@page import="org.gcm.server.transport.GcmUser"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%
            DbFunctions dbFunctions = new DbFunctions();
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GCM app</title>
        <jsp:include page="config_styles.jsp" />
        <script type="text/javascript" src="js/gcm.js"></script>
        <link rel="stylesheet" type="text/css" href="css/gcm-group-manager.css"/>
    </head>
    <body>
        <div id="contador" class="divRedonda">
            <ul class="nav nav-pills" style="display:inline-block">
                <li class="active">
                    <a href="#">
                        Total de grupos 
                        <span class="badge">
                            <%
                            Integer total = dbFunctions.getCountOfGroups();
                            %>
                            <%= total %>
                        </span>
                    </a>
                </li>
                <li>
                    <a href="#" id="adicionar_grupo">
                        Adicionar grupo
                    </a>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        Selecionar grupo 
                        <span class="caret">
                        </span>
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        <%
                        ArrayList<GcmGroup> listOfNames = dbFunctions.getAllNameAndCodeOfGroups();

                        for(GcmGroup groupInfo : listOfNames) { %>
                            <li data-id='<%= groupInfo.getId() %>'><a href="#"><%= groupInfo.getGroupName() %></a></li>
                        <%
                        }  	
                        %>
                    </ul>
                </li>
            </ul>
            <div id="form_cadastro_grupos">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="Nome do grupo">
                            <span id="contador_caracteres"></span>
                            <span class="input-group-btn">
                                <button class="btn btn-default" type="button" id="cadastrar">Cadastrar</button>
                            </span>
                        </div><!-- /input-group -->
                    </div><!-- /.col-lg-6 -->
                </div>
                <h4>Componentes do grupo</h3>
                    <select multiple="multiple" id="my-select" name="my-select[]">
                        <%
                            ArrayList<DtoGcmUser> listOfUserName = dbFunctions.getAllUserNames();
                            for(DtoGcmUser user : listOfUserName) { %>
                                <option value="<%= user.getId() %>"><%= user.getName() %></option>
                           <% } %>
                    </select>

            </div>
            <div class="gcm-text" data-selected="false">
                <div>
                    <label>Nome do grupo:</label>
                    <label class="group-name" groupid="0"> Por favor selecione um grupo </label>
                    <label class="badge floatRight" title="Quantidade de usuários">0</label>
                    <img src="img/mobile-phone.png" height="16" width="16" class="floatRight" title="Quantidade de usuários" />
                </div>
                <textarea placeholder="Mensagem..." disabled="disabled"></textarea>
                <div style="position:absolute; left:0; right:0; top:0; bottom:0; cursor: default;z-index:1" id="hidden" ></div>
                <a href="#" class="btn btn-primary" role="button">Enviar</a>
            </div>
            <div class="alert col-lg-6">
                <img src="" id="icone"><span id="mensagem"></span>
            </div>
    </body>
</html>
