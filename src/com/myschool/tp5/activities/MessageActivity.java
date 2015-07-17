package com.myschool.tp5.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.myschool.tp5.AppHelper;
import com.myschool.tp5.VolleyApp;
import com.myschool.tp5.adapters.MessageAdapter;
import com.myschool.tp5.models.Group;
import com.myschool.tp5.models.Message;
import com.myschool.tp5.volley.CustomJsonArrayRequest;
import com.myschool.tp5.volley.CustomJsonObjectRequest;
import com.myschool.tp7.R;

public class MessageActivity extends ActionBarActivity implements OnClickListener {

	private String mGroupId;
	private MessageAdapter mMessageAdapter;
	private List<Message> mMessages = new ArrayList<Message>();

	private ListView mMessageListView;
	private EditText mMessageEditText;
	private Button mSendButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		getSupportActionBar().setTitle("");
		mGroupId = getIntent().getExtras().getString("groupId");

		mMessageListView = (ListView) findViewById(R.id.messages_list);
		mMessageAdapter = new MessageAdapter(this, mMessages);
		mMessageEditText = (EditText) findViewById(R.id.message_edit);
		mSendButton = (Button) findViewById(R.id.message_send_button);
		mSendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendMessage();
			}
		});
	}

	@Override
	protected void onStart() {
		startGetGroup();
		super.onStart();
	}

	@Override
	protected void onResume() {

		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.create_button) {

		}
	}

	private void startGetGroup() {

		CustomJsonObjectRequest req = new CustomJsonObjectRequest(this, Method.GET, VolleyApp.URL_GROUPS + "/"
				+ mGroupId, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.i("VOLLEY", "Receive response " + response.toString());
				Group group = Group.parseFromJson(response);
				getSupportActionBar().setTitle(group.getName());
				startGetMessages();
			}

		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

				onVolleyError(error);

			}

		});

		VolleyApp.getInstance().addToRequestQueue(req);
	}

	private void startGetMessages() {

		CustomJsonArrayRequest req = new CustomJsonArrayRequest(this, VolleyApp.URL_GROUPS + "/" + mGroupId
				+ "/messages", new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				Log.i("VOLLEY", "Receive response " + response.toString());
				mMessages = Message.getMessagesListFromJSONArray(response);
				mMessageAdapter = new MessageAdapter(MessageActivity.this, mMessages);
				mMessageListView.setAdapter(mMessageAdapter);
			}

		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

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

		Toast.makeText(MessageActivity.this, errorStr, Toast.LENGTH_LONG).show();
	}

	public void sendMessage() {
		Message msg = new Message(mGroupId, mMessageEditText.getText().toString());
		mMessageEditText.setText("");
		mSendButton.setOnClickListener(null);
		mSendButton.setTextColor(getResources().getColor(R.color.gray));
		mMessageAdapter.add(msg);

		CustomJsonObjectRequest req = new CustomJsonObjectRequest(MessageActivity.this, Method.POST,
				VolleyApp.URL_MESSAGES, msg.toJSON(), new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.i("VOLLEY", "Receive response " + response.toString());

						mSendButton.setTextColor(MessageActivity.this.getResources().getColor(R.color.black));

						startGetMessages();
					}

				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

						onVolleyError(error);

					}

				});

		VolleyApp.getInstance().addToRequestQueue(req);
	}
}
