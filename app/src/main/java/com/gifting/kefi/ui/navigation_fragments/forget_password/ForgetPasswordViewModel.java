package com.gifting.kefi.ui.navigation_fragments.forget_password;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gifting.kefi.ui.navigation_fragments.register.RegisterRepo;

public class ForgetPasswordViewModel extends ViewModel {
    ForgetPasswordRepo forgetPasswordRepo;
    private MutableLiveData<String> failText;
    private MutableLiveData<String> successText;


    public ForgetPasswordViewModel() {
        this.forgetPasswordRepo = new ForgetPasswordRepo();
        this.failText = forgetPasswordRepo.getFailText();
        this.successText = forgetPasswordRepo.getSuccessText();
    }
    public void sendEmailReset(String email){
        forgetPasswordRepo.sendVerificationMailResetPassword(email);
    }

    public MutableLiveData<String> getSuccessText() {
        return successText;
    }

    public MutableLiveData<String> getFail() {
        return failText;
    }
}