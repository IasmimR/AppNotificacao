package org.gcm.android.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.gcm.android.config.IConfig;

import android.content.Context;
import android.util.Log;

/***
 * Classe respons�vel por enviar a requisi��o de registro do usu�rio ao servidor PHP.
 * @author Iasmim Ribeiro
 * @since 1.0
 * @version 1.0
 */
public class ShareWithPHPServer {

	/**
	 * Tag de identifica��o no Log.
	 */
	final String TAG = ShareWithPHPServer.class.getSimpleName();
	
	/**
	 * Registra o usu�rio junto ao servidor PHP.
	 * @param context Contexto de execu��o.
	 * @param regId GCM id recebido pelo servidor do google.
	 * @param nome Nome do usu�rio.
	 * @param email Email do usu�rio.
	 * @return Mensagem de sucesso ou falha.
	 */
	public final String registreUsuairioPHPServidor(final Context context,
			final String regId, final String nome, final String email) {

		String result = "";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("regId", regId);
		paramsMap.put("name", nome);
		paramsMap.put("email", email);
		try {
			URL serverUrl = null;
			try {
				serverUrl = new URL(IConfig.APP_SERVER_URL);
			} catch (MalformedURLException e) {
				Log.e(TAG, "URL Connection Error: "
						+ IConfig.APP_SERVER_URL, e);
				result = "Invalid URL: " + IConfig.APP_SERVER_URL;
			}

			StringBuilder postBody = monteRequisicao(paramsMap);
			
			String body = postBody.toString();
			byte[] bytes = body.getBytes();
			HttpURLConnection httpCon = null;
			try {
				httpCon = realizeRequisicao(serverUrl, bytes);

				int status = httpCon.getResponseCode();
				if (status == 200) {
					result = "RegId shared with Application Server. RegId: "
							+ regId;
				} else {
					result = "Post Failure." + " Status: " + status;
				}
			} finally {
				if (httpCon != null) {
					httpCon.disconnect();
				}
			}

		} catch (IOException e) {
			result = "Post Failure. Error in sharing with App Server.";
			Log.e(TAG, "Error in sharing with App Server: " + e);
		}
		return result;
	}

	/**
	 * M�todo de requisi��o http.
	 * @param serverUrl url de requisi��o.
	 * @param bytes bytes para limitar o tamanho da requisi��o.
	 * @return resultado da requisi��o http.
	 * @throws IOException Exce��o io.
	 * @throws ProtocolException Exce��o de protocolo.
	 */
	private final HttpURLConnection realizeRequisicao(URL serverUrl, byte[] bytes)
			throws IOException, ProtocolException {
		HttpURLConnection httpCon;
		httpCon = (HttpURLConnection) serverUrl.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setUseCaches(false);
		httpCon.setFixedLengthStreamingMode(bytes.length);
		httpCon.setRequestMethod("POST");
		//httpCon.setReadTimeout(15000);
		httpCon.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		OutputStream out = httpCon.getOutputStream();
		out.write(bytes);
		out.close();
		return httpCon;
	}

	/**
	 * Montagem da url de requisi��o (POST).
	 * @param paramsMap Par�metros da url.
	 * @return StringBuilder com todas os par�metros enviados.
	 */
	private final StringBuilder monteRequisicao(Map<String, String> paramsMap) {
		StringBuilder postBody = new StringBuilder();
		Iterator<Entry<String, String>> iterator = paramsMap.entrySet()
				.iterator();

		while (iterator.hasNext()) {
			Entry<String, String> param = iterator.next();
			postBody.append(param.getKey()).append('=')
					.append(param.getValue());
			if (iterator.hasNext()) {
				postBody.append('&');
			}
		}
		return postBody;
	}
}
