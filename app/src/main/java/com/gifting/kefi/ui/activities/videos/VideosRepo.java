package com.gifting.kefi.ui.activities.videos;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.gifting.kefi.data.model.VideoModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VideosRepo {

    private MutableLiveData<ArrayList<VideoModel>> videoLiveData;
    private MutableLiveData<String> failText;
    private Query query;
    private ArrayList<VideoModel> videoModels;

    public VideosRepo() {
        videoLiveData = new MutableLiveData<>();
        failText = new MutableLiveData<>();
        videoModels = new ArrayList<>();
    }

    public void getAllVideo(String language) {
        query = FirebaseDatabase.getInstance().getReference("VideoStructure").child("Videos").child(language);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        //Now get products
                        VideoModel videoModel = ds.getValue(VideoModel.class);
                        videoModels.add(videoModel);
                    }
                    videoLiveData.postValue(videoModels);
                } else {
                    failText.postValue("No item found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public MutableLiveData<ArrayList<VideoModel>> getVideoLiveData() {
        return videoLiveData;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }
}
