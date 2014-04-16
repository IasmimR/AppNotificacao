package projeto.util;

import com.google.android.gcm.GCMRegistrar;

import android.content.Context;
import android.util.Log;



public class Server_GCM {

    public static void ativa(Context context) { //Ativar o uso do GCM.
        GCMRegistrar.checkDevice(context);
        GCMRegistrar.checkManifest(context);
        final String regId = GCMRegistrar
                .getRegistrationId(context);
        if (regId.equals("")) {
            GCMRegistrar.register(context, Constantes.SENDER_ID);
            Log.i(Constantes.TAG, "Servi�o GCM ativado.");
        } else {
            Log.i(Constantes.TAG, "O Servi�o GCM j� est� ativo. ID: " + regId);
        }
    }
     
    // Desativar o uso do GCM.
    public static void desativa(Context context) {
            GCMRegistrar.unregister(context);
            Log.i(Constantes.TAG, "Servi�o GCM Desativado.");
    }
     
    // Verificar se o aplicativo est� ou n�o registrado para usar o GCM.
    public static boolean isAtivo(Context context) {
        return GCMRegistrar.isRegistered(context);
    }
 
	
	
	
	
	
	
}
