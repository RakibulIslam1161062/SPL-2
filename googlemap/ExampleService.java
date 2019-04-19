package com.example.googlemap;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;


import static com.example.googlemap.App.CHANNEL_ID;
import static com.example.googlemap.MapsActivity.locationPref;

public class ExampleService extends Service {
    public int a=0;
    public boolean flag = false;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


            flag= true;






            String input = intent.getStringExtra("inputExtra");

            Intent notificationIntent = new Intent(this, MapsActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, notificationIntent, 0);



            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Example Service")
                    .setContentText(input)
                    .setSmallIcon(R.drawable.bg)
                    .setContentIntent(pendingIntent)
                    .build();

            startForeground(1, notification);


        new Thread(new Runnable(){
            public void run() {
                // TODO Auto-generated method stub
                while(flag)
                {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //REST OF CODE HERE//


                    SharedPreferences prefs = getSharedPreferences(locationPref, MODE_PRIVATE);

                   // String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
                    final Long myLat = prefs.getLong("myLat", 0);
                    final Long myLon = prefs.getLong("myLon", 0);
                    final Long destLat = prefs.getLong("destLat", 0);
                    final Long destLon = prefs.getLong("destLon", 0);

                    float[] results = new float[1];
                    Location.distanceBetween(myLat, myLon, destLat, destLon, results);
                    float distanceInMeters = results[0];
                    boolean isWithin10km = distanceInMeters < 10000;


                   if(isWithin10km) {


                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(ExampleService.this.getApplicationContext(), "lat:"+myLat, Toast.LENGTH_SHORT).show();

                                playAlarm();



                            }
                        });

                   }



                }

            }
        }).start();





        return START_NOT_STICKY;


        //do heavy work on a background thread
/*
        if (input1.equals("1"))
        {
            stopSelf();
        }
        */

/*
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
               a++;
               if(a==5)
               stopSelf();
            }
        }, 2000);


*/






    }

    @Override
    public void onDestroy() {
        flag =false;
        super.onDestroy();

    }

    public void playAlarm()
    {
        flag = false;
//        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
//        r.play();
        MediaPlayer player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        player.setLooping(false);
        player.start();

    }

    //@androidx.annotation.Nullable
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
