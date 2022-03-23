package com.gifting.kefi.ui.activities.shipping_address_checkout;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ShippingAddressCheckoutViewModel extends ViewModel {
    private Query query;
    private ShippingAddressCheckoutRepoLiveData liveData;
    private FirebaseAuth firebaseAuth;

    public ShippingAddressCheckoutViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        query = FirebaseDatabase.getInstance().getReference("Address").child(firebaseAuth.getUid()).orderByKey();
    }

    public void setAllAddresses() {
        liveData = new ShippingAddressCheckoutRepoLiveData(query);
    }


    public ShippingAddressCheckoutRepoLiveData getDataSnapshotLiveData() {
        return liveData;
    }


}