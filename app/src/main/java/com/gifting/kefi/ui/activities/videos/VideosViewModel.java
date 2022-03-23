package com.gifting.kefi.ui.activities.videos;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gifting.kefi.data.model.VideoModel;

import java.util.ArrayList;

public class VideosViewModel extends ViewModel {
    private MutableLiveData<ArrayList<VideoModel>> videoLiveData;
    private MutableLiveData<String> failText;
    private VideosRepo videosRepo;

    public VideosViewModel() {
        videosRepo = new VideosRepo();
        videoLiveData = videosRepo.getVideoLiveData();
        failText = videosRepo.getFailText();
    }

    public void getAllVideos(String language){
        videosRepo.getAllVideo(language);
    }

    public MutableLiveData<ArrayList<VideoModel>> getVideoLiveData() {
        return videoLiveData;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }


}
