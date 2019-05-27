package com.example.admin;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class Login extends AppCompatActivity {

    private Button login;
    private Button signUp;
    private EditText userName;
    private EditText pswd;
    private CheckBox satView;
    private boolean remember= false;
    private boolean serverCheck= false;
    public static final String loginCheck= "MyPrefsFile2";

    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




       // this.getSupportActionBar().hide();


        SharedPreferences prefs = getSharedPreferences(loginCheck, MODE_PRIVATE);
        remember = prefs.getBoolean("remember", false);
        serverCheck =  prefs.getBoolean("serverCheck", false);
        // Toast.makeText(Login.this,remember+ " "+serverCheck,Toast.LENGTH_SHORT).show();



        if(remember == true && serverCheck == true) openBusList();

        pswd = (EditText) findViewById(R.id.pswd3);
        userName = (EditText) findViewById(R.id.userName3);
        login =(Button)  findViewById(R.id.login);


        satView = (CheckBox)findViewById(R.id.remember);
        satView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){


                    SharedPreferences.Editor editor = getSharedPreferences(loginCheck, MODE_PRIVATE).edit();
                    editor.putBoolean("remember", true);
                    editor.commit();



                } else {
                    ///System.out.println("Un-Checked");
                }
            }
        });




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String Pswd, UserName;
                Pswd = pswd.getText().toString();
                UserName = userName.getText().toString();

                LoginCred loginCred = new LoginCred(UserName,Pswd);

                Toast.makeText(Login.this,"user name:"+UserName+" pass:"+ Pswd,Toast.LENGTH_SHORT).show();
                //  Toast.makeText(Login.this,"trying",Toast.LENGTH_SHORT).show();
                checkValidity(loginCred);

            }
        });



    }

    public void openSignUpPage()
    {
        Intent intent2 = new Intent(this,SignUp.class);
        startActivity(intent2);

    }



    public void openBusList(){
//        Intent intent = new Intent(this,BusList.class);
//        startActivity(intent);

        Intent intent2 = new Intent(this,MainActivity.class);
        startActivity(intent2);
    }

    public void  checkValidity(LoginCred loginCred)
    {
//"http://192.168.1.104:4000/"
//"http://52.29.113.22/rakib/")
        //192.168.1.121:4000/adminSignUp

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.121:4000/")
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
                else {

                    SharedPreferences.Editor editor = getSharedPreferences(loginCheck, MODE_PRIVATE).edit();
                    editor.putBoolean("serverCheck", true);
                    editor.commit();

                    openBusList();
                }


            }

            @Override
            public void onFailure(Call<LoginCred> call, Throwable t) {
                Toast.makeText(Login.this,"trying",Toast.LENGTH_SHORT).show();

            }
        });


    }
}
