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

package org.gcm.server.transport;

import java.io.Serializable;

/**
 * Classe que representa a tabela gcm_users.
 * @author Iasmim Ribeiro
 * @since 1.0
 * @version 1.0
 */
public class GcmUser implements Serializable {
    
    // <editor-fold desc=" VARIÁVEIS " defaultstate="collapsed">
    /**
     * Id de identificação do usuário.
     */
    private int id;
    
    /**
     * Nome do usuário.
     */
    private String name;
    
    /**
     * Email do usuário.
     */
    private String email;
    
    /**
     * GCM id gerado pelo servidor do google.
     */
    private String gcm_regid;

    // </editor-fold>
    
    // <editor-fold desc=" GETTERS/SETTERS " defaultstate="collapsed">
    
    /**
     * @return O nome do usuário.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name O nome para setar na propriedade {@code nome}.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return O email do usuário.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email O email para setar na propriedade {@code email}.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return O gcm_regid do usuário.
     */
    public String getGcm_regid() {
        return gcm_regid;
    }

    /**
     * @param gcm_regid O gcm_regid para setar na propriedade {@code gcm_regid}.
     */
    public void setGcm_regid(String gcm_regid) {
        this.gcm_regid = gcm_regid;
    }

    /**
     * @return O id do usuário.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id O id para setar na propriedade {@code id}.
     */
    public void setId(int id) {
        this.id = id;
    }
    
    // </editor-fold>
}
