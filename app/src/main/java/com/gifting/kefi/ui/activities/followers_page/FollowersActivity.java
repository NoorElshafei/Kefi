package com.gifting.kefi.ui.activities.followers_page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.User;
import com.gifting.kefi.databinding.ActivityFollowersBinding;
import com.gifting.kefi.ui.activities.following_page.FollowingViewModel;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.adapters.FollowingAdapter;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.Language;
import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class FollowersActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityFollowersBinding binding;
    private FollowingViewModel viewModel;
    private FollowingAdapter adapter;
    private ArrayList<User> userArrayList;
    private boolean isLastPage = false;
    private String ownerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_followers);
        viewModel = new ViewModelProvider(this).get(FollowingViewModel.class);

        Language.changeBackDependsLanguage(binding.backImage,getApplicationContext());


        if (getIntent() != null) {
            ownerUser = getIntent().getStringExtra("USER_ID");
        }


        binding.followingRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        adapter = new FollowingAdapter(this,this);
        binding.followingRecyclerView.setAdapter(adapter);
        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);

        Glide.with(getApplicationContext()).load(R.drawable.loading).into(binding.loadProgress);


        binding.nestedScrollFollowing.getViewTreeObserver()
                .addOnScrollChangedListener(() -> {
                    if (!isLastPage) {
                        if (binding.nestedScrollFollowing.getChildAt(0).getBottom() <= (binding.nestedScrollFollowing.getHeight() + binding.nestedScrollFollowing.getScrollY())) {
                            //scroll view is at bottom
                            loadNextPage();
                            Log.d("noooor", "onScrollChanged: ");
                        }
                    }
                });

        loadFirstPage();
    }

    private void loadFirstPage() {
        userArrayList = new ArrayList<>();
        Query query = FirebaseDatabase.getInstance().getReference("Followers").child(ownerUser).orderByKey().limitToFirst(10);
        viewModel.getUserDetails(query);
        viewModel.getUserLiveDate().observe(this, users -> {
            viewModel.getUserLiveDate().removeObservers(this);
            binding.loadLayout.setVisibility(View.INVISIBLE);
            userArrayList.addAll(users);
            adapter.addAll(userArrayList);
            if (users.size() == 10) adapter.addLoadingFooter();
            else isLastPage = true;

        });

        viewModel.getFailText().observe(this, s -> {
            if (s.equals("No Users Found")) {
                binding.loadLayout.setVisibility(View.INVISIBLE);
                binding.noFoundText.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadNextPage() {
        String lastFollowingUserId = userArrayList.get(userArrayList.size() - 1).getId();
        Query query = FirebaseDatabase.getInstance().getReference("Followers").child(ownerUser).orderByKey().startAfter(lastFollowingUserId).limitToFirst(10);
        viewModel.getUserDetails(query);
        viewModel.getUserLiveDate().observe(this, users -> {
            viewModel.getUserLiveDate().removeObservers(this);
            adapter.removeLoadingFooter(); // 2
            userArrayList.addAll(users);
            adapter.addAll(users);
            if (users.size() == 10) adapter.addLoadingFooter();
            else isLastPage = true;

        });
    }

    @Override
    public void onClick(View v) {
        if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.notificationLayout) {
            startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
        } else if (v == binding.back) {
            onBackPressed();
        }

    }
}