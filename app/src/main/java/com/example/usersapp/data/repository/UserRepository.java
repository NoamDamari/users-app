package com.example.usersapp.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.usersapp.data.local_db.AppDatabase;
import com.example.usersapp.data.local_db.UserDao;
import com.example.usersapp.data.models.User;
import com.example.usersapp.data.models.UserResponse;
import com.example.usersapp.data.network.RetrofitClient;
import com.example.usersapp.data.network.UsersApiService;
import com.example.usersapp.utils.SnackBarUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserRepository {

    private final UserDao userDao;
    private final UsersApiService usersApiService;
    private final ExecutorService executor;

    public UserRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        this.userDao = database.userDao();
        this.usersApiService = RetrofitClient.getRetrofitClient().create(UsersApiService.class);
        this.executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Returns LiveData containing the list of users.
     * This LiveData will be updated when data is fetched from the database or API.
     */
    public LiveData<List<User>> getUserListLiveData(){
        return userDao.getAllUsers();
    }

    public LiveData<User> getUserById(int id){
        return userDao.getUserById(id);
    }

    /**
     * Loads the user list from the Room database.
     * If the database is empty, it will fetch users from the API.
     */
    public void loadUserList() {
        executor.execute(() -> {
            int users = userDao.getUserCount();
            if (users == 0) {
                Log.d("UserRepository", "Room database is empty, fetching from API");
                fetchUsersFromApi();
            } else {
                Log.d("UserRepository", "Room database has users, updating LiveData");
            }
        });
    }

    /**
     * Fetches the user list from the API.
     * On a successful response, updates the database and LiveData with the new user list.
     */
    public void fetchUsersFromApi(){

        Call<UserResponse> call = usersApiService.getUsers();
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    UserResponse userResponse = response.body();
                    List<User> userList = userResponse.getUserList();
                    executor.execute(() -> userDao.insertAllUsers(userList));
                }
            }
            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                Log.e("UserRepository", "API call failed: " + t.getMessage());
            }
        });
    }

    /**
     * Adds a new user to the database.
     */
    public void addUser(User user) {
        Executors.newSingleThreadExecutor().execute(() -> {
            userDao.insertUser(user);
        });
    }

    /**
     * Deletes an existing user from the database.
     */
    public void deleteUser(User user) {
        Executors.newSingleThreadExecutor().execute(() -> {
            userDao.deleteUser(user);
        });
    }

    /**
     * Updates an existing user's information in the database.
     */
    public void updateUser(User user) {
        Executors.newSingleThreadExecutor().execute(() -> {
            userDao.updateUser(user);
        });
    }
}
