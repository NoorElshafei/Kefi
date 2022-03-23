package com.gifting.kefi.ui.fragments.search;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gifting.kefi.data.model.Products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchRepo extends LiveData<ArrayList<Products>> {
    private static final String LOG_TAG = "FirebaseQueryLiveData";
    private ArrayList<Products> productsArrayList;
    private MutableLiveData<String> noItemText;


    public SearchRepo() {
        productsArrayList = new ArrayList<>();
        noItemText = new MutableLiveData<>();
    }


    public MutableLiveData<String> getNoItemText() {
        return noItemText;
    }

    public void setSearch(Query query, String searchTerm) {
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productsArrayList.clear();
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        //Now get products
                        Products products = ds.getValue(Products.class);
                        if (products.getSearch().contains(searchTerm)) {
                            productsArrayList.add(products);
                        }
                    }
                    if (productsArrayList.size() > 0)
                        setValue(productsArrayList);
                    else
                        noItemText.postValue("No item found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                noItemText.postValue(error.getMessage());

            }
        });
    }
}
