package com.gifting.kefi.ui.fragments.reel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gifting.kefi.data.model.ReelVideoModel;

import java.util.ArrayList;

public class ReelViewModel extends ViewModel {

    private MutableLiveData<ArrayList<String>> followingNumberLiveData;
    private MutableLiveData<String> failText;
    private ReelRepo reelRepo;
    private MutableLiveData<ArrayList<ReelVideoModel>> arrayListMutableLiveData;


    public ReelViewModel() {
    }

    public void getUserFollowingIds(String followedUserId) {
        reelRepo = new ReelRepo();
        failText =reelRepo.getFailText();
        followingNumberLiveData = reelRepo.getFollowingNumberLiveData();
        reelRepo.getFollowingUsers(followedUserId);
    }

    public void getPostsOfUsers(ArrayList<String> listIds) {
        reelRepo = new ReelRepo();
        failText =reelRepo.getFailText();
        arrayListMutableLiveData= reelRepo.getArrayListMutableLiveData();
        reelRepo.getPostsOfUsers(listIds);
    }




    public MutableLiveData<ArrayList<String>> getFollowingNumberLiveData() {
        return followingNumberLiveData;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }

    public MutableLiveData<ArrayList<ReelVideoModel>> getArrayListMutableLiveData() {
        return arrayListMutableLiveData;
    }
}