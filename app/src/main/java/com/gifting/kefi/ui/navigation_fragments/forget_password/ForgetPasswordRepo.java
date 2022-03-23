package com.gifting.kefi.ui.navigation_fragments.forget_password;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordRepo {

    private FirebaseAuth firebaseAuth;
    private MutableLiveData<String> failText;
    private MutableLiveData<String> successText;

    public ForgetPasswordRepo() {
        firebaseAuth = FirebaseAuth.getInstance();
        failText = new MutableLiveData<>();
        successText = new MutableLiveData<>();

    }

    public void sendVerificationMailResetPassword(String email) {


        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        successText.postValue("");
                        //Log.d(TAG, "Email sent.");
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
