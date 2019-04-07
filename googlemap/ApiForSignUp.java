package com.example.googlemap;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.Call;

public interface ApiForSignUp {


    @POST("signup")
        //Call<List<LatLon>> getLatlons();
    Call<SignUpPost> getSignUpPost(@Body SignUpPost signUpPost);

}
