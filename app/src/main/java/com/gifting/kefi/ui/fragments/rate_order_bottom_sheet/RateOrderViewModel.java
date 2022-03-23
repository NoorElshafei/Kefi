package com.gifting.kefi.ui.fragments.rate_order_bottom_sheet;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gifting.kefi.data.model.RateOrderModel;
import com.gifting.kefi.data.model.ReviewModel;

public class RateOrderViewModel extends ViewModel {
    private MutableLiveData<String> successText;
    private MutableLiveData<String> failText;
    private RateOrderRepo rateOrderRepo;

    public RateOrderViewModel() {
        rateOrderRepo = new RateOrderRepo();
        successText = rateOrderRepo.getSuccessText();
        failText = rateOrderRepo.getFailText();
    }

    public void addRateInDatabase(RateOrderModel rateOrderModel) {
        rateOrderRepo.storeRateInDatabase(rateOrderModel);
    }

    public MutableLiveData<String> getSuccessText() {
        return successText;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }
}