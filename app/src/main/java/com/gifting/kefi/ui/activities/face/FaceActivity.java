package com.gifting.kefi.ui.activities.face;

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
import com.gifting.kefi.data.model.VideoModel;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivityFaceBinding;
import com.gifting.kefi.ui.activities.face.eyes.EyesActivity;
import com.gifting.kefi.ui.activities.face.lips.LipsActivity;
import com.gifting.kefi.ui.activities.face.types_skin.TypesOfSkinActivity;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.adapters.FaceProductAdapter;
import com.gifting.kefi.ui.adapters.FaceVideoAdapter;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.Language;

public class FaceActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityFaceBinding binding;
    private FaceViewModel mViewModel;
    private UserSharedPreference userSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_face);
        mViewModel = new ViewModelProvider(this).get(FaceViewModel.class);

        Language.changeBackDependsLanguage(binding.backImage,getApplicationContext());
        Language.changeBackDependsLanguage(binding.imageView455,getApplicationContext());
        Language.changeBackDependsLanguage(binding.imageView456,getApplicationContext());
        Language.changeBackDependsLanguage(binding.imageView45,getApplicationContext());

        userSharedPreference = new UserSharedPreference(getApplicationContext());



        binding.eyesLayout.setOnClickListener(this);
        binding.lipsLayout.setOnClickListener(this);
        binding.typesLayout.setOnClickListener(this);

        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);

        binding.videosRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        FaceVideoAdapter adapter = new FaceVideoAdapter(this);
        binding.videosRecycler.setAdapter(adapter);

        binding.productsRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        FaceProductAdapter adapter1 = new FaceProductAdapter(this);
        binding.productsRecycler.setAdapter(adapter1);


        mViewModel.getFaceVideos("face",userSharedPreference.getLanguage());
        mViewModel.getFaceRepo().observe(this, videoModels -> {
            adapter.setVideoModels(videoModels);
            binding.loadProgress.setVisibility(View.GONE);
        });

        mViewModel.getFaceProducts("face",userSharedPreference.getLanguage());
        mViewModel.getFaceRepo1().observe(this, products -> {
            adapter1.setVideoModels(products);
            binding.loadProgress1.setVisibility(View.GONE);
        });

        mViewModel.getFailText().observe(this, s -> {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onClick(View v) {
        if (v == binding.eyesLayout) {
            startActivity(new Intent(getApplicationContext(), EyesActivity.class));
        } else if (v == binding.lipsLayout) {
            startActivity(new Intent(getApplicationContext(), LipsActivity.class));
        } else if (v == binding.typesLayout) {
            startActivity(new Intent(getApplicationContext(), TypesOfSkinActivity.class));
        } else if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.notificationLayout) {
            startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
        } else if (v == binding.back) {
            onBackPressed();
        }
    }
}