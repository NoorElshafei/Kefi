package com.gifting.kefi.ui.activities.shipping_address_edit;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gifting.kefi.data.model.AddressModel;
import com.google.firebase.auth.FirebaseAuth;

public class EditShippingAddressViewModel extends ViewModel {
    private AddAddressRepo addAddressRepo;
    private MutableLiveData<AddressModel> addressModelMutableLiveData;
    private MutableLiveData<String> failText;
    private FirebaseAuth firebaseAuth;

    public EditShippingAddressViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        addAddressRepo = new AddAddressRepo();
        addressModelMutableLiveData = addAddressRepo.getAddressModelMutableLiveData();
        failText = addAddressRepo.getFailText();
    }

    public void setNewAddress(AddressModel address) {
        addAddressRepo.storeAddressDataInDatabase(address, firebaseAuth.getUid());

    }

    public MutableLiveData<AddressModel> getAddressModelMutableLiveData() {
        return addressModelMutableLiveData;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }
}
