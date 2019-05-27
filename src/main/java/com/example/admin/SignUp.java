package com.example.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {

    private EditText name;
    private EditText dept;
    private EditText userName;
    private EditText pswd;
    private EditText pswd2;
    private Button submit;
    private boolean flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //this.getSupportActionBar().hide();

        name = (EditText) findViewById(R.id.name);
        dept = (EditText) findViewById(R.id.dept);
        userName=(EditText)  findViewById(R.id.userName);
        pswd = (EditText) findViewById(R.id.pswd);
        pswd2 = (EditText) findViewById(R.id.pswd2);
        submit =(Button)  findViewById(R.id.submitButton);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name,Dept,UserName,Pswd,Pswd2;
                Name = name.getText().toString();
                Dept = dept.getText().toString();
                UserName = userName.getText().toString();
                Pswd = pswd.getText().toString();
                Pswd2 = pswd2.getText().toString();


                // Toast.makeText(SignUp.this,"Check your Password"+Name,Toast.LENGTH_SHORT).show();

                if(Name.equals("") || Dept.equals("") || UserName.equals("")){
                    Toast.makeText(SignUp.this,"Fill up all the required fields",Toast.LENGTH_LONG).show();
                }
                else if(!Pswd.equals(Pswd2))
                {
                    Toast.makeText(SignUp.this,"Check your Password",Toast.LENGTH_SHORT).show();

                }

                else
                {
                    SignUpPost signUpPost= new SignUpPost(Name,Dept,"Taranga",UserName,Pswd);
                    callPost(signUpPost);

                }
            }




        });

    }

    private void gotoLoginPage() {

        Intent intent;
        intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public boolean callPost(SignUpPost signUpPost){

        //   "http://52.29.113.22/rakib/"
        //"http://127.0.0.1:4000/"
        //"http://192.168.1.104:4000/"
//192.168.1.121:4000/signUp
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.121:4000/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ApiForSignUp  api = retrofit.create(ApiForSignUp.class);
        Call<SignUpPost> call = api.getSignUpPost(signUpPost);

        call.enqueue(new Callback<SignUpPost>() {
            @Override
            public void onResponse(Call<SignUpPost> call, Response<SignUpPost> response) {
                Toast.makeText(SignUp.this, " Yes Yes !right"+response.body(),Toast.LENGTH_SHORT).show();
                flag=true;
                gotoLoginPage();
            }

            @Override
            public void onFailure(Call<SignUpPost> call, Throwable t) {
                Toast.makeText(SignUp.this, "Something went wrong",Toast.LENGTH_SHORT).show();
                flag=false;
            }
        });


        if(flag) return true;
        else return false;
    }
}
