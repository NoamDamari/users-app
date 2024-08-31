package com.example.usersapp.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.usersapp.data.models.User;
import com.example.usersapp.data.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private LiveData<User> userLiveData;
    private final MutableLiveData<Uri> imageUriLiveData;

    public UserViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = new UserRepository(application);
        this.userLiveData = new MutableLiveData<>();
        this.imageUriLiveData = new MutableLiveData<>();
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<Uri> getImageUriLiveData() {
        return imageUriLiveData;
    }

    public void setImageUri(Uri uri) {
        imageUriLiveData.setValue(uri);
    }
    public void loadUserById(int id) {
        userLiveData = userRepository.getUserById(id);
    }

    public void addUser(User user) {
        userRepository.addUser(user);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }
}

