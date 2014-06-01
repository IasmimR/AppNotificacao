/*
 * (C) Copyright ${year} Nuxeo SA (http://nuxeo.com/) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Iasmim Ribeiro
 */

package org.gcm.server.db;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.gcm.server.dto.DtoGcmUser;
import org.gcm.server.transport.GcmGroup;
import org.gcm.server.transport.GcmResponse;
import org.gcm.server.transport.GcmUser;

/**
 * Classe de gerenciamento das requisições feitas ao banco de dados.
 * @author Iasmim Ribeiro
 * @since 1.0
 * @version 1.0
 */
public class DbFunctions {
    
    // <editor-fold desc=" VARIÁVEIS " defaultstate="collapsed">
    /**
     * Referência para a classe do banco de dados.
     */
    private DbConnect db;
    
    /**
     * Referência a conexão do banco.
     */
    private Connection con;
    
    /**
     * Preparador de sqls.
     */
    private PreparedStatement st = null;
    
    // </editor-fold>
    
    // <editor-fold desc=" CONSTRUTOR " defaultstate="collapsed">
    /**
     * Construtor da classe
     */
    public DbFunctions(){
    }
    
    // </editor-fold>
    
    // <editor-fold desc=" MÉTODOS PÚBLICOS " defaultstate="collapsed">
    
    /**
     * Armazena um usuário que foi registrado via smartphone.
     * @param user Objeto usuário para armazenamento.
     * @return boolean Indicador de sucesso ou falha.
     */
    public Boolean storeUser(GcmUser user){
        db = new DbConnect();
        con = db.getCon();
       Timestamp date = new Timestamp(new java.util.Date().getTime());
       String query = "INSERT INTO gcm_users"
                       + "(name, email, gcm_regid, created_at) "
                       + "VALUES(?, ?, ?, ?)";
       
        try {
            st = con.prepareStatement(query);
            st.setString(1, user.getName());
            st.setString(2, user.getEmail());
            st.setString(3, user.getGcm_regid());
            st.setTimestamp(4, date );
        
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DbFunctions.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
           try {
               st.close();
               con.close();
           } catch (SQLException ex) {
               Logger.getLogger(DbFunctions.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        
        return st != null;
    }
    
    /**
     * Insere no banco de dados um grupo junto com uma lista de ids de usuários.
     * @param group_name Nome do grupo para inserção na tabela 'gcm_groups'.
     * @param list_user_id Lista com os ids dos usuários que farão parte do grupo.
     * @return Objeto de resposta convertido em JSON de resposta informando true em caso de sucesso.
     */
    public String storeGroup(String group_name, ArrayList<String> list_user_id){
        db = new DbConnect();
        con = db.getCon();
        GcmResponse obj = new GcmResponse();
        Gson gson = new Gson();
        String query = "INSERT INTO "
                        + "gcm_groups (group_name) "
                        + "VALUES (?)";
        
        String getMaxId = "SELECT MAX(id) as id FROM gcm_groups";
        String insertUser = "INSERT INTO user_per_group "
                            + "(gcm_user_id, gcm_group_id) "
                            + "VALUES (?,?)";
        int group_id = 0;
        
        try {
            /*Realiza o insert do nome do grupo na tabela*/
            st = con.prepareStatement(query);
            st.setString(1, group_name);
            st.executeUpdate();
            
            
            /* Obtém o código do grupo inserido */
            ResultSet rsMaxId = con.createStatement().executeQuery(getMaxId);
            if(rsMaxId != null){
                while(rsMaxId.next()){  
                    group_id = rsMaxId.getInt("id");
                }
            }
            
            /* Insere todos os itens da lista no banco */
            PreparedStatement insertSt = con.prepareStatement(insertUser);
            insertSt.setInt(2, group_id);
            for (String user_id : list_user_id) {
                insertSt.setString(1, user_id);
                insertSt.executeUpdate();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DbFunctions.class.getName()).log(Level.SEVERE, null, ex);
            obj.setSucesso(Boolean.FALSE);
            obj.setException(ex.getMessage());
                        
            return gson.toJson(obj);
        } finally{
           try {
               st.close();
               con.close();
           } catch (SQLException ex) {
               Logger.getLogger(DbFunctions.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        
        obj.setSucesso(Boolean.TRUE);
        obj.setGroupId(group_id);
        
        return gson.toJson(obj);
    }
    
    /**
     * Obtém todas as informações dos usuários no banco de dados.
     * @return Lista de {@code GcmUser} com todos os usuários presentes no banco.
     */
    public ArrayList<GcmUser> getAllUsers(){
        db = new DbConnect();
        con = db.getCon();
        ArrayList<GcmUser> listOfUsers = new ArrayList<>();
        String getAllUsersQuery = "SELECT * FROM gcm_users";
        
        try {
            ResultSet rs = con.createStatement().executeQuery(getAllUsersQuery);
            
            while(rs.next()){
                GcmUser gcmUser = new GcmUser();
                gcmUser.setId(rs.getInt("id"));
                gcmUser.setEmail(rs.getString("email"));
                gcmUser.setGcm_regid(rs.getString("gcm_regid"));
                gcmUser.setName(rs.getString("name"));
                
                listOfUsers.add(gcmUser);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DbFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listOfUsers;
    }
    
    /**
     * Obtém todos os nomes e ids de usuários no banco de dados.
     * @return Lista de objetos parciais com os nomes e ids dos usuários.
     */
    public ArrayList<DtoGcmUser> getAllUserNames(){
        db = new DbConnect();
        con = db.getCon();
        ArrayList<DtoGcmUser> listOfNames = new ArrayList<>();
        String getAllUsersQuery = "SELECT id,name FROM gcm_users";
        
        try {
            ResultSet rs = con.createStatement().executeQuery(getAllUsersQuery);
            
            while(rs.next()){
                DtoGcmUser gcmUser = new DtoGcmUser();
                gcmUser.setId(rs.getInt("id"));
                gcmUser.setName(rs.getString("name"));
                
                listOfNames.add(gcmUser);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DbFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listOfNames;
    }
    
    /**
     * Obtém o total de grupos cadastrados no banco.
     * @return Quantidade de grupos no banco.
     */
    public int getCountOfGroups(){
        db = new DbConnect();
        con = db.getCon();
        String getCount = "SELECT count(id) as count FROM  gcm_groups";
        int count = 0;
                
        try {
            ResultSet rs = con.createStatement().executeQuery(getCount);
            if(rs != null){
                while(rs.next()){
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return count;
    }
    
    /**
     * Obtém todos os nomes e códigos dos grupos cadastrados.
     * @return Lista com objeto que representa o grupo.
     */
    public ArrayList<GcmGroup> getAllNameAndCodeOfGroups(){
        db = new DbConnect();
        con = db.getCon();
        ArrayList<GcmGroup> listOfGroups = new ArrayList<>();
        String getAllUsersQuery = "SELECT * FROM gcm_groups";
        
        try {
            ResultSet rs = con.createStatement().executeQuery(getAllUsersQuery);
            
            while(rs.next()){
                GcmGroup gcmUser = new GcmGroup();
                gcmUser.setId(rs.getInt("id"));
                gcmUser.setGroupName(rs.getString("group_name"));
                
                listOfGroups.add(gcmUser);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DbFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listOfGroups;
    }
    
    /**
     * Obtém a contagem de usuários em um determinado grupo.
     * @param groupId Código do grupo para obter a quantidade de usuários.
     * @return Quantidade de usuários em um determinado grupo.
     */
    public int getCountOfUsersInGroup(int groupId){
        db = new DbConnect();
        con = db.getCon();
        String getCount = "SELECT COUNT(g.gcm_user_id) AS total "
                        + "FROM user_per_group AS g, gcm_groups AS h "
                        + "WHERE g.gcm_group_id = h.id AND h.id = (?)";
        int count = 0;
                
        try {            
            st = con.prepareStatement(getCount);
            st.setInt(1, groupId);
            
            ResultSet rs = st.executeQuery();
            if( rs != null){
                while(rs.next()){
                    count = rs.getInt("total");
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DbFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return count;
    }
    
    /**
     * Obtém todos os GCM ids dos usuários que estejam cadastrados em um determinado grupo.
     * @param groupId Identificador de um determinado grupo.
     * @return Lista de GCM id.
     */
    public ArrayList<String> getIdOfUsersInGroup(int groupId){
        db = new DbConnect();
        con = db.getCon();
        ArrayList<String> listOfIds = new ArrayList<>();
        String getUsers = "SELECT g.gcm_regid FROM "
                        + "gcm_users AS g,user_per_group AS h,gcm_groups AS d "
                        + "WHERE g.id = h.gcm_user_id "
                        + "AND d.id = h.gcm_group_id "
                        + "AND d.id = (?)";
                
        try {
            st = con.prepareStatement(getUsers);
            st.setInt(1, groupId);
            
            ResultSet rs = st.executeQuery();
            if(rs != null){
                while(rs.next()){                
                    listOfIds.add(rs.getString("gcm_regid"));
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DbFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listOfIds;
    }
    
     // </editor-fold>
}
