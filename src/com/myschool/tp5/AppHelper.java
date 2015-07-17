package com.myschool.tp5;

import android.content.Context;
import android.content.SharedPreferences;

import com.myschool.tp7.R;

public class AppHelper {

	public final static String KEY_TOKEN = "token";
	public final static String KEY_EMAIL = "email";
	public final static String KEY_NAME = "name";

	public static String getMessageForStatusCode(Context context, int code) {

		switch (code) {
		case 401:
			return context.getResources().getString(R.string.error_unauthorized);
		case 422:
			return context.getResources().getString(R.string.error_user_already_exists);
		case 500:
			return context.getResources().getString(R.string.error_server_unvailable);
		default:
			return context.getResources().getString(R.string.error_unknown);
		}
	}

	public static void saveInSharedPreferences(Context context, String email, String token) {

		// Save ids
		SharedPreferences userSettings = context.getSharedPreferences(email, 0);
		SharedPreferences.Editor editor = userSettings.edit();
		editor.putString(KEY_TOKEN, token);
		editor.putString(KEY_EMAIL, email);

		SharedPreferences userLoginPrefs = context.getSharedPreferences(VolleyApp.PREF_ACTIVE_USER, 0);
		SharedPreferences.Editor editor2 = userLoginPrefs.edit();
		editor2.putString(KEY_EMAIL, email);

		editor.commit();
		editor2.commit();
	}

	public static String getSessionToken(Context context) {
		String token = null;
		SharedPreferences userCurrentLogin = context.getSharedPreferences(VolleyApp.PREF_ACTIVE_USER, 0);
		if (userCurrentLogin != null) {
			String email = userCurrentLogin.getString(KEY_EMAIL, null);
			if (email != null) {
				SharedPreferences userDatas = context.getSharedPreferences(email, 0);
				token = userDatas.getString(KEY_TOKEN, null);
			}
		}
		return token;
	}

	public static String getUserName(Context context) {
		String name = "Moi";
		SharedPreferences userCurrentLogin = context.getSharedPreferences(VolleyApp.PREF_ACTIVE_USER, 0);
		if (userCurrentLogin != null) {
			String email = userCurrentLogin.getString(KEY_EMAIL, null);
			if (email != null) {
				SharedPreferences userDatas = context.getSharedPreferences(email, 0);
				name = userDatas.getString(KEY_NAME, null);
			}
		}
		return name;
	}

}
