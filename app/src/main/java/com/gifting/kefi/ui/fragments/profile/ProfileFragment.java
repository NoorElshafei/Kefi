package com.gifting.kefi.ui.fragments.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.gifting.kefi.R;
import com.gifting.kefi.data.model.ReelVideoModel;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.FragmentProfileBinding;
import com.gifting.kefi.ui.activities.edit_profile.EditProfileActivity;
import com.gifting.kefi.ui.activities.main.MainActivity;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.adapters.ProfileAdapter;
import com.gifting.kefi.ui.fragments.edit_name_bottom_sheet.EditNameFragment;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.CheckInternet;

import java.util.ArrayList;
import java.util.Collections;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private ProfileViewModel mViewModel;
    private FragmentProfileBinding binding;
    private ProfileAdapter adapter;
    private UserSharedPreference userSharedPreference;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        checkInternet();
        //binding.recentlyLayout.setCardElevation(6);
        //binding.mostViewedLayout.setCardElevation(0);

        //binding.profileRecyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext(), RecyclerView.HORIZONTAL, false));
        //  adapter = new ProfileAdapter(getContext(), this);
        // binding.profileRecyclerView.setAdapter(adapter);

        Glide.with(this).load(R.drawable.loading).into(binding.loadProgress);


    }


    private void setDataInUI() {
        userSharedPreference = new UserSharedPreference(getContext().getApplicationContext());
        //checkSkin();
        String[] myName = userSharedPreference.getUserDetails().getName().split(" ");
        binding.nameProfile.setText(myName[0].toUpperCase().charAt(0) + myName[0].substring(1).toLowerCase() + " " + myName[1].toUpperCase().charAt(0) + myName[1].substring(1).toLowerCase());
        binding.userName.setText("@" + userSharedPreference.getUserDetails().getUserName());
     /*   if (userSharedPreference.getHeadLine().equals(""))
            binding.headLineText.setText(R.string.please_add_your_headline);
        else
            binding.headLineText.setText(userSharedPreference.getHeadLine());
*/
        Glide.with(this).load(userSharedPreference.getUserDetails().getImageURL()).placeholder(R.drawable.avatar).into(binding.userImage);

        getCountFollowerAndFollowing(userSharedPreference.getUserDetails().getId());
    }

    private void getCountFollowerAndFollowing(String followedUserId) {
        mViewModel.countFollowerAndFollowing(followedUserId);
        mViewModel.getFollowerNumberLiveData().observe(getViewLifecycleOwner(), s -> {
            // binding.followerCount.setText(s);
        });
        mViewModel.getFollowingNumberLiveData().observe(getViewLifecycleOwner(), s -> {
            // binding.followingCount.setText(s);
            binding.loadLayout.setVisibility(View.GONE);
            binding.contentParentConstraint.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.editProfileButton.setOnClickListener(this);
        //   binding.recentlyLayout.setOnClickListener(this);
        //   binding.mostViewedLayout.setOnClickListener(this);
        binding.editProfileName.setOnClickListener(this);
        //   binding.editHeadLine.setOnClickListener(this);
        //   binding.followingLayout.setOnClickListener(this);
        //   binding.followerLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getChildFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.notificationLayout) {
            startActivity(new Intent(getContext().getApplicationContext(), NotificationsActivity.class));
        } else if (v == binding.editProfileButton) {
            startActivity(new Intent(getContext().getApplicationContext(), EditProfileActivity.class));
        } /*else if (v == binding.recentlyLayout) {
            // binding.profileRecyclerView.setVisibility(View.VISIBLE);
            // binding.playlistRecyclerView.setVisibility(View.GONE);
        //    binding.recentlyLayout.setCardElevation(6);
        //    binding.mostViewedLayout.setCardElevation(0);
            getVideosOfUser("recently");

        }*//* else if (v == binding.mostViewedLayout) {
            // binding.profileRecyclerView.setVisibility(View.GONE);
            // binding.playlistRecyclerView.setVisibility(View.VISIBLE);
        //    binding.recentlyLayout.setCardElevation(0);
        //    binding.mostViewedLayout.setCardElevation(6);
            getVideosOfUser("most");

        }*/ else if (v == binding.editProfileName) {
            EditNameFragment editNameFragment = EditNameFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("editName", userSharedPreference.getUserDetails().getName());
            editNameFragment.setArguments(bundle);
            editNameFragment.show(getChildFragmentManager(), "rateOrderFragment");

        } /*else if (v == binding.editHeadLine) {
            EditHeadLineFragment editHeadLineFragment = EditHeadLineFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("editHeadline", userSharedPreference.getHeadLine());
            editHeadLineFragment.setArguments(bundle);
            editHeadLineFragment.show(getChildFragmentManager(), "rateOrderFragment1");
        }*/ /*else if (v == binding.followingLayout) {
            Intent intent = new Intent(getContext().getApplicationContext(), FollowingActivity.class);
            intent.putExtra("USER_ID", userSharedPreference.getUserDetails().getId());
            startActivity(intent);
        }*/ /*else if (v == binding.followerLayout) {
            Intent intent = new Intent(getContext().getApplicationContext(), FollowersActivity.class);
            intent.putExtra("USER_ID", userSharedPreference.getUserDetails().getId());
            startActivity(intent);
        }*/
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).getBinding().profileImage.setImageResource(R.drawable.ic_profile_active);
        ((MainActivity) getActivity()).getBinding().profileText.setTextColor(getResources().getColor(R.color.bink));
        setDataInUI();
        //getVideosOfUser("recently");

    }

 /*   private void getVideosOfUser(String isRecentlyOrMost) {

        mViewModel.getPostsOfUsers(userSharedPreference.getUserDetails().getId());
        mViewModel.getUserVideosLiveData().observe(getViewLifecycleOwner(), reelVideoModels -> {
            mViewModel.getUserVideosLiveData().removeObservers(getViewLifecycleOwner());
            if (isRecentlyOrMost.equals("recently"))
                displayRecentlyPosts(reelVideoModels);
            else
                displayMostViewedPosts(reelVideoModels);
        });
        mViewModel.getFailText().observe(getViewLifecycleOwner(), s -> {
//            adapter.removeAllData();
//            adapter.notifyDataSetChanged();
            mViewModel.getFailText().removeObservers(getViewLifecycleOwner());
            binding.noVideo.setVisibility(View.VISIBLE);
        });
    }*/

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity) getActivity()).getBinding().profileImage.setImageResource(R.drawable.ic_profile_un_active);
        ((MainActivity) getActivity()).getBinding().profileText.setTextColor(getResources().getColor(R.color.bink_lighter));
    }

    public FragmentProfileBinding getBinding() {
        return binding;
    }


    private void displayRecentlyPosts(ArrayList<ReelVideoModel> reelVideoModelArrayList) {
        adapter.setIsRecentlyOrMost("recently");
        Collections.sort(reelVideoModelArrayList, (o1, o2) -> Long.compare(o2.getTime_created(), o1.getTime_created()));
        adapter.setReelVideoModels(reelVideoModelArrayList);
        // binding.loadingLayout.setVisibility(View.GONE);

    }

    private void displayMostViewedPosts(ArrayList<ReelVideoModel> reelVideoModelArrayList) {
        adapter.setIsRecentlyOrMost("most");
        Collections.sort(reelVideoModelArrayList, (o1, o2) -> o1.getTotal_views() - o2.getTotal_views());
        Collections.reverse(reelVideoModelArrayList);
        adapter.setReelVideoModels(reelVideoModelArrayList);
        //binding.loadingLayout.setVisibility(View.GONE);

    }

/*
    @Override
    public void isRecentlyOrMost(String isRecentlyOrMostString) {
       *//* if (isRecentlyOrMostString.equals("recently")) {
            getVideosOfUser("recently");
        } else if (isRecentlyOrMostString.equals("most")) {
            getVideosOfUser("most");
        }*//*
    }*/

    private void checkInternet() {
        CheckInternet.hasInternetConnection().subscribe((hasInternet) -> {
            if (!hasInternet && getContext() != null) {
                Toast.makeText(getContext(), getString(R.string.internet_may_be_unstable_or_not_connected), Toast.LENGTH_LONG).show();
            }
        });
    }

   /* private void checkSkin() {
        if (userSharedPreference.getLanguage().equals("ar")) {
            if (userSharedPreference.getUserDetails().getSkinType().equals("DRY")) {
                binding.typeSkinText.setText(" جافة");
            } else if (userSharedPreference.getUserDetails().getSkinType().equals("OILY")) {
                binding.typeSkinText.setText(" دهنية");
            } else if (userSharedPreference.getUserDetails().getSkinType().equals("NORMAL")) {
                binding.typeSkinText.setText(" عادية");
            } else {
                binding.typeSkinText.setText(" مختلطة");
            }
        } else
            binding.typeSkinText.setText(userSharedPreference.getUserDetails().getSkinType());


    }*/


}