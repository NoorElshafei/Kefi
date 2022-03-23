package com.gifting.kefi.ui.fragments.add_comment_product;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.gifting.kefi.data.model.ReviewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCommentRepo {
    private DatabaseReference reference;
    private MutableLiveData<String> successText;
    private MutableLiveData<String> failText;

    public AddCommentRepo() {
        failText = new MutableLiveData<>();
        successText = new MutableLiveData<>();


    }

    public void storeRateInDatabase(ReviewModel reviewModel) {
        reference = FirebaseDatabase.getInstance().getReference("Rating/" + reviewModel.getProductID() + "/" + reviewModel.getUserID() + "/");

        reference.setValue(reviewModel).addOnCompleteListener(task -> {
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
