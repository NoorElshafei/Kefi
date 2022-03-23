package com.gifting.kefi.ui.activities.videos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivityVideosBinding;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.adapters.VideosAdapter;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.Language;
import com.bumptech.glide.Glide;

public class VideosActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityVideosBinding binding;
    private VideosAdapter adapter1;
    private VideosViewModel mViewModel;
    private UserSharedPreference userSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_videos);
        mViewModel = new ViewModelProvider(this).get(VideosViewModel.class);

        Language.changeBackDependsLanguage(binding.backImage,this);


        userSharedPreference= new UserSharedPreference(getApplicationContext());

        Glide.with(getApplicationContext()).load(R.drawable.loading).into(binding.loadProgress);



        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);


        binding.videosRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        adapter1 = new VideosAdapter(this);
        binding.videosRecyclerView.setAdapter(adapter1);

        mViewModel.getAllVideos(userSharedPreference.getLanguage());
        mViewModel.getVideoLiveData().observe(this, videoModels -> {
            binding.loadProgress.setVisibility(View.GONE);
            binding.loadingText.setVisibility(View.GONE);
            adapter1.setVideosData(videoModels);
        });
        mViewModel.getFailText().observe(this, failText -> {
            Toast.makeText(getApplicationContext(), failText, Toast.LENGTH_SHORT).show();

        });


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
        }
    }
}