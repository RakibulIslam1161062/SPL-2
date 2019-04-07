package com.example.googlemap;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL = "http://52.29.113.22/rakib/";

    @GET("ninjas")
    //Call<List<LatLon>> getLatlons();
   Call<LatLon> getLatlons();

}
