package com.gifting.kefi.ui.activities.fitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivityFitnessBinding;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.activities.training.TrainingActivity;
import com.gifting.kefi.ui.activities.trending_food.TrendingFoodActivity;
import com.gifting.kefi.ui.adapters.TrainingAdapter;
import com.gifting.kefi.ui.adapters.TrendingFoodAdapter;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.ui.fragments.profile.ProfileFragment;

public class FitnessActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityFitnessBinding binding;
    TrendingFoodAdapter adapter1;
    TrainingAdapter adapter2;
    private UserSharedPreference userSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fitness);


        binding.trendingFoodRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapter1 = new TrendingFoodAdapter(this);
        binding.trendingFoodRecyclerView.setAdapter(adapter1);

        userSharedPreference = new UserSharedPreference(this);


        binding.trainingRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapter2 = new TrainingAdapter(this);
        binding.trainingRecyclerView.setAdapter(adapter2);


        binding.seeAllTrendingFood.setOnClickListener(this);
        binding.seeAllTraining.setOnClickListener(this);
        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.imageProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.seeAllTrendingFood) {
            startActivity(new Intent(FitnessActivity.this, TrendingFoodActivity.class));
        } else if (v == binding.seeAllTraining) {
            startActivity(new Intent(FitnessActivity.this, TrainingActivity.class));
        } else if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.notificationLayout) {
            startActivity(new Intent(this, NotificationsActivity.class));
        } /*else if (v == binding.imageProfile) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ProfileFragment.newInstance(), "profile").commit();
        }*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        String[] myName = userSharedPreference.getUserDetails().getName().split(" ");
        binding.title.setText("Hi " + myName[0].toUpperCase().charAt(0) + myName[0].substring(1).toLowerCase() + " ,");
    }
}