package com.gifting.kefi.ui.activities.face;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gifting.kefi.data.model.Products;
import com.gifting.kefi.data.model.VideoModel;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class FaceViewModel extends ViewModel {
    private FaceRepo<ArrayList<VideoModel>> faceRepo;
    private FaceRepo<ArrayList<Products>> faceRepo1;
    private MutableLiveData<String> failText;
    private Query query;


    public FaceViewModel() {
        faceRepo = new FaceRepo<>();
        faceRepo1 = new FaceRepo<>();
        failText = faceRepo.getFailText();
    }

    public void getFaceVideos(String videoType,String lang) {
        faceRepo.getFaceVideo(videoType, lang);
    }
    public void getFaceProducts(String productType,String lang) {
        faceRepo1.getFaceProducts(productType,lang);
    }

    public FaceRepo<ArrayList<VideoModel>> getFaceRepo() {
        return faceRepo;
    }

    public FaceRepo<ArrayList<Products>> getFaceRepo1() {
        return faceRepo1;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }
}
