package com.example.usersapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.usersapp.data.models.User;
import com.example.usersapp.data.repository.UserRepository;

import java.util.List;


public class UserViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final LiveData<List<User>> userListLiveData;
    public UserViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = new UserRepository(application);
        this.userListLiveData = userRepository.getUserListLiveData();
    }

    /*
     * Returns LiveData containing the list of users.
     */
    public LiveData<List<User>> getUserListLiveData() {
        return userListLiveData;
    }
}
