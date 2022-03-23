package com.gifting.kefi.ui.navigation_fragments.splash;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gifting.kefi.data.model.NotificationModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashViewModel extends ViewModel {
    private DatabaseReference reference;
    private MutableLiveData<String> failText;
    private MutableLiveData<String> successMessage;
    private MutableLiveData<Boolean> checkUpdate;

    public SplashViewModel() {
        reference = FirebaseDatabase.getInstance().getReference();
        failText = new MutableLiveData<>();
        successMessage = new MutableLiveData<>();
    }

    public void setNotificationInFirebase(NotificationModel notificationModel) {
        DatabaseReference notificationId = reference.child("Notifications").child(FirebaseAuth.getInstance().getUid()).push();
        notificationModel.setId(notificationId.getKey());
        notificationId.setValue(notificationModel).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                successMessage.postValue("success");
            } else {
                failText.postValue(task.getException().getMessage());
            }

        });
    }


    public void getCheckUpdateMethod() {
        checkUpdate = new MutableLiveData<>();
        DatabaseReference updateReference = reference.child("Update");
        updateReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    checkUpdate.postValue(snapshot.getValue(Boolean.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }

    public MutableLiveData<String> getSuccessMessage() {
        return successMessage;
    }

    public MutableLiveData<Boolean> getCheckUpdate() {
        return checkUpdate;
    }


}