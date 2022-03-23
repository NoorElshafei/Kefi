package com.gifting.kefi.ui.activities.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.gifting.kefi.R;
import com.gifting.kefi.databinding.ActivitySettingsCartsBinding;
import com.gifting.kefi.ui.activities.my_carts.MyCartsActivity;
import com.gifting.kefi.ui.activities.my_order.MyOrderActivity;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.activities.payment_method.PaymentMethodActivity;
import com.gifting.kefi.ui.activities.pending_payments.PendingPaymentActivity;
import com.gifting.kefi.ui.activities.shipping_address_settings.ShippingAddressActivity;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.Language;

public class SettingsCartsActivity extends AppCompatActivity implements View.OnClickListener {
    ActivitySettingsCartsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings_carts);

        Language.changeBackDependsLanguage(binding.editProfileImage2,this);
        Language.changeBackDependsLanguage(binding.logOutImage2,this);
        //Language.changeBackDependsLanguage(binding.notificationImage2,this);
        Language.changeBackDependsLanguage(binding.customerImage2,this);
        //Language.changeBackDependsLanguage(binding.inviteImage2,this);
        Language.changeBackDependsLanguage(binding.backImage,this);


        binding.myOrderLayout.setOnClickListener(this);
        binding.pendingOrderLayout.setOnClickListener(this);
        //binding.pendingPaymentsLayout1.setOnClickListener(this);

        binding.shippingAddressLayout.setOnClickListener(this);
        //binding.paymentMethodLayout.setOnClickListener(this);
        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == binding.myOrderLayout) {
            startActivity(new Intent(SettingsCartsActivity.this, MyCartsActivity.class));
        } else if (v == binding.pendingOrderLayout) {
            startActivity(new Intent(SettingsCartsActivity.this, MyOrderActivity.class));
        }/* else if (v == binding.pendingPaymentsLayout1) {
            startActivity(new Intent(SettingsCartsActivity.this, PendingPaymentActivity.class));
        }*/ else if (v == binding.shippingAddressLayout) {
            startActivity(new Intent(SettingsCartsActivity.this, ShippingAddressActivity.class));
        } /*else if (v == binding.paymentMethodLayout) {
            startActivity(new Intent(SettingsCartsActivity.this, PaymentMethodActivity.class));
        }*/ else if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.notificationLayout) {
            startActivity(new Intent(this, NotificationsActivity.class));
        } else if (v == binding.back) {
            onBackPressed();
        }

    }
}