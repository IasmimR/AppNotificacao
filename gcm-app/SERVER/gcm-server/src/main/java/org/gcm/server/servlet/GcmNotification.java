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

package org.gcm.server.servlet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.codehaus.jackson.map.ObjectMapper;
import org.gcm.server.config.Config;
import org.gcm.server.transport.Content;

/**
 *
 * @author Iasmim Ribeiro
 * @since 1.0
 * @version 1.0
 */
public class GcmNotification {
    
    public static String sendNotification(Content content) {
        StringBuilder response = null;
        try {

            URL url = new URL(Config.ANDROID_URL_MESSAGE_SEND);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "key=" + Config.GOOGLE_API_KEY);
            conn.setDoOutput(true);

            ObjectMapper mapper = new ObjectMapper();
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            try{
                mapper.writeValue(wr, content);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            wr.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()))) {
                String inputLine;
                response = new StringBuilder();
                
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (Exception ex){
            ex.printStackTrace();
        }
        
        return response.toString();
    }
}
