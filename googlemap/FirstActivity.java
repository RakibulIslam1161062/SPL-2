package com.example.googlemap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstActivity extends AppCompatActivity {
    private Button signIn;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        signIn = findViewById(R.id.button);
        signUp = findViewById(R.id.signUp);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpPage();


            }
        });


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openMapActivity();
                gotoLoginPage();
                //openBusList();
            }
        }


        );
    }

    /*public void openMapActivity(){
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }*/


    private void gotoLoginPage() {

        Intent intent;
        intent = new Intent(this,Login.class);
        startActivity(intent);
    }



    public void openSignUpPage()
    {
        Intent intent2 = new Intent(this,SignUp.class);
        startActivity(intent2);
    }



}
