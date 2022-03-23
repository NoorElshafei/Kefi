package com.gifting.kefi.ui.navigation_fragments.verify_phone;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.PhoneAuthCredential;

public class VerificationPhoneViewModel extends ViewModel {
    private VerificationPhoneRepo verificationPhoneRepo;
    private MutableLiveData<String>codeVerification;
    private MutableLiveData<String>failText;
    private MutableLiveData<PhoneAuthCredential>successText;
    private Activity activity;

    public VerificationPhoneViewModel(Activity activity) {
        verificationPhoneRepo = new VerificationPhoneRepo();
        codeVerification = verificationPhoneRepo.getCodeVerification();
        failText = verificationPhoneRepo.getFailText();
        successText = verificationPhoneRepo.getSuccessText();
        this.activity = activity;

    }
    public void sendVerificationCode(String mobile){
        verificationPhoneRepo.sendVerificationCode(mobile,activity);
    }

    public  void verifyVerificationCode(String code){
        verificationPhoneRepo.verifyVerificationCode(code);
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
}