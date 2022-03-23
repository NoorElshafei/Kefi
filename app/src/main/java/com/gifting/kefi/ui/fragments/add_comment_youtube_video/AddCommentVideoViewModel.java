package com.gifting.kefi.ui.fragments.add_comment_youtube_video;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gifting.kefi.data.model.VideoCommentModel;

public class AddCommentVideoViewModel extends ViewModel {
    private MutableLiveData<String> successText;
    private MutableLiveData<String> failText;
    private AddCommentVideoRepo addCommentRepo;

    public AddCommentVideoViewModel() {
        addCommentRepo = new AddCommentVideoRepo();
        successText = addCommentRepo.getSuccessText();
        failText = addCommentRepo.getFailText();
    }

    public void addRateInDatabase(VideoCommentModel videoCommentModel) {
        addCommentRepo.storeRateInDatabase(videoCommentModel);
    }

    public MutableLiveData<String> getSuccessText() {
        return successText;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }
}