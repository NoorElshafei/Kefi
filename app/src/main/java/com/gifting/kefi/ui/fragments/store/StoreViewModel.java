package com.gifting.kefi.ui.fragments.store;

import androidx.lifecycle.ViewModel;

import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class StoreViewModel extends ViewModel {
    private DatabaseReference reference;
    private Query query;
    private StoreRepoLiveData liveData;
    private StoreRepoLiveData liveData1;

    public StoreViewModel() {

        reference = FirebaseDatabase.getInstance().getReference("Products");

    }

    public void setLoadFirstProducts(String lang) {
        query = reference.child(lang).orderByKey().limitToFirst(10);
        liveData = new StoreRepoLiveData(query);
    }

    public void setLoadNextProducts(String id,String lang) {
        query = reference.child(lang).orderByKey().startAfter(id).limitToFirst(10);
        liveData1 = new StoreRepoLiveData(query);
    }


    public StoreRepoLiveData getDataSnapshotLiveData() {
        return liveData;
    }
    public StoreRepoLiveData getDataSnapshotLiveData1() {
        return liveData1;
    }

}