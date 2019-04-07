package com.example.googlemap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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

                if(!Pswd.equals(Pswd2))
                {
                    Toast.makeText(SignUp.this,"Check your Password",Toast.LENGTH_SHORT).show();

                }

                else
                {
                    SignUpPost signUpPost= new SignUpPost(Name,Dept,UserName,Pswd);
                    callPost(signUpPost);
                }
            }
        });

    }

    public void callPost(SignUpPost signUpPost){

     //   "http://52.29.113.22/rakib/"
    //"http://127.0.0.1:4000/"
        //"http://192.168.1.104:4000/"

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://52.29.113.22/rakib/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ApiForSignUp  api = retrofit.create(ApiForSignUp.class);
        Call<SignUpPost> call = api.getSignUpPost(signUpPost);

        call.enqueue(new Callback<SignUpPost>() {
            @Override
            public void onResponse(Call<SignUpPost> call, Response<SignUpPost> response) {
                Toast.makeText(SignUp.this, " Yes Yes !right"+response.body(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SignUpPost> call, Throwable t) {
                Toast.makeText(SignUp.this, "Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });



    }
}
