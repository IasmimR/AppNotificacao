package org.gcm.android.enumeradores;

public enum EnumPreferences {
	EMAIL("email"), NAME("nome"), LISTA_DE_MENSAGENS("listaDeMensagens"), USUARIO_REGISTRADO("usuarioRegistrado");
	public String valor;
	
	EnumPreferences(String valor){
		this.valor = valor;
	}
	
	@Override
	public String toString(){
		return valor;
		
	}
}
