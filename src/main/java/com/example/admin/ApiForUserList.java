package com.example.admin;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiForUserList {
    String BASE_URL = "http://192.168.1.121:4000/";

    @GET("users")
    Call<User> getUser();
}
