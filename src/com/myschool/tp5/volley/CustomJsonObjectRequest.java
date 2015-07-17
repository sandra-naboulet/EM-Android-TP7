package com.myschool.tp5.volley;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.myschool.tp5.AppHelper;

public class CustomJsonObjectRequest extends JsonObjectRequest {

	private Context mContext = null;

	public CustomJsonObjectRequest(Context context, int method, String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, jsonRequest, listener, errorListener);
		mContext = context;

	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "Bearer " + AppHelper.getSessionToken(mContext));
		return headers;
	}

}
