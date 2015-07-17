package com.myschool.tp5.activities;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.myschool.tp5.VolleyApp;
import com.myschool.tp5.models.Group;
import com.myschool.tp5.volley.CustomJsonObjectRequest;
import com.myschool.tp7.R;

public class AddGroupActivity extends Activity {

	EditText mGroupNameEditText;
	EditText mGroupUsersEditText;
	Button mAddButton;
	ProgressBar mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_group);

		mGroupNameEditText = (EditText) findViewById(R.id.group_name);
		mGroupUsersEditText = (EditText) findViewById(R.id.group_users);
		mAddButton = (Button) findViewById(R.id.add_button);
		mAddButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!hasErrors()) {
					mProgressBar.setVisibility(View.VISIBLE);
					mAddButton.setVisibility(View.GONE);
					startCreateGroup();
				}
			}
		});
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
	}

	private boolean hasErrors() {

		String inputName = mGroupNameEditText.getText().toString().trim();

		if (inputName.isEmpty()) {
			mGroupNameEditText.setError(getResources().getString(R.string.error_group_name));
			return true;
		}

		return false;
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {

		super.onResume();
	}

	private List<String> getEmailsList() {
		String[] emails = mGroupUsersEditText.getText().toString().trim().split(",");
		return Arrays.asList(emails);
	}

	private void startCreateGroup() {

		final Group group = new Group();
		group.setName(mGroupNameEditText.getText().toString().trim());
		group.setEmails(getEmailsList());

		CustomJsonObjectRequest req = new CustomJsonObjectRequest(this, Method.POST, VolleyApp.URL_GROUPS,
				group.toJSON(), new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {

						Toast.makeText(AddGroupActivity.this, "Groupe ajouté", Toast.LENGTH_LONG).show();
						mProgressBar.setVisibility(View.GONE);
						mAddButton.setVisibility(View.VISIBLE);
						finish();

					}

				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

						mProgressBar.setVisibility(View.GONE);
						mAddButton.setVisibility(View.VISIBLE);
						Toast.makeText(AddGroupActivity.this, "error", Toast.LENGTH_LONG).show();

					}

				});

		VolleyApp.getInstance().addToRequestQueue(req);

	}
}
