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
            Log.i(Constantes.TAG, "Serviço GCM ativado.");
        } else {
            Log.i(Constantes.TAG, "O Serviço GCM já está ativo. ID: " + regId);
        }
    }
     
    // Desativar o uso do GCM.
    public static void desativa(Context context) {
            GCMRegistrar.unregister(context);
            Log.i(Constantes.TAG, "Serviço GCM Desativado.");
    }
     
    // Verificar se o aplicativo está ou não registrado para usar o GCM.
    public static boolean isAtivo(Context context) {
        return GCMRegistrar.isRegistered(context);
    }
 
	
	
	
	
	
	
}
