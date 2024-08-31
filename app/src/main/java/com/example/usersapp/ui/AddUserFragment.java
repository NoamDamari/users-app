package com.example.usersapp.ui;

import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.usersapp.utils.ImagePickerManager;
import com.example.usersapp.R;
import com.example.usersapp.utils.UserInputValidator;
import com.example.usersapp.data.models.User;
import com.example.usersapp.databinding.FragmentAddUserBinding;
import com.example.usersapp.utils.SnackBarUtils;
import com.example.usersapp.viewmodel.UserViewModel;

public class AddUserFragment extends Fragment {

    private FragmentAddUserBinding binding;
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
        binding = FragmentAddUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViewModel();
        updateUIFromUserLiveData();
        updateUIFromImageUriLiveData();
        setUpUIListeners();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * Initializes the ViewModel for this Fragment.
     */
    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    /**
     * Sets up the image picker launcher to handle image selection.
     */
    private void initializeImagePickerLauncher() {
        imagePickerLauncher = ImagePickerManager.createImagePickerLauncher(this, uri -> {
            if (uri != null) {
                imageUri = uri;
                viewModel.setImageUri(imageUri);
                ImagePickerManager.handleImageUri(requireContext(), uri, binding.addUserImageView);
            }
        });
    }

    /**
     * Opens the image picker to select an image.
     */
    private void openImagePicker() {
        imagePickerLauncher.launch(new String[]{"image/*"});
    }

    /**
     * Creates a User object from the input fields in the UI.
     * @return A User object with the provided details.
     */
    private User createUserFromInput() {
        String userFirstName = binding.firstNameET.getText().toString().trim();
        String userLastName = binding.lastNameET.getText().toString().trim();
        String userEmail = binding.emailET.getText().toString().trim();
        if(imageUri != null){
            return new User(userEmail, userFirstName, userLastName, imageUri.toString());
        } else {
            return new User(userEmail, userFirstName, userLastName, null);
        }

    }

    /**
     * Updates the UI with the data from the ViewModel's LiveData.
     */
    public void updateUIFromUserLiveData() {
        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                binding.firstNameET.setText(user.getFirstName());
                binding.lastNameET.setText(user.getLastName());
                binding.emailET.setText(user.getEmail());
            }
        });
    }

    private void updateUIFromImageUriLiveData() {
        viewModel.getImageUriLiveData().observe(getViewLifecycleOwner(), uri -> {
            if (uri != null) {
                imageUri = uri;
                ImagePickerManager.handleImageUri(requireContext(), uri, binding.addUserImageView);
            }
        });
    }

    /**
     * Sets up listeners for UI interactions.
     */
    public void setUpUIListeners() {
        binding.pickImageBtn.setOnClickListener(v -> openImagePicker());
        binding.addUserBtn.setOnClickListener(v -> {
            if (inputIsValid()) {
                User newUser = createUserFromInput();
                viewModel.addUser(newUser);
                SnackBarUtils.showSnackBar(v, getString(R.string.user_added_successfully));
                Navigation.findNavController(v).navigate(R.id.action_addUserFragment_to_userListFragment);
            }
        });

        binding.addUserTopAppBar.setNavigationOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });
    }

    /**
     * Validates user input fields.
     * @return true if all input fields are valid, false otherwise.
     */
    private boolean inputIsValid() {
        return UserInputValidator.validateUserInputFields(binding.firstNameET, binding.firstNameTF,
                binding.lastNameET, binding.lastNameTF, binding.emailET, binding.emailTF);
    }
}