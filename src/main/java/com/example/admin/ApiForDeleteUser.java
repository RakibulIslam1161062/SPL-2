package com.example.admin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiForDeleteUser {

    @POST("usersDelete")
    Call<Id> deletePost(@Body Id deleteId);

    @POST("adminDelete")
    Call<Id> adminDeletePost(@Body Id deleteId);
}
