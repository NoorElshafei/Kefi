package com.gifting.kefi.ui.activities.checkout;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.gifting.kefi.data.model.OrderRequestModel;
import com.gifting.kefi.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CheckOutRepo {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference;
    private MutableLiveData<OrderRequestModel> userLiveData;
    private MutableLiveData<String> failText;
    public CheckOutRepo() {
        firebaseAuth = FirebaseAuth.getInstance();
        userLiveData = new MutableLiveData<>();
        failText = new MutableLiveData<>();

    }
    public void storeRequestDataInDatabase(OrderRequestModel orderRequestModel) {
        reference = FirebaseDatabase.getInstance().getReference("Request/" + orderRequestModel.getUserId()).push();
        orderRequestModel.setRequestId(reference.getKey());

        reference.setValue(orderRequestModel).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("nooooooooooor222", "onComplete2: ");
                userLiveData.postValue(orderRequestModel);
            } else {
                Log.d("nooooooooooor222", "faliar2: " + task.getException().getMessage());
                failText.postValue("there is no permission to store user data in database:" + task.getException().getMessage());
            }
        });
    }
    public MutableLiveData<OrderRequestModel> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }
}
