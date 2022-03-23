package com.gifting.kefi.ui.navigation_fragments.verify_phone;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerificationPhoneRepo {
    private MutableLiveData<String> codeVerification;
    private MutableLiveData<String> failText;
    private MutableLiveData<PhoneAuthCredential> successText;
    private FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private String phone;


    public VerificationPhoneRepo() {
        codeVerification = new MutableLiveData<>();
        failText = new MutableLiveData<>();
        successText = new MutableLiveData<>();
        firebaseAuth = FirebaseAuth.getInstance();
        getCallBacks();
    }


    public MutableLiveData<String> getCodeVerification() {
        return codeVerification;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }

    public MutableLiveData<PhoneAuthCredential> getSuccessText() {
        return successText;
    }

    public void sendVerificationCode(String mobile, Activity activity) {
        phone = mobile;
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(mobile)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(activity)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void verifyVerificationCode(String code) {
        //creating the credential
        if (mVerificationId != null) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            signInWithPhoneAuthCredential(credential);
        } else {
            failText.postValue("Message not sent yet");
        }

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        //verification successful we will start the profile activity
                        successText.postValue(credential);

                    } else {
                        //verification unsuccessful.. display an error message
                        String message = "Somthing is wrong, we will fix it soon...";
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid code entered...";
                        }
                        failText.postValue(message);
                    }
                });
    }

    private void getCallBacks() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                //Getting the code sent by SMS
                String code = phoneAuthCredential.getSmsCode();
                Log.d("nooooooor", "onVerificationCompleted: ");

                if (code != null) {
                    codeVerification.postValue(code);
                    verifyVerificationCode(code);
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
//                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                failText.postValue(e.getMessage());
                Log.d("nooooooor", "onVerificationFailed: " + e.getLocalizedMessage());
            }

            //when the code is generated then this method will receive the code.
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                // super.onCodeSent(s, forceResendingToken);
                Log.d("nooooooor", "onCodeSent: ");

                //storing the verification id that is sent to the user
                mVerificationId = s;
            }
        };

    }
}
