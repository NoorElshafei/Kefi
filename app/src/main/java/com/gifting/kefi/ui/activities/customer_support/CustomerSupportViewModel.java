package com.gifting.kefi.ui.activities.customer_support;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gifting.kefi.data.model.UserMessageModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class CustomerSupportViewModel extends ViewModel {

    private DatabaseReference reference;
    private MutableLiveData<String> successText;
    private MutableLiveData<String> failText;


    public void setRateApp(String userId, String userName, String userPhone, String message) {
        successText = new MutableLiveData<>();
        failText = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("UserMessages").child(userId);
        UserMessageModel userMessageModel = new UserMessageModel(Calendar.getInstance().getTimeInMillis(), message, userName, userPhone, userId);
        reference.setValue(userMessageModel).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("nooooooooooor222", "onComplete2: ");
                successText.postValue("successText");
            } else {
                Log.d("nooooooooooor222", "faliar2: " + task.getException().getMessage());
                failText.postValue("there is no permission to store user data in database:" + task.getException().getMessage());
            }
        });
    }

    public MutableLiveData<String> getSuccessText() {
        return successText;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }
}
