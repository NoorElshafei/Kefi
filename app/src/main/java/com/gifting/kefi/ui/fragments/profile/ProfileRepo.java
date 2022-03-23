package com.gifting.kefi.ui.fragments.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.gifting.kefi.data.model.ReelVideoModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileRepo {
    private MutableLiveData<String> followingNumberLiveData;
    private MutableLiveData<String> followerNumberLiveData;
    private MutableLiveData<ArrayList<ReelVideoModel>> userVideosLiveData;
    private MutableLiveData<String> failText;
    private DatabaseReference reference;
    private ArrayList<ReelVideoModel> userVideosList;

    public ProfileRepo() {
        followerNumberLiveData = new MutableLiveData<>();
        followingNumberLiveData = new MutableLiveData<>();
        userVideosLiveData = new MutableLiveData<>();
        failText = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference();
    }

    public void countFollowing(String followerUserId) {
        reference.child("Following").child(followerUserId).orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        count++;
                    }
                }
                countFollower(followerUserId, count + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void countFollower(String followedUserId, String count1) {
        reference.child("Followers").child(followedUserId).orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        count++;
                    }
                }
                followingNumberLiveData.postValue(count1 + "");
                followerNumberLiveData.postValue(count + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getUserVideos(String userId) {
        userVideosList = new ArrayList<>();
        reference.child("ReelVideos").child(userId).orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        ReelVideoModel reelVideoModel = snapshot1.getValue(ReelVideoModel.class);
                        userVideosList.add(reelVideoModel);

                    }
                    userVideosLiveData.postValue(userVideosList);
                } else {
                    failText.postValue("No Videos Found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getUserVideosUserDetails(String userId) {
        userVideosList = new ArrayList<>();
        reference.child("ReelVideos").child(userId).orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        ReelVideoModel reelVideoModel = snapshot1.getValue(ReelVideoModel.class);
                        if (!reelVideoModel.getStatus().equals("pending") && !reelVideoModel.getStatus().equals("reject"))
                            userVideosList.add(reelVideoModel);

                    }
                    userVideosLiveData.postValue(userVideosList);
                } else {
                    failText.postValue("No Videos Found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public MutableLiveData<String> getFollowingNumberLiveData() {
        return followingNumberLiveData;
    }

    public MutableLiveData<String> getFollowerNumberLiveData() {
        return followerNumberLiveData;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }

    public MutableLiveData<ArrayList<ReelVideoModel>> getUserVideosLiveData() {
        return userVideosLiveData;
    }
}
