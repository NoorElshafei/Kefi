package com.gifting.kefi.ui.fragments.rate_order_bottom_sheet;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.gifting.kefi.data.model.RateOrderModel;
import com.gifting.kefi.data.model.ReviewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RateOrderRepo {
    private DatabaseReference reference;
    private MutableLiveData<String> successText;
    private MutableLiveData<String> failText;

    public RateOrderRepo() {
        failText = new MutableLiveData<>();
        successText = new MutableLiveData<>();


    }

    public void storeRateInDatabase(RateOrderModel rateOrderModel) {
        reference = FirebaseDatabase.getInstance().getReference("OrderRating").child(rateOrderModel.getOrderId());

        reference.setValue(rateOrderModel).addOnCompleteListener(task -> {
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
