package com.example.usersapp.ui;

import android.view.LayoutInflater;
import android.view.RoundedCorner;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.usersapp.R;
import com.example.usersapp.data.models.User;
import com.example.usersapp.databinding.UserListItemBinding;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private List<User> userList;
    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        UserListItemBinding binding = UserListItemBinding.inflate(inflater, parent, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.binding.usernameTV.setText(user.getName());
        holder.binding.userEmailTV.setText(user.getEmail());

        RequestOptions requestOptions = new RequestOptions()
                .transform(new CenterCrop() , new RoundedCorners(8));

        Glide.with(holder.itemView.getContext())
                .load(user.getImageUrl())
                .apply(requestOptions)
                .placeholder(R.drawable.icon_user)
                .error(R.drawable.icon_user)
                .into(holder.binding.userImageView);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        private final UserListItemBinding binding;
        public UserViewHolder(UserListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
