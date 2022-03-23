package com.gifting.kefi.ui.activities.following_page;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.gifting.kefi.data.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FollowingRepo {
    private MutableLiveData<ArrayList<User>> userLiveDate;
    private MutableLiveData<String> failText;
    private DatabaseReference reference;
    private ArrayList<User> userArrayList;
    private ArrayList<String> listIds;
    private Query query;

    public FollowingRepo() {
        userLiveDate = new MutableLiveData<>();
        failText = new MutableLiveData<>();
        userArrayList = new ArrayList<>();
        listIds = new ArrayList<>();

    }

    public void getUserIds(Query query) {
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        listIds.add(snapshot1.getKey());
                    }
                    getUserDetails();

                } else {
                    failText.postValue("No Users Found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                failText.postValue(error.getMessage());
            }
        });

    }

    private void getUserDetails() {
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        User user = snapshot1.getValue(User.class);
                        for (String id : listIds) {
                            if (user.getId().equals(id)) {
                                userArrayList.add(user);
                            }
                        }

                    }
                    userLiveDate.postValue(userArrayList);
                } else {
                    failText.postValue("No item found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                failText.postValue(error.getMessage());
            }
        });
    }

    public MutableLiveData<ArrayList<User>> getUserLiveDate() {
        return userLiveDate;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }


}
