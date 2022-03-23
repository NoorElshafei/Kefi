package com.gifting.kefi.ui.activities.checkout;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gifting.kefi.data.model.OrderRequestModel;

public class CheckOutViewModel extends ViewModel {
    private CheckOutRepo checkOutRepo;
    private MutableLiveData<OrderRequestModel> userLiveData;
    private MutableLiveData<String> failText;


    public CheckOutViewModel() {
        this.checkOutRepo = new CheckOutRepo();
        this.userLiveData = checkOutRepo.getUserLiveData();
        this.failText = checkOutRepo.getFailText();
    }

    public void saveRequestInFirebase(OrderRequestModel orderRequestModel){
        checkOutRepo.storeRequestDataInDatabase(orderRequestModel);
    }
    public MutableLiveData<OrderRequestModel> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<String> getFail() {
        return failText;
    }
}
