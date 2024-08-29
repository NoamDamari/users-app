package com.example.usersapp.ui;

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
import com.example.usersapp.viewmodel.UserListViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;


public class UserListFragment extends Fragment implements UserAdapter.UserActionListener {

    private FragmentUserListBinding binding;
    private UserListViewModel viewModel;
    private int userPosition;
    private RecyclerView usersRV;
    private UserAdapter adapter;
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

        buildDeleteConfirmationDialog();
        setupRecyclerView();
        viewModel = new ViewModelProvider(this).get(UserListViewModel.class);
        viewModel.getUserListLiveData().observe(getViewLifecycleOwner(), users -> {
            if(users != null){
                adapter.setUserList(users);
            }
        });
        binding.toAddUserBtn.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_userListFragment_to_addUserFragment));
    }

    private void setupRecyclerView() {
        adapter = new UserAdapter(new ArrayList<User>(), this);

        usersRV = binding.userLIstRV;
        usersRV.setLayoutManager(new LinearLayoutManager(getContext()));
        usersRV.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onUpdateUser(int position) {

    }

    @Override
    public void onDeleteUser(int position) {
        userPosition = position;
        showDeleteConfirmationDialog();
    }

    private void showDeleteConfirmationDialog() {
        if (deleteConfirmationDialog != null) {
            deleteConfirmationDialog.show();
        }
    }
    private void deleteUser(){
        User userToDelete = adapter.getUserAtPosition(userPosition);
        viewModel.deleteUser(userToDelete);
        adapter.notifyItemChanged(userPosition);
    }

    private void buildDeleteConfirmationDialog() {
        deleteConfirmationDialog = AlertDialogUtils.createConfirmationDialog(
                requireContext(),
                "Confirm Deletion",
                "Are you sure you want to delete this user?",
                "Yes",
                "No",
                (dialog, which) -> deleteUser(),
                (dialog, which) -> dialog.dismiss()
        );
    }
}