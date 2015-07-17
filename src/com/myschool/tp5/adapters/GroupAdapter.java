package com.myschool.tp5.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myschool.tp5.models.Group;
import com.myschool.tp7.R;

public class GroupAdapter extends ArrayAdapter<Group> {
	private final Context context;
	private final List<Group> groups;

	public GroupAdapter(Context context, List<Group> groups) {
		super(context, -1, groups);
		this.context = context;
		this.groups = groups;
	}
	
	public Group getGroup(int position){
		return groups.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.item_group, parent, false);

		TextView nameTextView = (TextView) rowView.findViewById(R.id.item_group_name);
		TextView emailsTextView = (TextView) rowView.findViewById(R.id.item_group_emails);

		nameTextView.setText(groups.get(position).getName());
		emailsTextView.setText(groups.get(position).getCreator());

		return rowView;
	}
}
