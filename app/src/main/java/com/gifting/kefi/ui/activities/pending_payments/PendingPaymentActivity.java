package com.gifting.kefi.ui.activities.pending_payments;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.gifting.kefi.R;
import com.gifting.kefi.databinding.ActivityPendingPaymentBinding;
import com.gifting.kefi.util.Language;

public class PendingPaymentActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityPendingPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pending_payment);

        Language.changeBackDependsLanguage(binding.backImage,this);
        Language.changeBackDependsLanguage(binding.imageView21,this);

        binding.back.setOnClickListener(this);
        binding.relativeLayout7.setOnClickListener(this);
        binding.linearLayout55.setOnClickListener(this);
        binding.imageView21.setOnClickListener(this);
        binding.linearLayout555.setOnClickListener(this);
        binding.linearLayout4.setOnClickListener(this);
        binding.linearLayout2.setOnClickListener(this);
        binding.constraintLayout.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v == binding.back){
            onBackPressed();
        }
        if(v==binding.linearLayout2){
            Toast.makeText(getApplicationContext(), getString(R.string.not_available_now), Toast.LENGTH_SHORT).show();
        }
        if(v==binding.imageView21){
            Toast.makeText(getApplicationContext(), getString(R.string.not_available_now), Toast.LENGTH_SHORT).show();
        }
        if(v==binding.constraintLayout){
            Toast.makeText(getApplicationContext(), getString(R.string.not_available_now), Toast.LENGTH_SHORT).show();
        }

    }
}