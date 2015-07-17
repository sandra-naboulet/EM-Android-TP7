package com.myschool.tp5.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myschool.tp5.AppHelper;
import com.myschool.tp5.models.Message;
import com.myschool.tp7.R;

public class MessageAdapter extends ArrayAdapter<Message> {
	private final Context context;
	private final List<Message> mMessages;

	public MessageAdapter(Context context, List<Message> messages) {
		super(context, -1, messages);
		this.context = context;
		this.mMessages = messages;
	}

	public Message getMessage(int position) {
		return mMessages.get(position);
	}

	public void addMessage(Message msg) {
		mMessages.add(msg);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.item_message, parent, false);

		TextView creatorTextView = (TextView) rowView.findViewById(R.id.item_message_creator);
		TextView contentTextView = (TextView) rowView.findViewById(R.id.item_message_content);

		if (mMessages.get(position).getCreator() != null) {
			creatorTextView.setText(mMessages.get(position).getCreator().getName() + " dit : ");
		} else {

			creatorTextView.setText(AppHelper.getUserName(context) + " :");
		}

		contentTextView.setText(mMessages.get(position).getContent());

		return rowView;
	}
}
