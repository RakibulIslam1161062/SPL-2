package com.example.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Notification extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    public EditText adminName;
    public EditText adminDesig;
    public EditText message;
    public ImageView send;


    public GifImageView gifImageView;
    public String busNameS;
    public String adminNameS;
    public String adminDesigS;
    public String messageS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        gifImageView = (GifImageView) findViewById(R.id.tick);


        adminName = (EditText) findViewById(R.id.adminName);
        adminDesig = (EditText) findViewById(R.id.adminDesig);
        message = (EditText) findViewById(R.id.message2);

        send = (ImageView) findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminNameS = adminName.getText().toString();
                adminDesigS = adminDesig.getText().toString();
                String editStr= message.getText().toString().trim();
                messageS = editStr.replaceAll("\n"," ");

                NotificationItem  notificationItem = new NotificationItem(busNameS, adminNameS , adminDesigS , messageS);

                Toast.makeText(Notification.this,busNameS+" "+adminNameS+" "+ adminDesigS+" "+messageS,Toast.LENGTH_SHORT).show();
                sendNotification(notificationItem);


            }
        });





    }



    public void sendNotification(NotificationItem notificationItem){

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://52.29.113.22/rakib/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ApiForSendingNotification  api = retrofit.create(ApiForSendingNotification.class);
        Call<NotificationItem> call = api.getNotificationItem(notificationItem);

        call.enqueue(new Callback<NotificationItem>() {
            @Override
            public void onResponse(Call<NotificationItem> call, Response<NotificationItem> response) {
                // Toast.makeText(Login.this, " Yes Yes !right"+response.body().getUserName(),Toast.LENGTH_SHORT).show();

                gifImageView.setVisibility(View.VISIBLE);

                Intent intent = new Intent(Notification.this, MainActivity.class);
                startActivity(intent);




            }

            @Override
            public void onFailure(Call<NotificationItem> call, Throwable t) {
                Toast.makeText(Notification.this,"trying",Toast.LENGTH_SHORT).show();

            }
        });



    }

    public void  gotoSleep() throws InterruptedException {
        Thread.sleep(4000);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        busNameS = text;
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
