package com.example.googlemap;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    public static final String locationPref= "MyPrefsFile";
    Handler mHandler;
    private GoogleMap mMap;
    private static final int LOCATION_REQUEST = 500;
    ArrayList<LatLng> listPoints;
    double lat=0.9920,lon=0.222;

    double l1=23.00,l2=90.343;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        this.mHandler = new Handler();
        m_Runnable.run();

        listPoints = new ArrayList<>();
    }

    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            getDbData();
           // Toast.makeText(MapsActivity.this,"in runnable",Toast.LENGTH_SHORT).show();

            MapsActivity.this.mHandler.postDelayed(m_Runnable,10000);
        }

    };



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
        return;
    }
        mMap.setMyLocationEnabled(true);



            getDbData();
           geoFence();




       // Toast.makeText(MapsActivity.this,"here is "+destination,Toast.LENGTH_SHORT).show();




    }



    @Override
    public void onResume(){
        super.onResume();

        if(mMap != null){ //prevent crashing if the map doesn't exist yet (eg. on starting activity)
            mMap.clear();

            getDbData();

            // add markers from database to the map
        }
    }



    public void geoFence(){




        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if(listPoints.size()==1){
                    listPoints.clear();
                    mMap.clear();
                }
                listPoints.add(latLng);
               lat= latLng.latitude;
               lon= latLng.longitude;

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);

                if(listPoints.size()==1){
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    CircleOptions circleOptions = new CircleOptions()
                            .strokeColor(Color.BLACK) //Outer black border
                            .fillColor(Color.TRANSPARENT) //inside of the geofence will be transparent, change to whatever color you prefer like 0x88ff0000 for mid-transparent red
                            .center(latLng) // the LatLng Object of your geofence location
                            .radius(500); // The radius (in meters) of your geofence

                    Circle circle = mMap.addCircle(circleOptions);
                }
                else{
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                }
                mMap.addMarker(markerOptions);


                Button btn = findViewById(R.id.btn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        Location myLocation = mMap.getMyLocation();
                         l1 = myLocation.getLatitude();
                         l2 = myLocation.getLongitude();

                         destinationAlert(l1,l2,lat,lon);


                       // Toast.makeText(MapsActivity.this,"here"+l+" "+l2,Toast.LENGTH_LONG).show();
                    }
                });


                //todo: request getDirectionCode

              /*  if(listPoints.size()== 2){
                    //createURL
                    String url =  getRequestURL(listPoints.get(0),listPoints.get(1));

                    TaskRequestDirections taskRequestDirections = new TaskRequestDirections();
                    taskRequestDirections.execute(url);
                }
                */
            }
        });



    }

    public void destinationAlert(double lat1,double lon1,double lat2,double lon2) {

        float[] results = new float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        float distanceInMeters = results[0];
        boolean isWithin10km = distanceInMeters < 10000;

        long l1, l2, l3, l4;


        l1 = (new Double(lat1)).longValue();
        l2 = (new Double(lon1)).longValue();
        l3 = (new Double(lat2)).longValue();
        l4 = (new Double(lon2)).longValue();


        SharedPreferences.Editor editor = getSharedPreferences(locationPref, MODE_PRIVATE).edit();
        editor.putLong("myLat", l1);
        editor.putLong("myLon", l2);
        editor.putLong("destLat", l3);
        editor.putLong("destLon", l4);
        editor.commit();



//        SharedPreferences.Editor editor = getSharedPreferences(locationPref, MODE_PRIVATE).edit();
//        editor.putString("name", "Elena");
//        editor.putInt("idName", 12);
//        editor.commit();


        String input = "alarm";

        Intent serviceIntent = new Intent(this, ExampleService.class);
        serviceIntent.putExtra("inputExtra",input);

        startService(serviceIntent);

        ContextCompat.startForegroundService(this, serviceIntent);


        //startService(new Intent(this,ExampleService.class));


    }

/*
        if(isWithin10km)
        {
            Toast.makeText(MapsActivity.this,"here is 10 km",Toast.LENGTH_SHORT).show();
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
            //shared Preference Data

        }

        else
            Toast.makeText(MapsActivity.this,"here is no",Toast.LENGTH_SHORT).show();
    }
    */


    public void startService(View v){

        Toast.makeText(this,"yes started",Toast.LENGTH_SHORT).show();

        String input = "alarm";

        Intent serviceIntent = new Intent(this, ExampleService.class);
        serviceIntent.putExtra("inputExtra",input);

        startService(serviceIntent);

        ContextCompat.startForegroundService(this, serviceIntent);


    }

    public void stopService(View v) {
        Intent serviceIntent = new Intent(this, ExampleService.class);
        stopService(serviceIntent);

    }


    private String getRequestURL(LatLng origin, LatLng dest) {
        //value of origin
        String str_org = "origin="+origin.latitude+","+origin.longitude;

        //dest
        String str_dest = "destination="+ dest.latitude+","+dest.longitude;

        //enable sensor
        String sensor="sensor=false";
        String mode = "mode=driving";

        //full param

        String param = str_org +"&"+str_dest +"&" +sensor+"&"+mode;
        String output = "json";
        String key ="&key="+"AIzaSyDE7gdHOEnyB5--XGLW8eWeLE-9HW3n3XQ";
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+param+key;
        //System.out.print(url);
        //Toast.makeText(getApplicationContext(),"Direction n"+url,Toast.LENGTH_SHORT).show();
        return url;

        //https://maps.googleapis.com/maps/api/directions/origin=23.565,90.321113


    }

    private  String requestDirection(String reqUrl) throws IOException {
        String responseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;

        try{

            URL url = new URL(reqUrl);
            httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);

            }
            responseString = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();

        } catch (Exception e){
            e.printStackTrace();

        } finally {
            if(inputStream != null){
                inputStream.close();
            }
            httpURLConnection.disconnect();
        }

        return responseString;


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] granResults) {
        switch (requestCode) {
            case LOCATION_REQUEST:
                if (granResults.length > 0 && granResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    mMap.setMyLocationEnabled(true);
                }
                break;

        }
    }

    public class  TaskRequestDirections extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... strings) {
            String responseString ="";
            try {
                responseString = requestDirection(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //parse json
            TaskParser taskParser = new TaskParser();
            taskParser.execute(s);


        }
    }

    public class TaskParser extends  AsyncTask<String,Void, List<List<HashMap<String,String>>> >{

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject = null;
            List<List<HashMap<String,String>>> routes = null;
            try {
                jsonObject = new JSONObject(strings[0]);
                DirectionsParser directionsParser = new DirectionsParser();
                routes = directionsParser.parse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            //super.onPostExecute(lists);
            //list route and display here bro

            ArrayList points = null;

            PolylineOptions polylineOptions = null;

            for (List<HashMap<String, String>> path : lists ){
                points = new ArrayList();
                polylineOptions = new PolylineOptions();

                for (HashMap<String, String>  point : path){
                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lon"));


                    points.add(new LatLng(lat,lon));
                }
                polylineOptions.addAll(points);
                polylineOptions.width(15);
                polylineOptions.color(Color.BLUE);
                polylineOptions.geodesic(true);

            }
            if(polylineOptions!= null) {
                mMap.addPolyline(polylineOptions);

            } else {
                Toast.makeText(getApplicationContext(),"Direction not found",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getDbData()
    {
        // List<LatLng> listLL ;

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        //Call<List<LatLon>> call = api.getLatlons();



            Call<LatLon> call = api.getLatlons();

            call.enqueue(new Callback<LatLon>() {
                @Override
                public void onResponse(Call<LatLon> call, Response<LatLon> response) {


                    LatLon latlonList = response.body();
                    LatLng myLatLng = new LatLng(latlonList.getLat(), latlonList.getLon());


                    MarkerOptions markerOptions = new MarkerOptions();
                    //markerOptions.position(listPoints.get(0));markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    /// mMap.addMarker(markerOptions);

                    markerOptions.position(myLatLng);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
                    markerOptions.title(latlonList.getLat() + " : " + latlonList.getLon());
                    mMap.clear();
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(myLatLng));


                   // public void getAddress(double lat, double lng) {
                        Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(latlonList.getLat(), latlonList.getLon(), 1);
                            Address obj = addresses.get(0);
                            String add = obj.getAddressLine(0);
//                            add = add + "\n" + obj.getCountryName();
//                            add = add + "\n" + obj.getCountryCode();
//                            add = add + "\n" + obj.getAdminArea();
//                            add = add + "\n" + obj.getPostalCode();
//                            add = add + "\n" + obj.getSubAdminArea();
                            add = add + "\n" + obj.getLocality();
                            add = add + "\n" + obj.getSubThoroughfare();

//                            markerOptions.showInfoWindow();

                            mMap.addMarker(markerOptions.title(add)).showInfoWindow();


                          //  Log.v("IGA", "Address" + add);
                            // Toast.makeText(MapsActivity.this, "Address=>" + add,Toast.LENGTH_SHORT).show();


                            // TennisAppActivity.showDialog(add);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            //Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }



                        //text






                    CircleOptions circleOptions = new CircleOptions()
                            .strokeColor(Color.BLACK) //Outer black border
                            .fillColor(Color.TRANSPARENT) //inside of the geofence will be transparent, change to whatever color you prefer like 0x88ff0000 for mid-transparent red
                            .center(myLatLng) // the LatLng Object of your geofence location
                            .radius(500); // The radius (in meters) of your geofence

                   // Circle circle = mMap.addCircle(circleOptions);

                    //String url = getRequestURL(listPoints.get(0), listPoints.get(listPoints.size() - 1));

                    //TaskRequestDirections taskRequestDirections = new TaskRequestDirections();
                    //taskRequestDirections.execute(url);
                    Toast.makeText(MapsActivity.this, "You are right" + latlonList.getLat(), Toast.LENGTH_SHORT).show();


                }

                @Override
                public void onFailure(Call<LatLon> call, Throwable t) {
                    Toast.makeText(MapsActivity.this, "Something went wrong on getting data", Toast.LENGTH_SHORT).show();

                }
            });




    }


}
