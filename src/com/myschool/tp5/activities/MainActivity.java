package com.myschool.tp5.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.myschool.tp5.VolleyApp;
import com.myschool.tp5.adapters.GroupAdapter;
import com.myschool.tp5.models.Group;
import com.myschool.tp5.volley.CustomJsonArrayRequest;
import com.myschool.tp5.volley.CustomJsonObjectRequest;
import com.myschool.tp7.R;

public class MainActivity extends ActionBarActivity {

	TextView mWelcomeTextView = null;
	ProgressBar mProgressBar = null;
	ListView mGroupsListView = null;

	String mUserName = null;
	String mUserEmail = null;
	String mUserToken = null;

	// ArrayAdapter<String> mGroupsAdapter;
	GroupAdapter mAdapter;
	List<Group> mGroups = new ArrayList<Group>();

	boolean isGettingUserInfos = false;
	boolean isGettingGroups = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mWelcomeTextView = (TextView) findViewById(R.id.welcome);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
		mGroupsListView = (ListView) findViewById(R.id.groups_list);
		mGroupsListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Group g = mAdapter.getGroup(position);
				Intent mIntent = new Intent(MainActivity.this, MessageActivity.class);
				Bundle mBundle = new Bundle();
				mBundle.putString("groupId", g.getId());

				mIntent.putExtras(mBundle);
				startActivity(mIntent);
			}
		});

	}

	@Override
	protected void onStart() {
		mProgressBar.setVisibility(View.VISIBLE);
		mWelcomeTextView.setVisibility(View.GONE);
		startGetUserInfos();
		startGetGroups();
		super.onStart();
	}

	@Override
	protected void onResume() {
		// updateDisplay();
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_logout) {
			AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
			alertDialog.setTitle(getResources().getString(R.string.confirmation));
			alertDialog.setMessage(getResources().getString(R.string.confirmation_msg));
			alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getResources().getString(R.string.ok),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							leaveTheApp();
						}
					});
			alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getResources().getString(R.string.cancel),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			alertDialog.show();

		} else if (id == R.id.action_add_group) {
			Intent intent = new Intent(MainActivity.this, AddGroupActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	private void leaveTheApp() {
		getSharedPreferences(VolleyApp.PREF_ACTIVE_USER, 0).edit().clear().commit();
		finish();
	}

	private void startGetUserInfos() {
		isGettingGroups = true;
		CustomJsonObjectRequest req = new CustomJsonObjectRequest(this, Method.GET, VolleyApp.URL_ME, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.i("VOLLEY", "Receive response " + response.toString());
						isGettingUserInfos = false;

						try {

							mUserName = response.getString("name");
							mUserEmail = response.getString("email");

							updateDisplay();
						} catch (JSONException e) {

						}

					}

				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i("VOLLEY", "Error getting user infos");
						isGettingUserInfos = false;
					}

				});

		VolleyApp.getInstance().addToRequestQueue(req);

	}

	private void startGetGroups() {
		isGettingGroups = true;
		CustomJsonArrayRequest req = new CustomJsonArrayRequest(this, VolleyApp.URL_GROUPS,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.i("VOLLEY", "Receive response " + response.toString());
						isGettingGroups = false;
						try {
							mGroups.clear();
							for (int i = 0; i < response.length(); i++) {
								Group g = Group.parseFromJson(response.getJSONObject(i));
								mGroups.add(g);
								mAdapter = new GroupAdapter(MainActivity.this, mGroups);
								mGroupsListView.setAdapter(mAdapter);
							}

							updateDisplay();
						} catch (JSONException e) {

						}

					}

				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i("VOLLEY", "Error getting user infos");
						isGettingGroups = false;
					}

				});

		VolleyApp.getInstance().addToRequestQueue(req);

	}

	private void updateDisplay() {
		if (!isGettingGroups && !isGettingUserInfos) {
			String welcomeMsg = "";
			if (mUserName != null) {
				welcomeMsg = getResources().getString(R.string.welcome).replace("%s", mUserName);
			} else {
				welcomeMsg = getResources().getString(R.string.welcome);
			}
			mWelcomeTextView.setText(welcomeMsg);
			mProgressBar.setVisibility(View.GONE);
			mWelcomeTextView.setVisibility(View.VISIBLE);
		}
	}

}
