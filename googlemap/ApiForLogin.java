package com.example.googlemap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiForLogin {

    @POST("login")

    Call<LoginCred> getLoginPost(@Body LoginCred loginCred);
}
