package com.gifting.kefi.ui.activities.notification_settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.gifting.kefi.R;
import com.gifting.kefi.databinding.ActivityNotificationSettingsBinding;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;

public class NotificationSettingsActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityNotificationSettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification_settings);
        binding.option.setOnClickListener(this);
        binding.back.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.storeSwitch.setOnClickListener(this);
        binding.articleSwitch.setOnClickListener(this);
        binding.reelSwitch.setOnClickListener(this);
        binding.faceSwitch.setOnClickListener(this);
        binding.hairSwitch.setOnClickListener(this);
        binding.bodySwitch.setOnClickListener(this);

        binding.storeSwitch.setTag("on");
        binding.articleSwitch.setTag("on");
        binding.reelSwitch.setTag("on");
        binding.faceSwitch.setTag("on");
        binding.hairSwitch.setTag("on");
        binding.bodySwitch.setTag("on");



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
        } else if (v == binding.storeSwitch) {
            CheckSwitch(binding.storeSwitch);
        } else if (v == binding.articleSwitch) {
            CheckSwitch(binding.articleSwitch);
        } else if (v == binding.reelSwitch) {
            CheckSwitch(binding.reelSwitch);
        } else if (v == binding.faceSwitch) {
            CheckSwitch(binding.faceSwitch);
        } else if (v == binding.hairSwitch) {
            CheckSwitch(binding.hairSwitch);
        } else if (v == binding.bodySwitch) {
            CheckSwitch(binding.bodySwitch);
        }

    }

    private void CheckSwitch(ImageView imageView) {
        if (imageView.getTag() == "on") {
            imageView.setImageResource(R.drawable.ic_switch_off);
            imageView.setTag("off");

        } else if (imageView.getTag() == "off") {
            imageView.setImageResource(R.drawable.ic_switch_on);
            imageView.setTag("on");


        }
    }
}