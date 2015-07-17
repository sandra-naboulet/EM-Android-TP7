package com.myschool.tp5;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyApp extends Application {

	public static final String TAG = VolleyApp.class.getSimpleName();
	private static VolleyApp sInstance = null;
	
	public final static String PREF_ACTIVE_USER = "ACTIVE_USER";
	
	private static final String URL_BASE = "http://questioncode.fr:10007";
	public static final String URL_LOGIN = URL_BASE + "/auth/local";
	public static final String URL_USERS = URL_BASE + "/api/users";
	public static final String URL_ME = URL_USERS + "/me";
	public static final String URL_GROUPS = URL_BASE + "/api/groups";
	public static final String URL_MESSAGES = URL_BASE + "/api/messages";

	private RequestQueue mRequestQueue;
	
	@Override
	public void onCreate() {
		sInstance = this;
		super.onCreate();
	}

	public static synchronized VolleyApp getInstance() {
		if (sInstance == null) {
			sInstance = new VolleyApp();
		}
		return sInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue((VolleyApp)getApplicationContext());
		}

		return mRequestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
		
	}

}
