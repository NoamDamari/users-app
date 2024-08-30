package com.example.usersapp.ui;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usersapp.ImagePickerManager;
import com.example.usersapp.R;
import com.example.usersapp.data.models.User;
import com.example.usersapp.databinding.FragmentAddUserBinding;
import com.example.usersapp.databinding.FragmentUpdateUserBinding;
import com.example.usersapp.viewmodel.UserListViewModel;
import com.example.usersapp.viewmodel.UserViewModel;


public class UpdateUserFragment extends Fragment {

    private FragmentUpdateUserBinding binding;
    private UserViewModel viewModel;
    private ActivityResultLauncher<String[]> imagePickerLauncher;
    private Uri imageUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeImagePickerLauncher();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUpdateUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViewModel();
        loadUserDetails();
        setUpUIListeners();
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    public void setUpUIListeners() {
        binding.updatePickImageBtn.setOnClickListener(v -> openImagePicker());
        binding.updateUserBtn.setOnClickListener(v -> {
            User updatedUser = updateUserFromInput();
            viewModel.updateUser(updatedUser);
        });
    }
    /**
     * Initializes the image picker launcher for selecting images.
     */
    private void initializeImagePickerLauncher() {
        imagePickerLauncher = ImagePickerManager.createImagePickerLauncher(this, uri -> {
            if (uri != null) {
                imageUri = uri;
                ImagePickerManager.handleImageUri(requireContext(), uri, binding.updateUserImageView);
            }
        });
    }

    /**
     * Loads user details based on the provided USER_ID argument.
     */
    private void loadUserDetails() {
        Bundle args = getArguments();
        if (args != null) {
            int userId = args.getInt("USER_ID" , -1);
            viewModel.loadUserById(userId);
            Log.d("userId", "The value is: " + userId);
            viewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
                if (user != null) {
                    displayUserDetails(user);
                }
            });
        }
    }

    /**
     * Displays user details in the UI.
     *
     * @param user The user to display.
     */
    private void displayUserDetails(User user) {
        binding.updateFirstNameET.setText(user.getFirstName());
        binding.updateLastNameET.setText(user.getLastName());
        binding.updateEmailET.setText(user.getEmail());
        if (user.getImageUri() != null){
            Uri imageUri = Uri.parse(user.getImageUri());
            ImagePickerManager.handleImageUri(requireContext(), imageUri, binding.updateUserImageView);
        }
    }

    /**
     * Creates a User object from the input fields and the selected image.
     *
     * @return The User object with updated details.
     */
    private User updateUserFromInput() {
        int userId = viewModel.getUserLiveData().getValue().getId();
        String userFirstName = binding.updateFirstNameET.getText().toString().trim();
        String userLastName = binding.updateLastNameET.getText().toString().trim();
        String userEmail = binding.updateEmailET.getText().toString().trim();
        String imageUri = (this.imageUri != null) ? this.imageUri.toString() : viewModel.getUserLiveData().getValue().getImageUri() ;

        return new User(userId, userEmail, userFirstName, userLastName, imageUri);
    }

    private void openImagePicker() {
        imagePickerLauncher.launch(new String[]{"image/*"});
    }
}