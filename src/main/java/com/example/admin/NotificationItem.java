package com.example.admin;

public class NotificationItem {
    public  String busName;
    public  String  adminName;
    public  String adminDesig;
    public  String message;

    public NotificationItem(String busName, String adminName, String adminDesig, String message) {
        this.busName = busName;
        this.adminName = adminName;
        this.adminDesig = adminDesig;
        this.message = message;
    }

    public String getBusName() {
        return busName;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getAdminDesig() {
        return adminDesig;
    }

    public String getMessage() {
        return message;
    }
}
