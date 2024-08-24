package com.example.usersapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.usersapp.R;
import com.example.usersapp.data.models.User;
import com.example.usersapp.data.models.UserResponse;
import com.example.usersapp.data.network.RetrofitClient;
import com.example.usersapp.data.network.UsersApiService;
import com.example.usersapp.databinding.FragmentUserListBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserListFragment extends Fragment {

    private FragmentUserListBinding binding;
    private RecyclerView usersRV;
    private List<User> userList;
    private UserAdapter adapter;
    public UserListFragment() {
        // Required empty public constructor
    }

    public static UserListFragment newInstance(String param1, String param2) {
        return new UserListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userList = new ArrayList<User>();
        adapter = new UserAdapter(userList);
        usersRV = binding.userLIstRV;
        usersRV.setLayoutManager(new LinearLayoutManager(getContext()));
        usersRV.setAdapter(adapter);

        fetchUsers();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void fetchUsers(){
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        UsersApiService usersApiService = retrofit.create(UsersApiService.class);

        Call<UserResponse> call = usersApiService.getUsers();
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                userList.clear();
                userList.addAll(userResponse.getUserList());
                adapter.notifyDataSetChanged();

                showToast("Data retrieved successfully");
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                showToast("Error connecting to the server: " + t.getMessage());
            }
        });

    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}