package com.gifting.kefi.ui.activities.face.eyes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.Article2Model;
import com.gifting.kefi.data.model.ArticlesModel;
import com.gifting.kefi.data.model.VideoModel;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivityEyesBinding;
import com.gifting.kefi.ui.activities.articles_details_.ArticleDetails2Activity;
import com.gifting.kefi.ui.activities.face.FaceViewModel;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.adapters.EyesVideoAdapter;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.Language;

import java.util.ArrayList;

public class EyesActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityEyesBinding binding;

    private boolean blackHalosVisibility = false, fineLineVisibility = false, puffyEyesVisibility = false;
    private ArrayList<ArticlesModel> face;
    private Article2Model article2Model;
    private Intent intent;
    private FaceViewModel mViewModel;
    private UserSharedPreference userSharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_eyes);
        mViewModel = new ViewModelProvider(this).get(FaceViewModel.class);

        Language.changeBackDependsLanguage(binding.backImage,this);

        userSharedPreference= new UserSharedPreference(this);


        binding.videosRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        EyesVideoAdapter adapter = new EyesVideoAdapter(this);
        binding.videosRecycler.setAdapter(adapter);

     /*   binding.productsRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        EyesProductAdapter adapter1 = new EyesProductAdapter(this);
        binding.productsRecycler.setAdapter(adapter1);*/

        intent = new Intent(getApplicationContext(), ArticleDetails2Activity.class);

        binding.blackHalosLayout.setOnClickListener(this);
        binding.fineLinesLayout.setOnClickListener(this);
        binding.puffyLayout.setOnClickListener(this);
        binding.cardView102.setOnClickListener(this);
        binding.cardView112.setOnClickListener(this);
        binding.cardView122.setOnClickListener(this);
        binding.cardView101.setOnClickListener(this);
        binding.cardView121.setOnClickListener(this);
        binding.cardView111.setOnClickListener(this);
        binding.cardView11.setOnClickListener(this);
        binding.cardView10.setOnClickListener(this);
        binding.cardView12.setOnClickListener(this);

        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);


        mViewModel.getFaceVideos("face",userSharedPreference.getLanguage());
        mViewModel.getFaceRepo().observe(this, videoModels -> {
            ArrayList<VideoModel> eyeList = new ArrayList<>();
            eyeList.add(videoModels.get(3));
            eyeList.add(videoModels.get(7));
            eyeList.add(videoModels.get(9));
            adapter.setVideoModels(eyeList);
            binding.loadProgress.setVisibility(View.GONE);
        });

    }

    @Override
    public void onClick(View v) {
        if (v == binding.blackHalosLayout) {
            if (blackHalosVisibility) {
                binding.blackHalosContainer.setVisibility(View.GONE);
                binding.imageView455.setImageResource(R.drawable.ic_bottom_arrow);
                blackHalosVisibility = false;
            } else {
                binding.blackHalosContainer.setVisibility(View.VISIBLE);
                binding.imageView455.setImageResource(R.drawable.ic_top_arrow);
                blackHalosVisibility = true;
            }
        } else if (v == binding.fineLinesLayout) {
            if (fineLineVisibility) {
                binding.fineLines.setVisibility(View.GONE);
                binding.imageView456.setImageResource(R.drawable.ic_bottom_arrow);
                fineLineVisibility = false;
            } else {
                binding.fineLines.setVisibility(View.VISIBLE);
                binding.imageView456.setImageResource(R.drawable.ic_top_arrow);

                fineLineVisibility = true;
            }
        } else if (v == binding.puffyLayout) {
            if (puffyEyesVisibility) {
                binding.puffyEyes.setVisibility(View.GONE);
                binding.imageView45.setImageResource(R.drawable.ic_bottom_arrow);
                puffyEyesVisibility = false;
            } else {
                binding.puffyEyes.setVisibility(View.VISIBLE);
                binding.imageView45.setImageResource(R.drawable.ic_top_arrow);
                puffyEyesVisibility = true;
            }
        } else if (v == binding.cardView102) {
            article2Model = new Article2Model("16", R.drawable.tips_face, getString(R.string.puffy_eyes), getString(R.string.tips_to_overcome_it), getString(R.string.tipsPuffyEyes),"tips");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView112) {
            article2Model = new Article2Model("17", R.drawable.puffy_eye1,getString(R.string.puffy_eyes),  getString(R.string.description), getString(R.string.detailsPuffyEyes),"description");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView122) {
            article2Model = new Article2Model("18", R.drawable.eyes_natural2,getString(R.string.puffy_eyes),  getString(R.string.natural_recipes2), getString(R.string.natural_recipes_puffy_eyes),"natural");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView101) {
            article2Model = new Article2Model("19", R.drawable.tips_face,getString(R.string.fine_lines),  getString(R.string.tips_to_overcome_it), getString(R.string.tipsFineLine),"tips");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView121) {
            article2Model = new Article2Model("20", R.drawable.eyes_natural2, getString(R.string.fine_lines), getString(R.string.natural_recipes2), getString(R.string.natural_recipes_fine_lines),"natural");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView111) {
            article2Model = new Article2Model("21", R.drawable.fine_line2, getString(R.string.fine_lines),getString(R.string.description), getString(R.string.detailsFineLines),"description");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView11) {
            article2Model = new Article2Model("22", R.drawable.dark_circle2, getString(R.string.dark_circles), getString(R.string.description), getString(R.string.detailsDark),"description");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView10) {
            article2Model = new Article2Model("23", R.drawable.tips_face,  getString(R.string.dark_circles), getString(R.string.tips_to_overcome_it), getString(R.string.tipsDarkCircle),"tips");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView12) {
            article2Model = new Article2Model("24", R.drawable.eyes_natural2,  getString(R.string.dark_circles), getString(R.string.natural_recipes2), getString(R.string.natural_recipes_dark_circles),"natural");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
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