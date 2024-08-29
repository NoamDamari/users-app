package com.example.usersapp.ui;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usersapp.ImagePickerManager;
import com.example.usersapp.R;
import com.example.usersapp.UserInputValidator;
import com.example.usersapp.data.models.User;
import com.example.usersapp.databinding.FragmentAddUserBinding;
import com.example.usersapp.viewmodel.UserViewModel;

public class AddUserFragment extends Fragment {

    private FragmentAddUserBinding binding;
    private UserViewModel viewModel;
    private ActivityResultLauncher<String[]> imagePickerLauncher;
    private Uri imageUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imagePickerLauncher = ImagePickerManager.createImagePickerLauncher(this, uri -> {
            if (uri != null) {
                imageUri = uri;
                // Handle the selected image
                ImagePickerManager.handleImageUri(requireContext(), uri, binding.addUserImageView);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        updateUIFromUserLiveData();

        binding.pickImageBtn.setOnClickListener(v -> openImagePicker());
        binding.addUserBtn.setOnClickListener(v -> {
            if (validateFields()) {
                User newUser = createUserFromInput();
                viewModel.addUser(newUser);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void openImagePicker() {
        // Open the image picker
        imagePickerLauncher.launch(new String[]{"image/*"});
    }

    private Uri getDefaultImageUri() {
        return Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.drawable.icon_user);
    }

    private User createUserFromInput() {
        String userFirstName = binding.firstNameET.getText().toString().trim();
        String userLastName = binding.lastNameET.getText().toString().trim();
        String userEmail = binding.emailET.getText().toString().trim();
        Uri imageUri = (this.imageUri != null) ? this.imageUri : getDefaultImageUri();

        return new User(userEmail, userFirstName, userLastName, imageUri.toString());
    }

    public void updateUIFromUserLiveData() {
        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                binding.firstNameET.setText(user.getFirstName());
                binding.lastNameET.setText(user.getLastName());
                binding.emailET.setText(user.getEmail());
            }
        });
    }

    private boolean validateFields() {
        return UserInputValidator.validateUserInputFields(binding.firstNameET, binding.firstNameTF,
                binding.lastNameET, binding.lastNameTF, binding.emailET, binding.emailTF);
    }
}