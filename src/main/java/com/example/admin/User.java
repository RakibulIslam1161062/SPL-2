package com.example.admin;

public class User {

    public String userName;
    public String department;
    public String _id;

    public User(String userName, String department, String _id) {
        this.userName = userName;
        this.department = department;
        this._id = _id;
    }

    public String getUserName() {
        return userName;
    }

    public String getDepartment() {
        return department;
    }

    public String get_id() {
        return _id;
    }
}
