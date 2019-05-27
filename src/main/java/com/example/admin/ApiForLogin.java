package com.example.admin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiForLogin {

    @POST("AdminLogin")

    Call<LoginCred> getLoginPost(@Body LoginCred loginCred);
}
