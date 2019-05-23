package com.example.userlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<User> {


    private Context context;
    private List<User> users;

    public UserListAdapter(Context context, List<User> users){
        super(context, R.layout.user_row_layout, users);
        this.context = context;
        this.users = users;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        convertView = layoutInflater.inflate(R.layout.user_row_layout,parent,false);

        User user = users.get(position);
        TextView userNameText = (TextView) convertView.findViewById(R.id.userName);
        userNameText.setText(user.getUserName());
        TextView deptNameText = (TextView) convertView.findViewById(R.id.dept);

        deptNameText.setText(user.getDepartment());

        return convertView;
    }
}
