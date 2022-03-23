package com.gifting.kefi.ui.activities.payment_method;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.gifting.kefi.R;
import com.gifting.kefi.databinding.ActivityPaymentMethodBinding;
import com.gifting.kefi.util.Language;

public class PaymentMethodActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityPaymentMethodBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_method);
        binding.back.setOnClickListener(this);
        binding.linearLayout55.setOnClickListener(this);
        binding.linearLayout4.setOnClickListener(this);
        binding.linearLayout2.setOnClickListener(this);

        Language.changeBackDependsLanguage(binding.backImage,getApplicationContext());

    }

    @Override
    public void onClick(View v) {
        if(v == binding.back){
            onBackPressed();
        }
        if(v==binding.linearLayout2){
            Toast.makeText(getApplicationContext(), getString(R.string.not_available_now), Toast.LENGTH_SHORT).show();
        }
    }
}