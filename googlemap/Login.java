package com.example.googlemap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {



    private EditText userName;
    private EditText pswd;
    private Button login;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        pswd = (EditText) findViewById(R.id.pswd3);
        userName = (EditText) findViewById(R.id.userName3);
        login =(Button)  findViewById(R.id.login);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Pswd, UserName;
                Pswd = pswd.getText().toString();
                UserName = userName.getText().toString();

                LoginCred loginCred = new LoginCred(UserName,Pswd);

                checkValidity(loginCred);

            }
        });





    }


    public void openBusList(){
        Intent intent = new Intent(this,BusList.class);
        startActivity(intent);
    }

    public void  checkValidity(LoginCred loginCred)
    {
//"http://192.168.1.104:4000/"
//"http://52.29.113.22/rakib/")

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://52.29.113.22/rakib/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ApiForLogin  api = retrofit.create(ApiForLogin.class);
        Call<LoginCred> call = api.getLoginPost(loginCred);

        call.enqueue(new Callback<LoginCred>() {
            @Override
            public void onResponse(Call<LoginCred> call, Response<LoginCred> response) {
               // Toast.makeText(Login.this, " Yes Yes !right"+response.body().getUserName(),Toast.LENGTH_SHORT).show();
                if(response.body().getUserName().equals("invalid") && response.body().getPassword().equals("invalid"))
                    Toast.makeText(Login.this, "Invalid username of password ",Toast.LENGTH_SHORT).show();
                else openBusList();


            }

            @Override
            public void onFailure(Call<LoginCred> call, Throwable t) {
                Toast.makeText(Login.this,"trying",Toast.LENGTH_SHORT).show();

            }
        });


    }
}
