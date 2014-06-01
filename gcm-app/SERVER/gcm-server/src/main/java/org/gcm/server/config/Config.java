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

package org.gcm.server.config;

/**
 * Classe de configuração do banco de dados e informações para o servidor GCM.
 * @author Iasmim Ribeiro
 * @since 1.0
 * @version 1.0
 */
public class Config {
    
    /**
     * Host de hospedagem do banco de dados.
     */
    public static final String DB_HOST = "localhost:3306";
    
    /**
     * Usuário do banco de dados.
     */
    public static final String DB_USER = "root";
    
    /**
     * Senha do banco de dados.
     */
    public static final String DB_PASSWORD = "";
    
    /**
     * Banco de dados para conexão.
     */
    public static final String DB_DATABASE = "gcm";
    
    /**
     * Url de conexão para o driver do banco mysql.
     */
    public static final String URL = "jdbc:mysql://" + DB_HOST + "/" + DB_DATABASE;

    /**
     * Key de uso público para utilização da API GCM do google.
     */
    public static final String GOOGLE_API_KEY = "AIzaSyAixqpdcupZbebrDEavY40YbuY50OO9_Q8";
    
    /**
     * Url do GCM para envio de mensagens.
     */
    public static final String ANDROID_URL_MESSAGE_SEND = "https://android.googleapis.com/gcm/send";
}
