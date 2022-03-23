package com.gifting.kefi.ui.activities.shipping_address_checkout;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.gifting.kefi.data.model.AddressModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShippingAddressCheckoutRepoLiveData extends LiveData<ArrayList<AddressModel>> {
    private static final String LOG_TAG = "FirebaseQueryLiveData";
    private Query query;
    private MyValueEventListener listener = new MyValueEventListener();
    private ArrayList<AddressModel> addressFirebaseModelArrayList;


    public ShippingAddressCheckoutRepoLiveData(Query query) {
        this.query = query;
    }

    public ShippingAddressCheckoutRepoLiveData(DatabaseReference ref) {
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
            addressFirebaseModelArrayList = new ArrayList<>();
            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                AddressModel addressModel = snapshot1.getValue(AddressModel.class);
                addressFirebaseModelArrayList.add(addressModel);
            }
            setValue(addressFirebaseModelArrayList);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.e("nooooooooooooooor", "Can't listen to query " + query, error.toException());

        }
    }


}
