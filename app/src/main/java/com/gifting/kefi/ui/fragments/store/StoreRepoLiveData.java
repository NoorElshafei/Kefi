package com.gifting.kefi.ui.fragments.store;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.gifting.kefi.data.model.Products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoreRepoLiveData extends LiveData<ArrayList<Products>> {
    private static final String LOG_TAG = "FirebaseQueryLiveData";
    private Query query;
    private MyValueEventListener listener = new MyValueEventListener();
    private ArrayList<Products>  productsArrayList;


    public StoreRepoLiveData(Query query) {
        this.query = query;
    }

    public StoreRepoLiveData(DatabaseReference ref) {
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
            productsArrayList = new ArrayList<>();
            for (DataSnapshot snapshot1:snapshot.getChildren()) {
                Products product = snapshot1.getValue(Products.class);
                productsArrayList.add(product);
            }
            setValue(productsArrayList);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.e("nooooooooooooooor", "Can't listen to query " + query, error.toException());

        }
    }


}
