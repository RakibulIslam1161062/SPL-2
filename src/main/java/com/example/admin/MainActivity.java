package com.example.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import static com.example.admin.Login.loginCheck;

public class MainActivity extends AppCompatActivity {

    public ImageView notificationButton;
    public ImageView userListButton;
    public ImageView addAdminButton;
    public ImageView RemoveAdminButton;
    public ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationButton = (ImageView) findViewById(R.id.notification);

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Notification.class);
                startActivity(intent);
            }
        });

        userListButton = (ImageView) findViewById(R.id.userList);

        userListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key = "user";
                Intent intent = new Intent(MainActivity.this,UserList.class);
                intent.putExtra("key",key);
                startActivity(intent);
            }
        });

        addAdminButton = (ImageView) findViewById(R.id.addAdmin);

        addAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);
            }
        });

        RemoveAdminButton = (ImageView) findViewById(R.id.removeAdmin);

        RemoveAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = "admin";
                Intent intent = new Intent(MainActivity.this,UserList.class);
                intent.putExtra("key",key);
                startActivity(intent);
            }
        });
       logout = (ImageView) findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = getSharedPreferences(loginCheck, MODE_PRIVATE).edit();
                editor.putBoolean("serverCheck", false);
                editor.commit();

                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
            }
        });



    }
}
