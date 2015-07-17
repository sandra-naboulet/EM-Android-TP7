package com.myschool.tp5.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

	private String mId;
	private String mName;

	public String getId() {
		return mId;
	}

	public void setId(String mId) {
		this.mId = mId;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public boolean isMe(User user){
		return false;
	}
	
	public static User parseFromJSON(JSONObject json) {
		User user = new User();
		try {
			user.setId(json.getString("_id"));
			user.setName(json.getString("name"));
		} catch (JSONException e) {

		}
		return user;
	}

}
