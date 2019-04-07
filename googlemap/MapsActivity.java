package com.example.googlemap;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int LOCATION_REQUEST = 500;
    ArrayList<LatLng> listPoints;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        listPoints = new ArrayList<>();
    }

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



    }


     /*
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if(listPoints.size()==2){
                    listPoints.clear();
                    mMap.clear();
                }
                listPoints.add(latLng);

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);

                if(listPoints.size()==1){
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                }
                else{
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                }
                mMap.addMarker(markerOptions);

                //todo: request getDirectionCode

                if(listPoints.size()== 2){
                    //createURL
                    String url =  getRequestURL(listPoints.get(0),listPoints.get(1));

                    TaskRequestDirections taskRequestDirections = new TaskRequestDirections();
                    taskRequestDirections.execute(url);
                }
            }
        });
        */



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
                    mMap.addMarker(markerOptions);

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
