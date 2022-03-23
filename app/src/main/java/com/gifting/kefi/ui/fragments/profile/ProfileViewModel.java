package com.gifting.kefi.ui.fragments.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gifting.kefi.data.model.ReelVideoModel;

import java.util.ArrayList;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<String> followerNumberLiveData;
    private MutableLiveData<String> followingNumberLiveData;
    private MutableLiveData<ArrayList<ReelVideoModel>> userVideosLiveData;
    private MutableLiveData<String> failText;
    private ProfileRepo profileRepo;

    public ProfileViewModel() {
    }

    public void countFollowerAndFollowing(String followedUserId) {
        profileRepo = new ProfileRepo();
        followingNumberLiveData = profileRepo.getFollowingNumberLiveData();
        followerNumberLiveData = profileRepo.getFollowerNumberLiveData();
        failText = profileRepo.getFailText();
        profileRepo.countFollowing(followedUserId);
    }

    public void getPostsOfUsers(String userId) {
        profileRepo = new ProfileRepo();
        userVideosLiveData = profileRepo.getUserVideosLiveData();
        failText = profileRepo.getFailText();
        profileRepo.getUserVideos(userId);
    }
    public void getPostsOfUsersDetails(String userId) {
        profileRepo = new ProfileRepo();
        userVideosLiveData = profileRepo.getUserVideosLiveData();
        failText = profileRepo.getFailText();
        profileRepo.getUserVideosUserDetails(userId);
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

    public MutableLiveData<ArrayList<ReelVideoModel>> getUserVideosLiveData() {
        return userVideosLiveData;
    }
}