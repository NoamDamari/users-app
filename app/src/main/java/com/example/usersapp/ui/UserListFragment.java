package com.example.usersapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.usersapp.R;
import com.example.usersapp.data.models.User;
import com.example.usersapp.databinding.FragmentUserListBinding;
import com.example.usersapp.utils.AlertDialogUtils;
import com.example.usersapp.utils.SnackBarUtils;
import com.example.usersapp.viewmodel.UserListViewModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class UserListFragment extends Fragment implements UserAdapter.UserActionListener {

    private FragmentUserListBinding binding;
    private UserListViewModel viewModel;
    private int userPosition;
    private RecyclerView usersRV;
    private UserAdapter adapter;
    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_FIRST_LAUNCH = "first_launch";
    private AlertDialog deleteConfirmationDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViewModel();
        setupRecyclerView();
        buildDeleteConfirmationDialog();
        observeUserList();
        setUpUIListeners();
    }

    /**
     * Initializes the ViewModel
     * loads users if it's the first launch
     */
    private void initializeViewModel() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isFirstLaunch = sharedPreferences.getBoolean(KEY_FIRST_LAUNCH, true);
        viewModel = new ViewModelProvider(requireActivity()).get(UserListViewModel.class);
        if (isFirstLaunch) {
            viewModel.loadUsers();
            sharedPreferences.edit().putBoolean(KEY_FIRST_LAUNCH, false).apply();
        }
    }

    /**
     * Observes changes in the user list and updates the adapter
     */
    private void observeUserList() {
        viewModel.getUserListLiveData().observe(getViewLifecycleOwner(), users -> {
            if(users != null){
                List<User> reversedList = new ArrayList<>(users);
                Collections.reverse(reversedList);
                adapter.setUserList(reversedList);
            }
        });
    }

    /**
     * Sets up listeners for UI interactions
     */
    public void setUpUIListeners() {
        binding.toAddUserBtn.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_userListFragment_to_addUserFragment));
    }

    /**
     * Configures the RecyclerView
     */
    private void setupRecyclerView() {
        adapter = new UserAdapter(new ArrayList<User>(), this);
        usersRV = binding.userListRV;
        usersRV.setLayoutManager(new LinearLayoutManager(getContext()));
        usersRV.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Handles the user update action
     * @param position The position of the user in the adapter to be updated.
     */
    @Override
    public void onUpdateUser(int position) {
        int userId = adapter.getUserAtPosition(position).getId();
        Bundle bundle = new Bundle();
        bundle.putInt("USER_ID", userId);
        Navigation.findNavController(requireView())
                .navigate(R.id.action_userListFragment_to_updateUserFragment, bundle);
    }

    /**
     * Handles the user deletion action
     * @param position The position of the user in the adapter to be deleted.
     */
    @Override
    public void onDeleteUser(int position) {
        userPosition = position;
        showDeleteConfirmationDialog();
    }

    /**
     * Shows the delete confirmation dialog.
     */
    private void showDeleteConfirmationDialog() {
        if (deleteConfirmationDialog != null) {
            deleteConfirmationDialog.show();
        }
    }
    /**
     * Deletes the user at the specified position and updates the UI
     */
    private void deleteUser(){
        User userToDelete = adapter.getUserAtPosition(userPosition);
        viewModel.deleteUser(userToDelete);
        adapter.notifyItemChanged(userPosition);
        SnackBarUtils.showSnackBar(requireView(), "User deleted successfully");
    }

    /**
     * Builds the confirmation dialog for user deletion
     */
    private void buildDeleteConfirmationDialog() {
        deleteConfirmationDialog = AlertDialogUtils.createConfirmationDialog(
                requireContext(),
                getString(R.string.confirm_deletion),
                getString(R.string.delete_message),
                getString(R.string.yes),
                getString(R.string.no),
                (dialog, which) -> deleteUser(),
                (dialog, which) -> dialog.dismiss()
        );
    }
}