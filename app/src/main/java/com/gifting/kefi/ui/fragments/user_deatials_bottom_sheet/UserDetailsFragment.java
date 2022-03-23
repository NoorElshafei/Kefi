package com.gifting.kefi.ui.fragments.user_deatials_bottom_sheet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.User;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.FragmentUserDetailsBinding;
import com.gifting.kefi.ui.activities.user_details.UserDetailsActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class UserDetailsFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private UserDetailsViewModel mViewModel;
    private FragmentUserDetailsBinding binding;
    private User user;
    private UserSharedPreference userSharedPreference;


    public static UserDetailsFragment newInstance() {
        return new UserDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_details, container, false);
        binding.followButton.setOnClickListener(this);
        binding.profileButton.setOnClickListener(this);
        View view = binding.getRoot();
        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UserDetailsViewModel.class);
        // TODO: Use the ViewModel
        userSharedPreference = new UserSharedPreference(getContext());
        if (getArguments() != null) {
            user = getArguments().getParcelable("USER");
            setUI();
            checkIfFollowUser(user.getId(), userSharedPreference.getUserDetails().getId());
            getCountFollowerAndFollowing(user.getId());
        }

    }

    private void getCountFollowerAndFollowing(String followedUserId) {
        mViewModel.countFollowerAndFollowing(followedUserId);
        mViewModel.getFollowerNumberLiveData().observe(getViewLifecycleOwner(), s -> {
            binding.shimmerViewContainer.hideShimmer();
            binding.followerCount.setBackgroundColor(getResources().getColor(R.color.transparent));
            binding.followerCount.setText(s);
        });
        mViewModel.getFollowingNumberLiveData().observe(getViewLifecycleOwner(), s -> {
            binding.shimmerViewContainer1.hideShimmer();
            binding.followingCount.setBackgroundColor(getResources().getColor(R.color.transparent));
            binding.followingCount.setText(s);
        });
    }

    private void checkIfFollowUser(String followerUserId, String followedUserId) {
        mViewModel.checkFollow(followerUserId, followedUserId);
        mViewModel.getCheckFollowLiveData().observe(getViewLifecycleOwner(), s -> {
            if (s.equals("Unfollow"))
                binding.followText.setText(getString(R.string.unfollow));
            else
                binding.followText.setText(getString(R.string.follow));

        });
    }

    private void setUI() {
        if (user.getId().equals(userSharedPreference.getUserDetails().getId())) {
            binding.followButton.setVisibility(View.GONE);
        }
        Glide.with(this).load(user.getImageURL()).placeholder(R.drawable.avatar).into(binding.userImage);
        binding.nameText.setText(user.getName());
        binding.userNameText.setText(user.getUserName());
        binding.userNameText.setText("@" + user.getUserName());
        if (user.getHeadLine().equals(""))
            binding.headlineText.setText(R.string.no_headline);
        else
            binding.headlineText.setText(user.getHeadLine());

        binding.followButton.setOnClickListener(this);
        binding.profileButton.setOnClickListener(this);
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
        } else if (v == binding.profileButton) {
            Intent intent = new Intent(getContext().getApplicationContext(), UserDetailsActivity.class);
            intent.putExtra("USER_ID", user);
            getContext().startActivity(intent);
        }
    }
}