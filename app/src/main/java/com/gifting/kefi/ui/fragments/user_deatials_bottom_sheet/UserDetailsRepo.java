package com.gifting.kefi.ui.fragments.user_deatials_bottom_sheet;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDetailsRepo {
    private MutableLiveData<String> checkFollowLiveData;
    private MutableLiveData<String> followingNumberLiveData;
    private MutableLiveData<String> followerNumberLiveData;
    private MutableLiveData<String> failText;
    private DatabaseReference reference;

    public UserDetailsRepo() {
        followerNumberLiveData = new MutableLiveData<>();
        followingNumberLiveData = new MutableLiveData<>();
        checkFollowLiveData = new MutableLiveData<>();
        failText = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference();
    }

    public void setFollowToUser(String followedUserId, String followerUserId) {
        reference.child("Followers").child(followedUserId).child(followerUserId).setValue(true).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                reference.child("Following").child(followerUserId).child(followedUserId).setValue(true).addOnCompleteListener(task1 -> {
                    if (!task1.isSuccessful())
                        failText.postValue(task1.getException().getMessage());
                });
            } else
                failText.postValue(task.getException().getMessage());
        });

    }

    public void setUnFollowToUser(String followedUserId, String followerUserId) {
        reference.child("Followers").child(followedUserId).child(followerUserId).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                reference.child("Following").child(followerUserId).child(followedUserId).removeValue().addOnCompleteListener(task1 -> {
                    if (!task1.isSuccessful())
                        failText.postValue(task1.getException().getMessage());
                });
            } else
                failText.postValue(task.getException().getMessage());
        });

    }

    public void checkFollow(String followedUserId, String followerUserId) {
        reference.child("Following").child(followerUserId).child(followedUserId).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    checkFollowLiveData.postValue("Unfollow");
                } else {
                    checkFollowLiveData.postValue("Follow");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void countFollowing(String followerUserId) {
        reference.child("Following").child(followerUserId).orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        count++;
                    }
                }
                followingNumberLiveData.postValue(count + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void countFollower(String followedUserId) {
        reference.child("Followers").child(followedUserId).orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        count++;
                    }
                }
                followerNumberLiveData.postValue(count + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public MutableLiveData<String> getCheckFollowLiveData() {
        return checkFollowLiveData;
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

}
