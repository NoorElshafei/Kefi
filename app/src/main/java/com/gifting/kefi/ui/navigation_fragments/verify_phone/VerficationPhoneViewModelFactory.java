package com.gifting.kefi.ui.navigation_fragments.verify_phone;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class VerficationPhoneViewModelFactory implements ViewModelProvider.Factory {
    Activity activity;

    public VerficationPhoneViewModelFactory(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new VerificationPhoneViewModel(activity);
    }
}
