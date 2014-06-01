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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Iasmim Ribeiro
 * @since 1.0
 * @version 1.0
 */
public class Content {

    public List<String> registration_ids;
    public Map<String,String> data;

    public void addRegId(String regId){
        if(registration_ids == null)
            registration_ids = new LinkedList<>();
        registration_ids.add(regId);
    }

    public void createData(String type, String message){
        if(data == null)
            data = new HashMap<>();

        data.put("type", type);
        data.put("message", message);
    }
    
    public void setRegistrationIds(List<String> list){
        this.registration_ids = list;
    }
    
    /**
     * @return the registration_ids
     */
    public List<String> getRegistration_ids() {
        return registration_ids;
    }

    /**
     * @param registration_ids the registration_ids to set
     */
    public void setRegistration_ids(List<String> registration_ids) {
        this.registration_ids = registration_ids;
    }

    /**
     * @return the data
     */
    public Map<String,String> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Map<String,String> data) {
        this.data = data;
    }
}
