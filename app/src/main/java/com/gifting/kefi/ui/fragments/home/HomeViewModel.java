package com.gifting.kefi.ui.fragments.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<String> stringMutableLiveData;
    private DatabaseReference reference;
    private Query query;


    public HomeViewModel() {
        stringMutableLiveData = new MutableLiveData<>();
    }

    private void getSizeOfNotification() {
        reference = FirebaseDatabase.getInstance().getReference("Notifications");
        query = reference.child(FirebaseAuth.getInstance().getUid()).orderByValue();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        count++;
                    }
                }
                stringMutableLiveData.postValue(count + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public MutableLiveData<String> getStringMutableLiveData() {
        return stringMutableLiveData;
    }


}