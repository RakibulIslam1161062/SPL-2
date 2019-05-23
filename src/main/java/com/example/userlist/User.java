package com.example.userlist;

public class User {

    public String userName;
    public String department;

    public User(String userName, String department) {
        this.userName = userName;
        this.department = department;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
