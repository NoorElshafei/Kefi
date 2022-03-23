package com.gifting.kefi.ui.fragments.add_comment_youtube_video;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.gifting.kefi.data.model.VideoCommentModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCommentVideoRepo {
    private DatabaseReference reference;
    private MutableLiveData<String> successText;
    private MutableLiveData<String> failText;

    public AddCommentVideoRepo() {
        failText = new MutableLiveData<>();
        successText = new MutableLiveData<>();


    }

    public void storeRateInDatabase(VideoCommentModel videoCommentModel) {
        reference = FirebaseDatabase.getInstance().getReference().child("VideoStructure").child("Comments").child(videoCommentModel.getVideoID());
        if (videoCommentModel.getCommentID() == null) {
            reference = reference.push();
            videoCommentModel.setCommentID(reference.getKey());
        } else {
            reference = reference.child(videoCommentModel.getCommentID());
        }
        reference.setValue(videoCommentModel).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("nooooooooooor222", "onComplete2: ");
                successText.postValue("successText");
            } else {
                Log.d("nooooooooooor222", "faliar2: " + task.getException().getMessage());
                failText.postValue("there is no permission to store user data in database:" + task.getException().getMessage());
            }
        });
    }

    public MutableLiveData<String> getSuccessText() {
        return successText;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }
}
