package com.gifting.kefi.ui.activities.rate_us;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.gifting.kefi.R;
import com.gifting.kefi.databinding.ActivityRateUsBinding;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.Language;

public class RateUsActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityRateUsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rate_us);

        Language.changeBackDependsLanguage(binding.backImage,getApplicationContext());
/*
        binding.simpleRatingBar.setOnRatingChangeListener((ratingBar, rating, fromUser) -> {
            binding.starNumber.setText(rating + "");
            Intent intent = new Intent(getApplicationContext(), RateUsMessageActivity.class);
            intent.putExtra("rateValue", rating);
            startActivity(intent);
        });*/

        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);
        binding.imageView4.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.notificationLayout) {
            startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
        } /*else if (v == binding.imageView4) {
            UserDetailsFragment userDetailsFragment = UserDetailsFragment.newInstance();
            userDetailsFragment.show(getSupportFragmentManager(),"dssd");
        }*/ else if (v == binding.back) {
            onBackPressed();
        }
    }
}