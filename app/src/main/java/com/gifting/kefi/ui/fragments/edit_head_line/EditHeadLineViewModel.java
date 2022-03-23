package com.gifting.kefi.ui.fragments.edit_head_line;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gifting.kefi.data.model.User;

public class EditHeadLineViewModel extends ViewModel {
    private MutableLiveData<String> failText;
    private MutableLiveData<String> successText;
    private EditHeadLineRepo editHeadLineRepo;


    public EditHeadLineViewModel() {
    }

    public void editHeadLine(String headLine) {
        editHeadLineRepo = new EditHeadLineRepo();
        failText = editHeadLineRepo.getFailText();
        successText = editHeadLineRepo.getSuccessText();

        editHeadLineRepo.changeHeadLine(headLine);
    }


    public MutableLiveData<String> getFailText() {
        return failText;
    }

    public MutableLiveData<String> getSuccessText() {
        return successText;
    }
}