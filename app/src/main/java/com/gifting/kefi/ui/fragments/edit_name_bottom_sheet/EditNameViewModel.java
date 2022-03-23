package com.gifting.kefi.ui.fragments.edit_name_bottom_sheet;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gifting.kefi.data.model.User;

public class EditNameViewModel extends ViewModel {
    private MutableLiveData<String> failText;
    private MutableLiveData<User> successText;
    private EditNameRepo editNameRepo;


    public EditNameViewModel() {
    }

    public void editName(User user) {
        editNameRepo = new EditNameRepo();
        failText = editNameRepo.getFailText();
        successText = editNameRepo.getSuccessText();

        editNameRepo.changeName(user);
    }


    public MutableLiveData<String> getFailText() {
        return failText;
    }

    public MutableLiveData<User> getSuccessText() {
        return successText;
    }
}