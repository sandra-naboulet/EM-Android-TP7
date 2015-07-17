package com.myschool.tp5.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Message {

	private String mId;
	private String mGroupId;
	private String mContent;
	private User mCreator;

	public Message() {

	}

	public Message(String groupID, String content) {
		mGroupId = groupID;
		mContent = content;
	}

	public String getId() {
		return mId;
	}

	public void setId(String mId) {
		this.mId = mId;
	}

	public String getGroupId() {
		return mGroupId;
	}

	public void setGroupId(String mGroupId) {
		this.mGroupId = mGroupId;
	}

	public String getContent() {
		return mContent;
	}

	public void setContent(String mContent) {
		this.mContent = mContent;
	}

	public User getCreator() {
		return mCreator;
	}

	public void setCreator(User creator) {
		this.mCreator = creator;
	}

	public static Message parseFromJSON(JSONObject json, boolean isFromMe) {
		Message msg = new Message();
		try {
			msg.setId(json.getString("_id"));
			msg.setGroupId(json.getString("group"));
			msg.setContent(json.getString("content"));
			msg.setCreator(User.parseFromJSON(json.getJSONObject("_creator")));
		} catch (JSONException e) {

		}
		return msg;
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		try {
			json.put("group", getGroupId());
			json.put("content", getContent());

		} catch (JSONException e) {

		}
		return json;
	}

	public static List<Message> getMessagesListFromJSONArray(JSONArray array) {
		List<Message> messages = new ArrayList<Message>();
		try {
			for (int i = 0; i < array.length(); i++) {
				messages.add(parseFromJSON(array.getJSONObject(i), false));
			}
		} catch (JSONException e) {

		}
		return messages;
	}
}
