package com.example.usersapp.ui;

import android.view.LayoutInflater;
import android.view.RoundedCorner;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

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
    private UserActionListener actionListener;
    public UserAdapter(List<User> userList, UserActionListener actionListener) {
        this.userList = userList;
        this.actionListener = actionListener;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        private final UserListItemBinding binding;
        public UserViewHolder(UserListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
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

        holder.binding.userItemMenuBtn.setOnClickListener(v -> showUserPopupMenu(v, position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setUserList(List<User> users) {
        userList.clear();
        userList.addAll(users);
        notifyDataSetChanged();
    }

    public User getUserAtPosition(int position) {
        return userList.get(position);
    }
    private void showUserPopupMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.user_options_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(option -> {
            int itemId = option.getItemId();
            if (itemId == R.id.action_update) {
                actionListener.onUpdateUser(position);
                return true;
            } else if (itemId == R.id.action_delete) {
                actionListener.onDeleteUser(position);
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }

    public interface UserActionListener {
        void onUpdateUser(int position);
        void onDeleteUser(int position);
    }

}
