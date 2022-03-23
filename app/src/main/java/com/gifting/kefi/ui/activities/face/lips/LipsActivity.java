package com.gifting.kefi.ui.activities.face.lips;

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
import com.gifting.kefi.databinding.ActivityLipsBinding;
import com.gifting.kefi.ui.activities.articles_details_.ArticleDetails2Activity;
import com.gifting.kefi.ui.activities.face.FaceViewModel;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.adapters.LipsVideoAdapter;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.Language;

import java.util.ArrayList;

public class LipsActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityLipsBinding binding;
    private boolean chappedVisibility = false, crackedVisibility = false, suddenVisibility = false, sunBurnedVisibility = false;
    private Intent intent;
    private Article2Model article2Model;
    private FaceViewModel mViewModel;
    private UserSharedPreference userSharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lips);
        mViewModel = new ViewModelProvider(this).get(FaceViewModel.class);

        Language.changeBackDependsLanguage(binding.backImage,this);


        userSharedPreference = new UserSharedPreference(this);


        binding.videosRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        LipsVideoAdapter adapter = new LipsVideoAdapter(this);
        binding.videosRecycler.setAdapter(adapter);

       /* binding.productsRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        LipsProductAdapter adapter1 = new LipsProductAdapter(this);
        binding.productsRecycler.setAdapter(adapter1);*/

        intent = new Intent(LipsActivity.this, ArticleDetails2Activity.class);


        new ArticlesModel("20", R.drawable.sunburned_lips,getString(R.string.sunburned_lips) , getString(R.string.detailsSunburnedLips),  getString(R.string.tips_to_overcome_it), getString(R.string.tipsSunburnedLips), getString(R.string.description),"");


        binding.chappedItem.setOnClickListener(this);
        binding.crackedItem.setOnClickListener(this);
        binding.suddenItem.setOnClickListener(this);
        binding.sunburnedItem.setOnClickListener(this);
        binding.cardView102.setOnClickListener(this);
        binding.cardView112.setOnClickListener(this);
        binding.cardView122.setOnClickListener(this);
        binding.cardView101.setOnClickListener(this);
        binding.cardView121.setOnClickListener(this);
        binding.cardView111.setOnClickListener(this);
        binding.cardView11.setOnClickListener(this);
        binding.cardView10.setOnClickListener(this);
        binding.cardView12.setOnClickListener(this);
        binding.cardView1027.setOnClickListener(this);
        binding.cardView1227.setOnClickListener(this);
        binding.cardView1127.setOnClickListener(this);

        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);


        mViewModel.getFaceVideos("face",userSharedPreference.getLanguage());
        mViewModel.getFaceRepo().observe(this, videoModels -> {
            ArrayList<VideoModel> lipsList = new ArrayList<>();
            lipsList.add(videoModels.get(0));
            lipsList.add(videoModels.get(1));
            lipsList.add(videoModels.get(2));
            lipsList.add(videoModels.get(8));
            adapter.setVideoModels(lipsList);
            binding.loadProgress.setVisibility(View.GONE);
        });
    }

    @Override
    public void onClick(View v) {
        if (v == binding.chappedItem) {
            if (chappedVisibility) {
                binding.chappedContainer.setVisibility(View.GONE);
                binding.imageView455.setImageResource(R.drawable.ic_bottom_arrow);
                chappedVisibility = false;
            } else {
                binding.chappedContainer.setVisibility(View.VISIBLE);
                binding.imageView455.setImageResource(R.drawable.ic_top_arrow);
                chappedVisibility = true;
            }
        } else if (v == binding.crackedItem) {
            if (crackedVisibility) {
                binding.crackedContainer.setVisibility(View.GONE);
                binding.imageView456.setImageResource(R.drawable.ic_bottom_arrow);
                crackedVisibility = false;
            } else {
                binding.crackedContainer.setVisibility(View.VISIBLE);
                binding.imageView456.setImageResource(R.drawable.ic_top_arrow);
                crackedVisibility = true;
            }
        } else if (v == binding.suddenItem) {
            if (suddenVisibility) {
                binding.suddenContainer.setVisibility(View.GONE);
                binding.imageView45.setImageResource(R.drawable.ic_bottom_arrow);
                suddenVisibility = false;
            } else {
                binding.suddenContainer.setVisibility(View.VISIBLE);
                binding.imageView45.setImageResource(R.drawable.ic_top_arrow);
                suddenVisibility = true;
            }
        } else if (v == binding.sunburnedItem) {
            if (sunBurnedVisibility) {
                binding.sunburnedContainer.setVisibility(View.GONE);
                binding.imageView458.setImageResource(R.drawable.ic_bottom_arrow);
                sunBurnedVisibility = false;
            } else {
                binding.sunburnedContainer.setVisibility(View.VISIBLE);
                binding.imageView458.setImageResource(R.drawable.ic_top_arrow);
                sunBurnedVisibility = true;
            }

        }else if (v == binding.cardView102) {
            article2Model = new Article2Model("25", R.drawable.tips_face, getString(R.string.sudden_swollen_lips), getString(R.string.tips_to_overcome_it), getString(R.string.tipsSuddenSwollenLips),"tips");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView112) {
            article2Model = new Article2Model("26", R.drawable.sudden_swollen_lips_lowquality2, getString(R.string.sudden_swollen_lips), getString(R.string.description), getString(R.string.detailsSuddenSwollenLips),"description");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView122) {
            article2Model = new Article2Model("27", R.drawable.eyes_natural, getString(R.string.sudden_swollen_lips), getString(R.string.natural_recipes2), getString(R.string.natural_recipes_sudden_swollen_lips),"natural");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView101) {
            article2Model = new Article2Model("28", R.drawable.tips_face, getString(R.string.cracked_corner_lips),getString(R.string.tips_to_overcome_it), getString(R.string.tipsCrackedCornerLips),"tips");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView121) {
            article2Model = new Article2Model("29", R.drawable.eyes_natural,  getString(R.string.cracked_corner_lips),getString(R.string.natural_recipes2), getString(R.string.natural_recipes_cracked_corner_lips),"natural");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView111) {
            article2Model = new Article2Model("30", R.drawable.cracked_cornor_lowquality,  getString(R.string.cracked_corner_lips), getString(R.string.description), getString(R.string.detailsCrackedCornerLips),"description");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView11) {
            article2Model = new Article2Model("31", R.drawable.chapped_lips3, getString(R.string.chapped_lips), getString(R.string.description), getString(R.string.detailsChappedLips),"description");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView10) {
            article2Model = new Article2Model("32", R.drawable.tips_face, getString(R.string.chapped_lips), getString(R.string.tips_to_overcome_it), getString(R.string.tipsChappedLips),"tips");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView12) {
            article2Model = new Article2Model("33", R.drawable.eyes_natural, getString(R.string.chapped_lips),getString(R.string.natural_recipes2),getString(R.string.natural_recipes_chapped_lips),"natural");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView1027) {
            article2Model = new Article2Model("34", R.drawable.tips_face, getString(R.string.sunburned_lips), getString(R.string.tips_to_overcome_it), getString(R.string.tipsSunburnedLips),"tips");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView1227) {
            article2Model = new Article2Model("35", R.drawable.eyes_natural, getString(R.string.sunburned_lips),getString(R.string.natural_recipes2), getString(R.string.natural_recipes_sunburned_lips),"natural");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView1127) {
            article2Model = new Article2Model("36", R.drawable.sunburned_lips_lowquality, getString(R.string.sunburned_lips), getString(R.string.description), getString(R.string.detailsSunburnedLips),"description");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.notificationLayout) {
            startActivity(new Intent(this, NotificationsActivity.class));
        } else if (v == binding.back) {
            onBackPressed();
        }
    }
}