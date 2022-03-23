package com.gifting.kefi.ui.activities.customer_support;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.gifting.kefi.R;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivityCustomerSupportMessageBinding;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.DialogCustom;
import com.gifting.kefi.util.Language;

public class CustomerSupportMessageActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityCustomerSupportMessageBinding binding;
    private CustomerSupportViewModel customerSupportViewModel;
    private DialogCustom dialogCustom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_support_message);
        customerSupportViewModel = new ViewModelProvider(this).get(CustomerSupportViewModel.class);

        Language.changeBackDependsLanguage(binding.backImage,this);

        binding.sendMessageButton.setOnClickListener(this);
        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        dialogCustom = new DialogCustom(this);
        if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.notificationLayout) {
            startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
        } else if (v == binding.back) {
            onBackPressed();

        } else if (v == binding.sendMessageButton) {

            if (!binding.messageEditText.getText().toString().equals("")) {
                saveRate();
            }
            /*amany*/
            else {
                Toast.makeText(this, getString(R.string.please_fill_all_information), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void saveRate() {
        UserSharedPreference userSharedPreference=new UserSharedPreference(this);
        customerSupportViewModel.setRateApp(userSharedPreference.getUserDetails().getId(),userSharedPreference.getUserDetails().getName(),userSharedPreference.getUserDetails().getPhoneNumber(), binding.messageEditText.getText().toString());
        customerSupportViewModel.getSuccessText().observe(this, s -> {
            dialogCustom.dismissDialog();
            Toast.makeText(this, getString(R.string.thank_you_your_message_sent_successfully), Toast.LENGTH_SHORT).show();
            onBackPressed();
        });

        customerSupportViewModel.getFailText().observe(this, s -> {
            dialogCustom.dismissDialog();
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        });
    }
}