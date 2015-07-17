package com.myschool.tp5.volley;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.myschool.tp5.AppHelper;

public class CustomJsonArrayRequest extends JsonArrayRequest {

	private Context mContext = null;

	public CustomJsonArrayRequest(Context context, String url, Listener<JSONArray> listener, ErrorListener errorListener) {
		super(url, listener, errorListener);
		mContext = context;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "Bearer " + AppHelper.getSessionToken(mContext));
		return headers;
	}

}
