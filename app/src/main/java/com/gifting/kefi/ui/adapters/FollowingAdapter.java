package com.gifting.kefi.ui.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.User;
import com.gifting.kefi.ui.fragments.user_deatials_bottom_sheet.UserDetailsFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.ViewHolder> {

    private Context context;
    private FragmentActivity activity;
    private ArrayList<User> users;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;


    public FollowingAdapter(Context context, FragmentActivity activity) {
        this.context = context;
        this.activity = activity;
        users = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View finishItemLayoutView;
        if (viewType == ITEM) {
            finishItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_following, parent, false);
        } else {
            finishItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_progress, parent, false);
        }
        return new ViewHolder(finishItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM) {
            holder.name.setText(users.get(position).getName());
            holder.userName.setText("@" + users.get(position).getUserName());
            Glide.with(context.getApplicationContext()).load(users.get(position).getImageURL()).placeholder(R.drawable.avatar).into(holder.userImage);
            holder.userItem.setOnClickListener(v -> {
               /* Intent intent = new Intent(context.getApplicationContext(), UserDetailsActivity.class);
                intent.putExtra("USER_ID", users.get(position));
                context.startActivity(intent);*/

                openFragment(position);
            });
        }

    }

    @Override
    public int getItemCount() {
        return (users != null) ? users.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == users.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, userName;
        private ImageView userImage;
        private ConstraintLayout userItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_text);
            userName = itemView.findViewById(R.id.user_name_text);
            userImage = itemView.findViewById(R.id.user_image);
            userItem = itemView.findViewById(R.id.user_item);
        }
    }



      /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(User user) {
        users.add(user);
        notifyItemInserted(users.size() - 1);
    }

    public void addAll(ArrayList<User> mcList) {
        for (User user : mcList) {
            add(user);
        }
    }

    public void remove(User user) {
        int position = users.indexOf(user);
        if (position > -1) {
            users.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new User());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = users.size() - 1;
        User item = getItem(position);

        if (item != null) {
            users.remove(position);
            notifyItemRemoved(position);
        }
    }

    public User getItem(int position) {
        return users.get(position);
    }

    private void openFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("USER", users.get(position));
        UserDetailsFragment userDetailsFragment = UserDetailsFragment.newInstance();
        userDetailsFragment.setArguments(bundle);
        userDetailsFragment.show(activity.getSupportFragmentManager(), "dssd1");
    }

}
