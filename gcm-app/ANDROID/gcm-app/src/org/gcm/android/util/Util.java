package org.gcm.android.util;

import org.gcm.android.MainActivity;

import android.content.Context;
import android.content.SharedPreferences;

public class Util {

	@SuppressWarnings("unchecked")
	public final static <T> T getPreference(Context context, String prefName, T type){
		final SharedPreferences prefs = context.getSharedPreferences(
				MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		Object resultado = null;
		
		 if (type instanceof String) {
			 resultado = prefs.getString(prefName, "");
         }
         if (type instanceof Integer) {
        	 resultado = prefs.getInt(prefName, 0);
         }
         if (type instanceof Float) {
        	 resultado = prefs.getFloat(prefName, 0l);
         }
         if (type instanceof Boolean) {
        	 resultado = prefs.getBoolean(prefName, false);
         }
         
         return (T) resultado;
	}
	
	public final static <T> void setPreference(Context context, String prefName, T value) {
		final SharedPreferences prefs = context.getSharedPreferences(
				MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor mEditor = prefs.edit();
		
		 if (value instanceof String) {
             mEditor.putString(prefName, (String) value);
         }
         if (value instanceof Integer) {
             mEditor.putInt(prefName, (Integer) value);
         }
         if (value instanceof Float) {
             mEditor.putFloat(prefName, (Float) value);
         }
         if (value instanceof Boolean) {
             mEditor.putBoolean(prefName, (Boolean) value);
         }
		
		mEditor.commit();
	}
	
}
