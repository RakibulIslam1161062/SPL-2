package com.example.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity {

    private String key;
    private ListView listViewUserList;
    private List<User> users;
    public static final String listPref= "MyPrefsFile";
    private  String url = "http:192.168.1.121:4000/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        users = new ArrayList<User>();
        listViewUserList = (ListView) findViewById(R.id.listView);

        Intent intent = getIntent();
        key = intent.getExtras().getString("key");
        Toast.makeText(UserList.this,"here: "+key,Toast.LENGTH_SHORT).show();

        if(key.equalsIgnoreCase("admin")) {

            url ="http:192.168.1.121:4000/adminUsers";
        }
        else url="http:192.168.1.121:4000/users";

        SharedPreferences.Editor editor = getSharedPreferences(listPref, MODE_PRIVATE).edit();
        editor.putString("whichList", key);
        editor.commit();

        loadRecyclerData();

    }

    private void loadRecyclerData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();

                        try {
                            JSONArray array = new JSONArray(s);
                            for (int i = 0 ; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);

                                // Toast.makeText(MainActivity.this,"here: "+o.getString("name"),Toast.LENGTH_SHORT).show();

//                                if(o.getString("name").equals(null) || o.getString("dept").equals(null) ||o.getString("").equals(null) )
//                                    continue;


//
                                User user = new User( o.getString("name"),
                                        o.getString("dept"), o.getString("_id"));
                                users.add(user);

                                //Toast.makeText(MainActivity.this,"here: "+user.getUserName(),Toast.LENGTH_SHORT).show();
                            }
                            listViewUserList.setAdapter(new UserListAdapter(getApplicationContext(), users));

                            // Toast.makeText(MainActivity.this,"hey"+s,Toast.LENGTH_SHORT).show();
//                            adapter = new MyAdapter(listItems,getApplicationContext());
//                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
