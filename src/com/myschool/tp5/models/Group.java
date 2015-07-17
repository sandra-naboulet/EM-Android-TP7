package com.myschool.tp5.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Group {

	private String mId;
	private String mName;
	private String mCreator;
	private List<String> mEmails;

	public Group() {

	}

	public Group(String mName, List<String> mEmails) {
		super();
		this.mName = mName;
		this.mEmails = mEmails;
	}

	public String getId() {
		return mId;
	}

	public void setId(String id) {
		this.mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public String getCreator() {
		return mCreator;
	}

	public void setCreator(String mOwner) {
		this.mCreator = mOwner;
	}

	
	public List<String> getEmails() {
		return mEmails;
	}

	public String getEmailsStr() {
		String emailsStr = "";
		for (String email : getEmails()) {
			emailsStr += email + " ";
		}
		return emailsStr;
	}

	public void setEmails(List<String> mEmails) {
		this.mEmails = mEmails;
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		try {
			json.put("name", getName());
			JSONArray array = new JSONArray();
			for (String email : getEmails()) {
				array.put(email);
			}
			json.put("emails", array);
		} catch (JSONException e) {

		}
		return json;
	}

	public static Group parseFromJson(JSONObject json) {
		Group group = new Group();
		try {
			group.setId(json.getString("_id"));
			group.setCreator(json.getString("_creator"));
			group.setName(json.getString("name"));
			JSONArray emailsArray = json.getJSONArray("emails");
			List<String> emails = new ArrayList<String>();
			for (int i = 0; i < emailsArray.length(); i++) {
				emails.add(emailsArray.getString(i));
			}

			group.setEmails(emails);

		} catch (JSONException e) {

		}

		return group;
	}

}
