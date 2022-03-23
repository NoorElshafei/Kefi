package com.gifting.kefi.ui.fragments.user_deatials_bottom_sheet;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserDetailsViewModel extends ViewModel {
    private MutableLiveData<String> checkFollowLiveData;
    private MutableLiveData<String> followerNumberLiveData;
    private MutableLiveData<String> followingNumberLiveData;
    private MutableLiveData<String> failText;
    private UserDetailsRepo userDetailsRepo;

    public UserDetailsViewModel() {
        userDetailsRepo = new UserDetailsRepo();
        checkFollowLiveData = userDetailsRepo.getCheckFollowLiveData();
        followerNumberLiveData = userDetailsRepo.getFollowerNumberLiveData();
        followingNumberLiveData = userDetailsRepo.getFollowingNumberLiveData();
        failText = userDetailsRepo.getFailText();
    }

    public void setFollowToUser(String followedUserId, String followerUserId) {
        userDetailsRepo.setFollowToUser(followedUserId, followerUserId);
    }

    public void setUnFollowToUser(String followedUserId, String followerUserId) {
        userDetailsRepo.setUnFollowToUser(followedUserId, followerUserId);
    }

    public void checkFollow(String followerUserId, String followedUserId) {
        userDetailsRepo.checkFollow(followerUserId, followedUserId);
    }

    public void countFollowerAndFollowing(String followedUserId) {
        userDetailsRepo.countFollower(followedUserId);
        userDetailsRepo.countFollowing(followedUserId);
    }


    public MutableLiveData<String> getCheckFollowLiveData() {
        return checkFollowLiveData;
    }

    public MutableLiveData<String> getFollowerNumberLiveData() {
        return followerNumberLiveData;
    }

    public MutableLiveData<String> getFollowingNumberLiveData() {
        return followingNumberLiveData;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }
}