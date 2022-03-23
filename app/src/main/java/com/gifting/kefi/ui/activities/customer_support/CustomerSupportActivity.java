package com.gifting.kefi.ui.activities.customer_support;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.gifting.kefi.R;
import com.gifting.kefi.databinding.ActivityCustomerSupportBinding;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.Language;

public class CustomerSupportActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityCustomerSupportBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_support);

        Language.changeBackDependsLanguage(binding.backImage,getApplicationContext());


        binding.textLayout.setOnClickListener(this);
        binding.callButton.setOnClickListener(this);
        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.notificationLayout) {
            startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
        } else if (v == binding.back) {
            onBackPressed();
        } else if (v == binding.textLayout) {
            startActivity(new Intent(getApplicationContext(), CustomerSupportMessageActivity.class));
        } else if (v == binding.callButton) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "01027668669", null));
            startActivity(intent);
        }
    }
}