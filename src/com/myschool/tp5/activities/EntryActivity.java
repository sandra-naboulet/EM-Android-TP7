package com.myschool.tp5.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.myschool.tp5.VolleyApp;

public class EntryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

	}

	@Override
	protected void onStart() {
		super.onStart();
		checkSharedPreferences();
	}

	private void checkSharedPreferences() {

		SharedPreferences userCurrentLogin = getSharedPreferences(VolleyApp.PREF_ACTIVE_USER, 0);
		if (userCurrentLogin != null) {
			String email = userCurrentLogin.getString("email", null);
			if (email != null && !email.isEmpty()) {
				goToLoginActivity(email);
				return;
			}
		}

		// show menu
		goToMenuActivity();

	}

	private void goToMenuActivity() {
		Intent intent = new Intent(this, MenuActivity.class);
		startActivity(intent);
	}

	private void goToLoginActivity(String email) {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

}
