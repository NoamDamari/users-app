package com.example.usersapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.usersapp.data.models.User;
import com.example.usersapp.data.repository.UserRepository;

import java.util.List;


public class UserListViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final LiveData<List<User>> userListLiveData;

    public UserListViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = new UserRepository(application);
        this.userListLiveData = userRepository.getUserListLiveData();
    }

    public LiveData<List<User>> getUserListLiveData() {
        return userListLiveData;
    }

    public void loadUsers() {
        userRepository.loadUserList();
    }

    public void deleteUser(User user){
        userRepository.deleteUser(user);
    }
}
