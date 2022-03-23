package com.gifting.kefi.ui.activities.face;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gifting.kefi.data.model.Products;
import com.gifting.kefi.data.model.VideoModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FaceRepo<T> extends LiveData<T> {
    private Query query, query1;
    private ArrayList<VideoModel> videoModelArrayList;
    private ArrayList<Products> productsArrayList;
    private MutableLiveData<String> failText;

    public FaceRepo() {
        videoModelArrayList = new ArrayList<>();
        productsArrayList = new ArrayList<>();
        failText = new MutableLiveData<>();
    }

    public void getFaceVideo(String videoType, String lang) {
        query = FirebaseDatabase.getInstance().getReference("VideoStructure").child("Videos").child(lang).orderByChild("videoType");

        query.equalTo(videoType).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        VideoModel videoModel = snapshot1.getValue(VideoModel.class);
                        videoModelArrayList.add(videoModel);
                    }
                    postValue((T) videoModelArrayList);
                } else {
                    failText.postValue("no video");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                failText.postValue(error.getMessage());
            }
        });
    }

    public void getFaceProducts(String productType, String lang) {
        query1 = FirebaseDatabase.getInstance().getReference("Products").child(lang).orderByChild("product_type");
        query1.equalTo(productType).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Products product = snapshot1.getValue(Products.class);
                        productsArrayList.add(product);
                    }
                    postValue((T) productsArrayList);
                } else {
                    failText.postValue("no product");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                failText.postValue(error.getMessage());
            }
        });
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }

}
