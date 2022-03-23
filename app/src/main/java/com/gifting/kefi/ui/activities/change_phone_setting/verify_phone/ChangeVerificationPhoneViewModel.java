package com.gifting.kefi.ui.activities.change_phone_setting.verify_phone;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChangeVerificationPhoneViewModel extends ViewModel {
    private ChangeVerificationPhoneRepo verificationPhoneRepo;
    private MutableLiveData<String>codeVerification;
    private MutableLiveData<String>failText;
    private MutableLiveData<String>successText;
    private Activity activity;

    public ChangeVerificationPhoneViewModel(Activity activity) {
        verificationPhoneRepo = new ChangeVerificationPhoneRepo();
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

    public MutableLiveData<String> getSuccessText() {
        return successText;
    }
}