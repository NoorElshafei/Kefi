package com.gifting.kefi.ui.activities.product_details;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gifting.kefi.data.model.Products;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProductDetailsViewModel extends ViewModel {
    private DatabaseReference reference, reference1;
    private Query query;
    private ProductDetailsRepoLiveData liveData;
    private ProductDetailsRepoLiveData liveData1;
    private MutableLiveData<Products> productsMutableLiveData;
    private MutableLiveData<String> failText;

    public ProductDetailsViewModel() {
        reference = FirebaseDatabase.getInstance().getReference("Rating");
        reference1 = FirebaseDatabase.getInstance().getReference("Products");


    }

    public void setLoadFirstReviews(String productId) {
        query = reference.child(productId).orderByKey().limitToFirst(10);
        liveData = new ProductDetailsRepoLiveData(query);
        failText = liveData.getFailText();
    }

    public void getProductDetails(String productId,String lang) {

        productsMutableLiveData = new MutableLiveData<>();
        failText = new MutableLiveData<>();
        reference1.child(lang).child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Products product = snapshot.getValue(Products.class);
                    productsMutableLiveData.postValue(product);
                } else {
                    failText.postValue("no item found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                failText.postValue(error.getMessage());
            }
        });
    }

    public void setLoadNextReviews(String userId, String productId) {
        query = reference.child(productId).orderByKey().startAfter(userId).limitToFirst(10);
        liveData1 = new ProductDetailsRepoLiveData(query);
        failText = liveData1.getFailText();
    }


    public ProductDetailsRepoLiveData getFirstReviewsLiveData() {
        return liveData;
    }

    public ProductDetailsRepoLiveData getNextReviewsLiveData1() {
        return liveData1;
    }

    public MutableLiveData<Products> getProductsMutableLiveData() {
        return productsMutableLiveData;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }
}