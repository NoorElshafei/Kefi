package com.gifting.kefi.ui.activities.product_details;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gifting.kefi.data.model.Products;
import com.gifting.kefi.data.model.ReviewModel;
import com.gifting.kefi.data.model.VideoModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductDetailsRepoLiveData extends LiveData<ArrayList<ReviewModel>> {
    private static final String LOG_TAG = "FirebaseQueryLiveData";
    private Query query;
    private MyValueEventListener listener = new MyValueEventListener();
    private ArrayList<ReviewModel> reviewModelArrayList;
    private MutableLiveData<String> failText;


    public ProductDetailsRepoLiveData(Query query) {
        this.query = query;
        failText = new MutableLiveData<>();
    }

    public ProductDetailsRepoLiveData(DatabaseReference ref) {
        this.query = ref;

    }


    @Override
    protected void onActive() {
        super.onActive();
        query.addValueEventListener(listener);
        Log.d("nooooooooooooooor", "onActive");

    }

    @Override
    protected void onInactive() {
        super.onInactive();
        query.removeEventListener(listener);
        Log.d("nooooooooooooooor", "onInactive");

    }


    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            reviewModelArrayList = new ArrayList<>();
            if (snapshot.exists()) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    ReviewModel reviewModel = snapshot1.getValue(ReviewModel.class);
                    reviewModelArrayList.add(reviewModel);
                }
                setValue(reviewModelArrayList);
            }else{
                failText.postValue("No comments found");
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.e("nooooooooooooooor", "Can't listen to query " + query, error.toException());

        }
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }
}
