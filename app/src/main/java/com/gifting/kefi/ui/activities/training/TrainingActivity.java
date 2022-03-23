package com.gifting.kefi.ui.activities.training;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.gifting.kefi.R;
import com.gifting.kefi.databinding.ActivityTrainingBinding;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.adapters.AllTrainingAdapter;
import com.gifting.kefi.ui.adapters.AllTrendingFoodAdapter;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

public class TrainingActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityTrainingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_training);


        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        binding.trainingRecyclerView.setLayoutManager(layoutManager);
        AllTrainingAdapter adapter = new AllTrainingAdapter(this);
        binding.trainingRecyclerView.setAdapter(adapter);


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
            startActivity(new Intent(this, NotificationsActivity.class));
        } else if (v == binding.back) {
            onBackPressed();
        }
    }
}