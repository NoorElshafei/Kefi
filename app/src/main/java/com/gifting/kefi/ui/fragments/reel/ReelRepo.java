package com.gifting.kefi.ui.fragments.reel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.gifting.kefi.data.model.ReelVideoModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReelRepo {
    private MutableLiveData<ArrayList<String>> followingNumberLiveData;
    private MutableLiveData<ArrayList<ReelVideoModel>> arrayListMutableLiveData;
    private MutableLiveData<String> failText;
    private DatabaseReference reference;
    private ArrayList<String> listIds;
    private ArrayList<ReelVideoModel> reelVideoModels;

    public ReelRepo() {
        followingNumberLiveData = new MutableLiveData<>();
        arrayListMutableLiveData = new MutableLiveData<>();
        failText = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference();
        listIds = new ArrayList<>();
        reelVideoModels = new ArrayList<>();
    }


    public void getFollowingUsers(String followerUserId) {
        reference.child("Following").child(followerUserId).orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        listIds.add(snapshot1.getKey());
                    }
                    followingNumberLiveData.postValue(listIds);
                } else {
                    if (reelVideoModels.size() == 0)
                        failText.postValue("Follow blummers to see their reels");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                failText.postValue(error.getMessage());
            }
        });

    }

    public void getPostsOfUsers(ArrayList<String> listIds) {
        for (int i = 0; i < listIds.size(); i++) {
            final int count = i;
            reference.child("ReelVideos").child(listIds.get(i)).orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                            ReelVideoModel reelVideoModel = snapshot1.getValue(ReelVideoModel.class);
                            if (!reelVideoModel.getStatus().equals("pending") && !reelVideoModel.getStatus().equals("reject"))
                                reelVideoModels.add(reelVideoModel);
                        }
                    }
                    if (count >= listIds.size() - 1) {
                        if (reelVideoModels.size() == 0) {
                            failText.postValue("No Videos exist now");
                        } else {
                            arrayListMutableLiveData.postValue(reelVideoModels);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    failText.postValue(error.getMessage());
                }
            });
        }

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
