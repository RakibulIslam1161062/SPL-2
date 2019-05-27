package com.example.admin;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.Call;



public interface ApiForSendingNotification {


        @POST("sendnotification")
        Call<NotificationItem> getNotificationItem(@Body NotificationItem notificationItem);

    }

