package com.gifting.kefi.ui.activities.shipping_address_edit;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.gifting.kefi.data.model.AddressModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAddressRepo {
    private MutableLiveData<AddressModel> addressModelMutableLiveData;
    private MutableLiveData<String> failText;
    private DatabaseReference reference;

    public AddAddressRepo() {
        addressModelMutableLiveData = new MutableLiveData<>();
        failText= new MutableLiveData<>();
    }

    public void storeAddressDataInDatabase(AddressModel addressModel, String userId) {
        if (addressModel.getAddress_id().equals("")) {
            reference = FirebaseDatabase.getInstance().getReference("Address").child(userId).push();
            addressModel.setAddress_id(reference.getKey());
        }else{
            reference = FirebaseDatabase.getInstance().getReference("Address").child(userId).child(addressModel.getAddress_id());
        }
        reference.setValue(addressModel).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("nooooooooooor222", "onComplete2: ");
                addressModelMutableLiveData.postValue(addressModel);
            } else {
                Log.d("nooooooooooor222", "faliar2: " + task.getException().getMessage());
                failText.postValue("there is no permission to store user data in database:" + task.getException().getMessage());
            }
        });
    }

    public MutableLiveData<AddressModel> getAddressModelMutableLiveData() {
        return addressModelMutableLiveData;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }
}
