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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.gcm.server.config.Config;

/**
 * Classe de conexão com o banco de dados mysql.
 * @author Iasmim Ribeiro
 * @since 1.0
 * @since 1.0
 */
public class DbConnect {
    
    /**
     * Variável de conexão com o banco de dados.
     */
    private Connection con = null;
        
    /**
     * Tag de identificação no log.
     */
    private final String TAG = DbConnect.class.getName();
    
    /**
     * Método para gerar uma nova conexão com o banco de dados.
     * @return Retorna uma conexão ativa com o banco de dados em caso de sucesso.
     */
    private Connection Connect(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Config.URL, Config.DB_USER, Config.DB_PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(TAG).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(con == null){
            Logger.getLogger(TAG).log(Level.CONFIG, "Conexão falhou.");
        }
        
        return con;
    }
    
    /**
     * @return Retorna a conexão.
     */
    public Connection getCon() {
        if(con == null){
            this.con = Connect();
        }
        
        return con;
    }
    
}
