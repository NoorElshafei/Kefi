package com.gifting.kefi.ui.activities.change_phone_setting.verify_phone;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ChangeVerificationPhoneViewModelFactory implements ViewModelProvider.Factory {
    Activity activity;

    public ChangeVerificationPhoneViewModelFactory(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ChangeVerificationPhoneViewModel(activity);
    }
}
