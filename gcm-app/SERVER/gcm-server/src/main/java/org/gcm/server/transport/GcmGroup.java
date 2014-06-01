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

/**
 * Classe que representa a tabela gcm_group no banco de dados.
 * @author Iasmim Ribeiro
 * @since 1.0
 * @version 1.0
 */
public class GcmGroup {
    
    // <editor-fold desc=" VARIÁVEIS " defaultstate="collapsed">
    /**
     * Id do grupo.
     */
    private int id;
    
    /**
     * Nome do grupo.
     */
    private String groupName;
    
    // </editor-fold>

    // <editor-fold desc=" GETTERS/SETTERS " defaultstate="collapsed">
    /**
     * @return O valor da propriedade id.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id O valor para alterar a propriedade {@code id}.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return O valor da propriedade GroupName.
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName O valor para alterar a propriedade {@code groupName}.
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
     // </editor-fold>
}
