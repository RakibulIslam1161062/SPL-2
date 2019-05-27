package com.example.admin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiForSignUp {


    @POST("AdminSignup")
        //Call<List<LatLon>> getLatlons();
    Call<SignUpPost> getSignUpPost(@Body SignUpPost signUpPost);

}
