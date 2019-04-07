package com.example.googlemap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BusList extends AppCompatActivity {

    private Button taranga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_list);

        taranga= findViewById(R.id.taranga);
        taranga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapActivity();
            }
        });


    }

    public void openMapActivity(){
        Intent intent = new Intent(this,BusActivity.class);
        startActivity(intent);
    }
}
