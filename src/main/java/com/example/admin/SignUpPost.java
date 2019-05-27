package com.example.admin;

public class SignUpPost {

    //private String _id;
    private String name;
    private String dept;
    private String busName;
    private String userName;
    private String password;
    //private int __v;


    public SignUpPost(String name, String dept,String busName, String userName, String password) {
        //this._id = _id;
        this.name = name;
        this.dept = dept;
        this.busName = busName;
        this.userName = userName;
        this.password = password;
        //this.__v = __v;
    }
/*
    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int get__v() {
        return __v;
    }
*/
}
