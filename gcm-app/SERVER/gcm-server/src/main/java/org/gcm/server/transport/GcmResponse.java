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
 * Classe para envio de respostas no formato JSON ao cliente android.
 * @author Iasmim Ribeiro
 * @since 1.0
 * @version 1.0
 */
public class GcmResponse implements Serializable{
    
    // <editor-fold desc=" VARIÁVEIS " defaultstate="collapsed">
    /**
     * Indicador de sucesso na operação.
     */
    private Boolean sucesso;
    
    /**
     * Descrição da exceção lançada.
     */
    private String exception;
    
    /**
     * Id do grupo.
     */
    private int groupId;

    // </editor-fold>
    
    // <editor-fold desc=" GETTERS/SETTERS " defaultstate="collapsed">
    
    /**
     * @return A propriedade sucesso.
     */
    public Boolean getSucesso() {
        return sucesso;
    }
    
    /**
     * @param sucesso O valor sucesso para setar na propriedade {@code sucesso}.
     */
    public void setSucesso(Boolean sucesso) {
        this.sucesso = sucesso;
    }

    /**
     * @return A propriedade exception. 
     */
    public String getException() {
        return exception;
    }

    /**
     * @param exception O valor exception para setar na propriedade {@code exception}.
     */
    public void setException(String exception) {
        this.exception = exception;
    }

    /**
     * @return A propriedade groupId.
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * @param groupId O valor groupId para setar na propriedade {@code groupId}.
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
     
    // </editor-fold>
}
