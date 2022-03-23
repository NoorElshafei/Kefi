package com.gifting.kefi.ui.navigation_fragments.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gifting.kefi.data.model.User;

public class LoginViewModel extends ViewModel {
    private LoginRepo loginRepo;
    private MutableLiveData<User> userLiveData;
    private MutableLiveData<String> failText;

    public LoginViewModel() {
        this.loginRepo = new LoginRepo();
        this.userLiveData = loginRepo.getUserLiveData();
        this.failText = loginRepo.getFailText();
    }

    public void login(String email,String password){
        loginRepo.login(email,password);
    }
    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<String> getFail() {
        return failText;
    }
}