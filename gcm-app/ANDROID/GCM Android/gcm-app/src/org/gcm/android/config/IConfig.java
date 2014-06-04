package org.gcm.android.config;

/***
 * Interface de configurações para envio e recebimento de informações pela aplicação.
 * @author Iasmim Ribeiro
 * @since 1.0
 * @version 1.0
 */
public interface IConfig {

	/**
	 * Utilizado para enviar o GCM regId para o servidor . //necessário alterar o IP abaixo para o IP da maquina
	 */
	static final String APP_SERVER_URL = "http://192.168.0.11:8080/gcm-server/Register";

	/**
	 * Número do projeto.
	 */
	static final String GOOGLE_PROJECT_ID = "52482822914";
	
	/**
	 * Tag de identificação da mensagem no JSON recebido pelo servidor PHP. 
	 */
	static final String MESSAGE_KEY = "message";
	
	static final String MESSAGE_TYPE = "type";

}
