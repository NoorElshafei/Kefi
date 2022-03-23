package com.gifting.kefi.ui.navigation_fragments.register;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gifting.kefi.data.model.User;

public class RegisterViewModel extends ViewModel {
    private RegisterRepo registerRepo;
    private MutableLiveData<User> userLiveData;
    private MutableLiveData<String> failText;


    public RegisterViewModel() {
        this.registerRepo = new RegisterRepo();
        this.userLiveData = registerRepo.getUserLiveData();
        this.failText = registerRepo.getFailText();
    }

    public void register(String userName, String email,String password,long birthDate){

        registerRepo.register(userName,email,password,birthDate);
    }
    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<String> getFail() {
        return failText;
    }
}