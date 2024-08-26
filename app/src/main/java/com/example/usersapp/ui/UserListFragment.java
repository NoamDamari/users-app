package com.example.usersapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.usersapp.data.models.User;
import com.example.usersapp.databinding.FragmentUserListBinding;
import com.example.usersapp.viewmodel.UserViewModel;

import java.util.ArrayList;


public class UserListFragment extends Fragment {

    private FragmentUserListBinding binding;
    private UserViewModel viewModel;
    private RecyclerView usersRV;
    private UserAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel.getUserListLiveData().observe(getViewLifecycleOwner(), users -> {
            if(users != null){
                adapter.setUserList(users);
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new UserAdapter(new ArrayList<User>());

        usersRV = binding.userLIstRV;
        usersRV.setLayoutManager(new LinearLayoutManager(getContext()));
        usersRV.setAdapter(adapter);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}