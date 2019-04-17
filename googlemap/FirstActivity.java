package com.example.googlemap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {
    private Button signIn;
    private Button signUp;
    public static final String MY_PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);




        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("name", "Elena");
        editor.putInt("idName", 12);
        editor.commit();

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
        if (restoredText != null) {
            String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
            int idName = prefs.getInt("idName", 0); //0 is the default value.


             Toast.makeText(this,"here name is "+name+ " "+idName,Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"lol",Toast.LENGTH_SHORT).show();



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





               // openMapActivity();
                gotoLoginPage();
                //openBusList();
            }
        }


        );
    }



    public void openMapActivity(){
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }


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
