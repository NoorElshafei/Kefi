package com.gifting.kefi.ui.activities.following_page;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gifting.kefi.data.model.User;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class FollowingViewModel extends ViewModel {
    private MutableLiveData<ArrayList<User>> userLiveDate;
    private MutableLiveData<String> failText;
    private FollowingRepo followingRepo;

    public FollowingViewModel() {
    }

    public void getUserDetails(Query query) {
        defineRepo();
        followingRepo.getUserIds(query);
    }

    private void defineRepo() {
        followingRepo = new FollowingRepo();
        userLiveDate = followingRepo.getUserLiveDate();
        failText = followingRepo.getFailText();
    }


    public MutableLiveData<ArrayList<User>> getUserLiveDate() {
        return userLiveDate;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }
}
