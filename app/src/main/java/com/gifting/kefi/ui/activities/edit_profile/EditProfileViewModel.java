package com.gifting.kefi.ui.activities.edit_profile;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditProfileViewModel extends ViewModel {
    private EditProfileRepo editProfileRepo;
    private MutableLiveData<String> userLiveData;
    private MutableLiveData<String> failText;
    private MutableLiveData<String> urlImageLiveData;
    private MutableLiveData<String> successDeleteText;

    public EditProfileViewModel() {
        this.editProfileRepo = new EditProfileRepo();
        this.userLiveData = editProfileRepo.getUserLiveData();
        this.failText = editProfileRepo.getFailText();
    }

    public void editPassword(String email, String oldPassword, String newPassword) {
        editProfileRepo.editPassword(email, oldPassword, newPassword);
    }

    public void uploadImage(Uri uri) {
        editProfileRepo.uploadImage(uri);
        this.urlImageLiveData = editProfileRepo.getUrlImageLiveData();
    }

    public void deleteImage() {
        editProfileRepo.deleteImage();
        successDeleteText = editProfileRepo.getSuccessDeleteText();
    }


    public MutableLiveData<String> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<String> getFail() {
        return failText;
    }

    public MutableLiveData<String> getUrlImageLiveData() {
        return urlImageLiveData;
    }

    public MutableLiveData<String> getSuccessDeleteText() {
        return successDeleteText;
    }
}
