package com.gifting.kefi.ui.activities.shipping_address_settings;

import androidx.lifecycle.ViewModel;

import com.gifting.kefi.ui.activities.shipping_address_settings.ShippingAddressRepoLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ShippingAddressViewModel extends ViewModel {
    private Query query;
    private ShippingAddressRepoLiveData liveData;
    private FirebaseAuth firebaseAuth;

    public ShippingAddressViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        query = FirebaseDatabase.getInstance().getReference("Address").child(firebaseAuth.getUid()).orderByKey();
    }

    public void setAllAddresses() {
        liveData = new ShippingAddressRepoLiveData(query);
    }


    public ShippingAddressRepoLiveData getDataSnapshotLiveData() {
        return liveData;
    }


}