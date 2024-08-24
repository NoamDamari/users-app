package com.example.usersapp.data.network;

import com.example.usersapp.data.models.UserResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UsersApiService {

    @GET("users")
    Call<UserResponse> getUsers();
}
