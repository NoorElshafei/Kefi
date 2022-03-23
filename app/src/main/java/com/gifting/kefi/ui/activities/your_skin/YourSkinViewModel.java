package com.gifting.kefi.ui.activities.your_skin;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gifting.kefi.data.model.User;

public class YourSkinViewModel extends ViewModel {

    private MutableLiveData<User> userLiveData;
    private MutableLiveData<String> failText;
    private YourSkinRepo yourSkinRepo;

    public YourSkinViewModel() {
        yourSkinRepo = new YourSkinRepo();
        userLiveData = yourSkinRepo.getUserLiveData();
        failText = yourSkinRepo.getFailText();
    }

    public void storeUserData(User user){
        yourSkinRepo.storeUserDataInDatabase(user);
    }

    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }
}
