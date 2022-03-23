package com.gifting.kefi.ui.fragments.search_reel;

import com.gifting.kefi.data.model.Products;
import com.gifting.kefi.ui.fragments.search.SearchRepo;

public class SearchReelRepo {
  /*  private static final String LOG_TAG = "FirebaseQueryLiveData";
    private Query query;
    private MyValueEventListener listener = new MyValueEventListener();
    private ArrayList<Products> productsArrayList;
    private MutableLiveData<String> noItemText;


    public  SearchReelRepo() {
        productsArrayList = new ArrayList<>();
        noItemText = new MutableLiveData<>();
    }


    public MutableLiveData<String> getNoItemText() {
        return noItemText;
    }

    public void setSearch(Query query) {
        this.query = query;
        query.addValueEventListener(listener);
    }


    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            productsArrayList.clear();
            if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //Now get products
                    Products products = ds.getValue(Products.class);
                    productsArrayList.add(products);
                }
                setValue(productsArrayList);
            } else {
                noItemText.postValue("No item found");
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.d("FIREBASE CRUD", error.getMessage());
            noItemText.postValue(error.getMessage());

        }
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        query.removeEventListener(listener);
    }*/
}
