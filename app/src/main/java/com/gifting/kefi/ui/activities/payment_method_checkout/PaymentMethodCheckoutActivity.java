package com.gifting.kefi.ui.activities.payment_method_checkout;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.gifting.kefi.R;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivityPaymentMethodCheckoutBinding;
import com.gifting.kefi.util.Language;

public class PaymentMethodCheckoutActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityPaymentMethodCheckoutBinding binding;
    private UserSharedPreference userSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_method_checkout);

        userSharedPreference = new UserSharedPreference(getApplicationContext());

        binding.back.setOnClickListener(this);
        binding.cashOnDeliveryLayout.setOnClickListener(this);
        binding.linearLayout2.setOnClickListener(this);

        Language.changeBackDependsLanguage(binding.backImage,this);

    }

    @Override
    public void onClick(View v) {
        if (v == binding.cashOnDeliveryLayout) {
            userSharedPreference.setSelectedPaymentMethod("Cash on delivery");
            onBackPressed();
        } else if (v == binding.back) {
            onBackPressed();
        }
        if(v==binding.linearLayout2){
            Toast.makeText(getApplicationContext(), getString(R.string.not_available_now), Toast.LENGTH_SHORT).show();
        }
        if(v==binding.cashOnDeliveryLayout){
            Toast.makeText(getApplicationContext(), getString(R.string.cash_on_delivery_selected_successful), Toast.LENGTH_SHORT).show();
        }
    }
}