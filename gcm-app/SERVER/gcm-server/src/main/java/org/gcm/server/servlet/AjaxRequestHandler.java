/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gcm.server.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.gcm.server.db.DbFunctions;
import org.gcm.server.transport.Content;
import org.gcm.server.transport.GcmObject;

/**
 *
 * @author Ronniery
 */
public class AjaxRequestHandler extends HttpServlet {

    private final String TAG = AjaxRequestHandler.class.getSimpleName();
    private PrintWriter out;
    private Content content;
    private final DbFunctions dbFunctions;

    String type = null;
    String resp = null;

    public AjaxRequestHandler() {
        this.content = new Content();
        this.dbFunctions = new DbFunctions();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Register</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Register at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        type = "0";

        out = response.getWriter();
        
        List<String> list = new ArrayList<>();
        list.add(request.getParameter("regId"));

        content.setRegistrationIds(list);
        content.createData(type, request.getParameter("message"));

        try {
            String resp = GcmNotification.sendNotification(content);
            out.write(resp);
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Integer typeOfParameter = Integer.parseInt(request.getParameter("type"));
        String json = request.getParameter("data");

        Gson gson = new Gson();
        GcmObject obj = null;

        out = response.getWriter();

        switch (typeOfParameter) {
            /* Requisição para obter o total de grupos */
            case 1:
                out.write(dbFunctions.getCountOfGroups());
                out.close();
                break;
            /* Requisição para cadastrar um grupo com usuários */
            case 2:
                try {
                    obj = gson.fromJson(json, GcmObject.class);
                } catch (JsonSyntaxException ex) {
                    java.util.logging.Logger.getLogger(TAG).log(Level.SEVERE, null, ex);
                }
                String respStoreGroup = dbFunctions.storeGroup(obj.getGroupName(), obj.getLista());
                out.write(respStoreGroup);
                out.close();
                break;
            case 3:
                obj = gson.fromJson(json, GcmObject.class);
                Integer respGetCountOfUsers = dbFunctions.getCountOfUsersInGroup(obj.getId());
                out.write(gson.toJson(respGetCountOfUsers));
                out.close();
                break;
            /* Envio de mensagens 1:N */
            case 4:
                type = "1";
                obj = gson.fromJson(json, GcmObject.class);
                content = new Content();

                content.setRegistrationIds((List<String>) dbFunctions.getIdOfUsersInGroup(obj.getId()));
                content.createData(type, obj.getMessage());

                try {
                    resp = GcmNotification.sendNotification(content);
                    out.write(resp);
                    out.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                break;
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
