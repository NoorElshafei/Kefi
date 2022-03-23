package com.gifting.kefi.ui.activities.user_details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.ReelVideoModel;
import com.gifting.kefi.data.model.User;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivityUserDetailsBinding;
import com.gifting.kefi.ui.activities.followers_page.FollowersActivity;
import com.gifting.kefi.ui.activities.following_page.FollowingActivity;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.adapters.ProfileAdapter;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.ui.fragments.profile.ProfileViewModel;
import com.gifting.kefi.ui.fragments.user_deatials_bottom_sheet.UserDetailsViewModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;

public class UserDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityUserDetailsBinding binding;
    private UserDetailsViewModel mViewModel;
    private UserSharedPreference userSharedPreference;
    private User user;
    private ProfileAdapter adapter;
    private ProfileViewModel profileViewModel;
    private ArrayList<ReelVideoModel> reelVideoModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_details);

        mViewModel = new ViewModelProvider(this).get(UserDetailsViewModel.class);

        userSharedPreference = new UserSharedPreference(getApplicationContext());

        if (getIntent() != null) {
            user = getIntent().getParcelableExtra("USER_ID");
            setUI();
            checkIfFollowUser(user.getId(), userSharedPreference.getUserDetails().getId());
            getCountFollowerAndFollowing(user.getId());
        }

        binding.recentlyLayout.setCardElevation(6);
        binding.mostViewedLayout.setCardElevation(0);
        binding.recentlyLayout.setOnClickListener(this);
        binding.mostViewedLayout.setOnClickListener(this);
        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.followerLayout.setOnClickListener(this);
        binding.followingLayout.setOnClickListener(this);
        binding.followButton.setOnClickListener(this);
        binding.linearLayout14.setOnClickListener(this);
        binding.cardView3.setOnClickListener(this);

        binding.profileRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        adapter = new ProfileAdapter(this);
        binding.profileRecyclerView.setAdapter(adapter);

    }

    private void getCountFollowerAndFollowing(String followedUserId) {
        mViewModel.countFollowerAndFollowing(followedUserId);
        mViewModel.getFollowerNumberLiveData().observe(this, s -> {
            binding.followerCount.setText(s);
        });
        mViewModel.getFollowingNumberLiveData().observe(this, s -> {
            binding.followingCount.setText(s);
        });
    }

    private void checkIfFollowUser(String followerUserId, String followedUserId) {
        mViewModel.checkFollow(followerUserId, followedUserId);
        mViewModel.getCheckFollowLiveData().observe(this, s -> {
            binding.followText.setText(s);

        });
    }

    private void setUI() {
        if (user.getId().equals(userSharedPreference.getUserDetails().getId())) {
            binding.followButton.setVisibility(View.GONE);
            binding.linearLayout14.setVisibility(View.GONE);
        }
        Glide.with(getApplicationContext()).load(user.getImageURL()).placeholder(R.drawable.avatar).into(binding.userImage);
        binding.nameText.setText(user.getName());
        binding.userNameText.setText(user.getUserName());
        binding.userNameText.setText("@" + user.getUserName());
        if (user.getHeadLine().equals(""))
            binding.headlineText.setText(getString(R.string.no_headline));
        else
            binding.headlineText.setText(user.getHeadLine());

        binding.followButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.followButton) {
            if (binding.followText.getText().toString().equals(getString(R.string.follow))) {
                binding.followText.setText(getString(R.string.unfollow));
                mViewModel.setFollowToUser(user.getId(), userSharedPreference.getUserDetails().getId());
            } else {
                binding.followText.setText(getString(R.string.follow));
                mViewModel.setUnFollowToUser(user.getId(), userSharedPreference.getUserDetails().getId());
            }
        } else if (v == binding.recentlyLayout) {
            binding.recentlyLayout.setCardElevation(6);
            binding.mostViewedLayout.setCardElevation(0);
            getPostsOfUser("recently");
        } else if (v == binding.mostViewedLayout) {
            binding.recentlyLayout.setCardElevation(0);
            binding.mostViewedLayout.setCardElevation(6);
            getPostsOfUser("most");
        } else if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.notificationLayout) {
            startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
        } else if (v == binding.followingLayout) {
            Intent intent = new Intent(getApplicationContext(), FollowingActivity.class);
            intent.putExtra("USER_ID", user.getId());
            startActivity(intent);
        } else if (v == binding.followerLayout) {
            Intent intent = new Intent(getApplicationContext(), FollowersActivity.class);
            intent.putExtra("USER_ID", user.getId());
            startActivity(intent);
        } else if (v == binding.linearLayout14) {
            Toast.makeText(this, "you poked " + user.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPostsOfUser("recently");
    }

    private void getPostsOfUser(String recentlyOrMost) {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        profileViewModel.getPostsOfUsersDetails(user.getId());
        profileViewModel.getUserVideosLiveData().observe(this, reelVideoModels -> {
            profileViewModel.getUserVideosLiveData().removeObservers(this);
            this.reelVideoModels = reelVideoModels;
            if (recentlyOrMost.equals("recently")) {
                displayRecentlyPosts(reelVideoModels);
            } else {
                displayMostViewedPosts(reelVideoModels);
            }
        });
        profileViewModel.getFailText().observe(this, s -> {
            profileViewModel.getFailText().removeObservers(this);
            binding.noVideo.setVisibility(View.VISIBLE);

        });
    }

    private void displayRecentlyPosts(ArrayList<ReelVideoModel> reelVideoModelArrayList) {
        Collections.sort(reelVideoModelArrayList, (o1, o2) -> Long.compare(o2.getTime_created(), o1.getTime_created()));
        adapter.setReelVideoModels(reelVideoModelArrayList);
        // binding.loadingLayout.setVisibility(View.GONE);

    }

    private void displayMostViewedPosts(ArrayList<ReelVideoModel> reelVideoModelArrayList) {
        Collections.sort(reelVideoModelArrayList, (o1, o2) -> o1.getTotal_views() - o2.getTotal_views());
        Collections.reverse(reelVideoModelArrayList);
        adapter.setReelVideoModels(reelVideoModelArrayList);
        //binding.loadingLayout.setVisibility(View.GONE);

    }
}