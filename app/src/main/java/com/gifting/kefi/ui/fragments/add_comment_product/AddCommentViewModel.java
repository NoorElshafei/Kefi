package com.gifting.kefi.ui.fragments.add_comment_product;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gifting.kefi.data.model.ReviewModel;

public class AddCommentViewModel extends ViewModel {
    private MutableLiveData<String> successText;
    private MutableLiveData<String> failText;
    private AddCommentRepo addCommentRepo;

    public AddCommentViewModel() {
        addCommentRepo = new AddCommentRepo();
        successText = addCommentRepo.getSuccessText();
        failText = addCommentRepo.getFailText();
    }

    public void addRateInDatabase(ReviewModel reviewModel) {
        addCommentRepo.storeRateInDatabase(reviewModel);
    }

    public MutableLiveData<String> getSuccessText() {
        return successText;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }
}