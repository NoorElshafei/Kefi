package com.gifting.kefi.ui.activities.blumers;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.gifting.kefi.data.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BlumersRepo {
    private MutableLiveData<ArrayList<User>> userLiveData;
    private MutableLiveData<ArrayList<User>> userLiveData1;
    private MutableLiveData<String> failText;
    private Query query;
    private ArrayList<User> userArrayList;
    private ArrayList<User> userArrayList1;
    private String search = "dssd";

    public BlumersRepo() {
        userLiveData = new MutableLiveData<>();
        userLiveData1 = new MutableLiveData<>();
        failText = new MutableLiveData<>();
        query = FirebaseDatabase.getInstance().getReference("Users").orderByKey();
        userArrayList = new ArrayList<>();
        userArrayList1 = new ArrayList<>();

    }

    public void getUsers() {
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        //Now get products
                        User user = ds.getValue(User.class);
                        userArrayList.add(user);
                    }
                    userLiveData.postValue(userArrayList);
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

    public void setSearch(Query query, String searchTerm) {
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        //Now get products
                        User user = ds.getValue(User.class);
                        if (user.getSearch().contains(searchTerm)) {
                            userArrayList1.add(user);
                        }
                    }
                    userLiveData1.postValue(userArrayList1);
                } else {
                    failText.postValue("No User found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                failText.postValue(error.getMessage());

            }
        });
    }

    public MutableLiveData<ArrayList<User>> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }

    public MutableLiveData<ArrayList<User>> getUserLiveData1() {
        return userLiveData1;
    }
}
