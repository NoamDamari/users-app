package com.example.usersapp.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {
    @SerializedName("data")
    private List<User> userList;

    public List<User> getUserList() {
        return userList;
    }
}
