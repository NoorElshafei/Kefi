package com.gifting.kefi.ui.fragments.edit_head_line;

import androidx.lifecycle.MutableLiveData;

import com.gifting.kefi.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditHeadLineRepo {

    private MutableLiveData<String> successText;
    private MutableLiveData<String> failText;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;

    public EditHeadLineRepo() {
        failText = new MutableLiveData<>();
        successText = new MutableLiveData<>();
        firebaseAuth= FirebaseAuth.getInstance();
    }

    public void changeHeadLine(String headLine) {
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getUid()).child("headLine");
        reference.setValue(headLine).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
               successText.postValue(headLine);

            } else {
                failText.postValue(task.getException().getMessage());
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
