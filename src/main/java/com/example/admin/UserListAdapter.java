package com.example.admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.content.SharedPreferences;
import static android.content.Context.MODE_PRIVATE;
import static com.example.admin.UserList.listPref;

public class UserListAdapter extends ArrayAdapter<User> {


    private Context context;
    private List<User> users;
    private String id;
    SharedPreferences prefs = this.getContext().getSharedPreferences(listPref, MODE_PRIVATE);

    final String key = prefs.getString("whichList", "");


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



        Button btButton = (Button) convertView.findViewById(R.id.btn);
        // Cache row position inside the button using `setTag`
        btButton.setTag(position);
        // Attach the click event handler
        btButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                // Access the row position here to get the correct data item

                User user = getItem(position);

               // Toast.makeText(context, "you Clicked  on "+user.get_id(),Toast.LENGTH_SHORT).show();
                id= user.get_id();
                deleteRequest(id);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String key2 = key;
                Intent intent = new Intent(context.getApplicationContext(), UserList.class);
                intent.putExtra("key",key);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);







                // Do what you want here...
            }
        });



        return convertView;
    }




    public void deleteRequest(String _id){


        //"http:192.168.1.121:4000/users"
        //"http://52.29.113.22/rakib/"
        //String str ="http:192.168.1.121:4000/"




        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http:192.168.1.121:4000/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        Id id = new Id(_id);
        ApiForDeleteUser  api = retrofit.create(ApiForDeleteUser.class);
        Call<Id> call;
        if(key.equalsIgnoreCase("admin"))
        {
           call = api.adminDeletePost(id);
        }
        else {
            call = api.deletePost(id);
        }
       // Call<Id> call = api.deletePost(id);

        call.enqueue(new Callback<Id>() {
            @Override
            public void onResponse(Call<Id> call, Response<Id> response) {
                //Toast.makeText(UserListAdapter.this,"yes ",Toast.LENGTH_LONG).show();
               // Toast.makeText(context, "you Clicked  on "+ response.body(),Toast.LENGTH_SHORT).show();
               // LayoutInflater layoutInflater = LayoutInflater.from(context);
                Toast.makeText(context, "User Deleted",Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<Id> call, Throwable t) {


            }
        });


    }
}
