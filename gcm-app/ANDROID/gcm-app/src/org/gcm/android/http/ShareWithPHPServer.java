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
 * Classe responsável por enviar a requisição de registro do usuário ao servidor PHP.
 * @author Iasmim Ribeiro
 * @since 1.0
 * @version 1.0
 */
public class ShareWithPHPServer {

	/**
	 * Tag de identificação no Log.
	 */
	final String TAG = ShareWithPHPServer.class.getSimpleName();
	
	/**
	 * Registra o usuário junto ao servidor PHP.
	 * @param context Contexto de execução.
	 * @param regId GCM id recebido pelo servidor do google.
	 * @param nome Nome do usuário.
	 * @param email Email do usuário.
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
	 * Método de requisição http.
	 * @param serverUrl url de requisição.
	 * @param bytes bytes para limitar o tamanho da requisição.
	 * @return resultado da requisição http.
	 * @throws IOException Exceção io.
	 * @throws ProtocolException Exceção de protocolo.
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
	 * Montagem da url de requisição (POST).
	 * @param paramsMap Parâmetros da url.
	 * @return StringBuilder com todas os parâmetros enviados.
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
