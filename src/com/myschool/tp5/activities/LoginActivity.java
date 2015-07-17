package com.myschool.tp5.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.myschool.tp5.AppHelper;
import com.myschool.tp5.VolleyApp;
import com.myschool.tp5.models.Me;
import com.myschool.tp7.R;

public class LoginActivity extends Activity implements OnClickListener {

	private final static String PREF_ACTIVE_USER = "ACTIVE_USER";

	Button mLoginButton = null;
	EditText mEmailEditText = null;
	EditText mPasswordEditText = null;
	ProgressBar mProgressBar = null;

	SharedPreferences mSharedPrefs;
	String mEmailPrefs;
	String mPasswordPrefs;
	String mEmail = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mLoginButton = (Button) findViewById(R.id.login_button);
		mEmailEditText = (EditText) findViewById(R.id.login_email);
		mPasswordEditText = (EditText) findViewById(R.id.login_password);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
		mLoginButton.setOnClickListener(this);

	}

	@Override
	protected void onStart() {
		initWithUserPrefs();
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onResume() {
		mProgressBar.setVisibility(View.GONE);
		mLoginButton.setVisibility(View.VISIBLE);
		super.onResume();
	}

	private void initWithUserPrefs() {

		SharedPreferences userCurrentLogin = getSharedPreferences(PREF_ACTIVE_USER, 0);
		if (userCurrentLogin != null) {
			mEmailPrefs = userCurrentLogin.getString("email", null);
			if (mEmailPrefs != null && !mEmailPrefs.isEmpty()) {
				mEmailEditText.setText(mEmailPrefs);
				return;
			}
		}
		mEmailEditText.setText("");
		mPasswordEditText.setText("");
	}

	private boolean hasErrors() {

		String inputEmail = mEmailEditText.getText().toString().trim();
		String inputPass = mPasswordEditText.getText().toString().trim();

		if (inputEmail.isEmpty()) {
			mEmailEditText.setError(getResources().getString(R.string.email_error));
			return true;
		}

		if (inputPass.isEmpty()) {
			mPasswordEditText.setError(getResources().getString(R.string.password_error));
			return true;
		}

		return false;
	}

	private void login() {

		final Me user = new Me();
		user.setEmail(mEmailEditText.getText().toString().trim());
		user.setPassword(mPasswordEditText.getText().toString().trim());

		JsonObjectRequest req = new JsonObjectRequest(Method.POST, VolleyApp.URL_LOGIN, user.toLoginJSON(),
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.i("VOLLEY", "Receive response " + response.toString());
						try {
							String token = response.getString("token");
							AppHelper.saveInSharedPreferences(LoginActivity.this, user.getEmail(), token);

							// Toast.makeText(LoginActivity.this,
							// "Vous etes connecté", Toast.LENGTH_LONG).show();

							Intent intent = new Intent(LoginActivity.this, MainActivity.class);
							startActivity(intent);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

						mProgressBar.setVisibility(View.GONE);
						mLoginButton.setVisibility(View.VISIBLE);

						onVolleyError(error);
					}

				});

		VolleyApp.getInstance().addToRequestQueue(req);
	}

	private void onVolleyError(VolleyError error) {
		String errorStr = error.toString();
		NetworkResponse networkResponse = error.networkResponse;

		if (networkResponse != null) {
			errorStr = AppHelper.getMessageForStatusCode(this, networkResponse.statusCode);
		} else {
			errorStr = getResources().getString(R.string.error_no_internet);
		}

		Toast.makeText(LoginActivity.this, errorStr, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.login_button) {
			if (!hasErrors()) {
				mProgressBar.setVisibility(View.VISIBLE);
				mLoginButton.setVisibility(View.GONE);
				login();
			}
		}
	}

}
