package com.gifting.kefi.ui.activities.kids;

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
import com.gifting.kefi.data.model.Article2Model;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivityKidsBinding;
import com.gifting.kefi.ui.activities.articles_details_.ArticleDetails2Activity;
import com.gifting.kefi.ui.activities.face.FaceViewModel;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.adapters.KidsProductAdapter;
import com.gifting.kefi.ui.adapters.KidsVideosAdapter;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.Language;

public class KidsActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityKidsBinding binding;
    KidsVideosAdapter adapterKids1;
    KidsProductAdapter adapterKids2;
    Article2Model articles2Model;
    private FaceViewModel mViewModel;
    boolean skin = false, hair = false, diaper = false;
    private UserSharedPreference userSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_kids);
        mViewModel = new ViewModelProvider(this).get(FaceViewModel.class);

        Language.changeBackDependsLanguage(binding.backImage, getApplicationContext());


        userSharedPreference = new UserSharedPreference(getApplicationContext());


        binding.articleKidsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        adapterKids1 = new KidsVideosAdapter(KidsActivity.this);
        binding.articleKidsRecyclerView.setAdapter(adapterKids1);


        binding.productsRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        adapterKids2 = new KidsProductAdapter(this);
        binding.productsRecycler.setAdapter(adapterKids2);

        binding.linearLayoutSkinCare.setVisibility(View.GONE);
        binding.linearLayoutHair.setVisibility(View.GONE);
        binding.linearLayoutDiaper.setVisibility(View.GONE);
        binding.cardDescriptionSkin.setOnClickListener(this);
        binding.cardDescriptionHair.setOnClickListener(this);
        binding.cardDescriptionDiaper.setOnClickListener(this);
        binding.cardTipsSkin.setOnClickListener(this);
        binding.cardTipsHair.setOnClickListener(this);
        binding.cardTipsDiaper.setOnClickListener(this);
        binding.cardNaturalSkin.setOnClickListener(this);
        binding.cardNaturalHair.setOnClickListener(this);
        binding.cardNaturalDiaper.setOnClickListener(this);


        mViewModel.getFaceVideos("kids", userSharedPreference.getLanguage());
        mViewModel.getFaceRepo().observe(this, videoModels -> {
            adapterKids1.setVideoModels(videoModels);
            binding.loadProgress.setVisibility(View.GONE);
        });

        mViewModel.getFaceProducts("kids", userSharedPreference.getLanguage());
        mViewModel.getFaceRepo1().observe(this, products -> {
            adapterKids2.setProductModels(products);
            binding.loadProgress1.setVisibility(View.GONE);
        });

        mViewModel.getFailText().observe(this, s -> {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        });


        binding.skinLayout.setOnClickListener(v -> {
            if (!skin) {
                binding.linearLayoutSkinCare.setVisibility(View.VISIBLE);
                binding.dropDown1.setImageResource(R.drawable.ic_top_arrow);

                skin = true;

            } else {
                binding.linearLayoutSkinCare.setVisibility(View.GONE);
                binding.dropDown1.setImageResource(R.drawable.ic_bottom_arrow);

                skin = false;
            }
        });
        binding.HairLayout.setOnClickListener(v -> {
            if (!hair) {

                binding.linearLayoutHair.setVisibility(View.VISIBLE);
                binding.dropDown2.setImageResource(R.drawable.ic_top_arrow);
                hair = true;

            } else {
                binding.linearLayoutHair.setVisibility(View.GONE);
                hair = false;
                binding.dropDown2.setImageResource(R.drawable.ic_bottom_arrow);
            }
        });
        binding.DiaperLayout.setOnClickListener(v -> {
            if (!diaper) {
                binding.linearLayoutDiaper.setVisibility(View.VISIBLE);
                binding.dropDown3.setImageResource(R.drawable.ic_top_arrow);
                diaper = true;
            } else {
                binding.linearLayoutDiaper.setVisibility(View.GONE);
                binding.dropDown3.setImageResource(R.drawable.ic_bottom_arrow);
                diaper = false;
            }

        });

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
            startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
        } else if (v == binding.back) {
            onBackPressed();
        } else if (v == binding.cardDescriptionSkin) {
            Intent intent = new Intent(this, ArticleDetails2Activity.class);
            articles2Model = new Article2Model("61", R.drawable.skin_care_lowquality, getString(R.string.skin_care), getString(R.string.description), getString(R.string.detailsSkinCare), "description");
            intent.putExtra("Article", articles2Model);
            startActivity(new Intent(intent));
        } else if (v == binding.cardTipsSkin) {
            Intent intent = new Intent(this, ArticleDetails2Activity.class);
            articles2Model = new Article2Model("62", R.drawable.tips_lowquality, getString(R.string.skin_care), getString(R.string.tips), getString(R.string.tipsSkinCare), "tips");
            intent.putExtra("Article", articles2Model);
            startActivity(new Intent(intent));
        } else if (v == binding.cardNaturalSkin) {
            Intent intent = new Intent(this, ArticleDetails2Activity.class);
            articles2Model = new Article2Model("63", R.drawable.kids_natural, getString(R.string.skin_care), getString(R.string.natural_recipes2), getString(R.string.natural_recipes_skin_care), "natural");
            intent.putExtra("Article", articles2Model);
            startActivity(new Intent(intent));
        } else if (v == binding.cardDescriptionHair) {
            Intent intent = new Intent(this, ArticleDetails2Activity.class);
            articles2Model = new Article2Model("64", R.drawable.hair_baby54, getString(R.string.hair_care), getString(R.string.description), getString(R.string.detailsHairCare), "description");
            intent.putExtra("Article", articles2Model);
            startActivity(new Intent(intent));
        } else if (v == binding.cardTipsHair) {
            Intent intent = new Intent(this, ArticleDetails2Activity.class);
            articles2Model = new Article2Model("65", R.drawable.tips_lowquality, getString(R.string.hair_care), getString(R.string.tips), getString(R.string.tipsHairCare), "tips");
            intent.putExtra("Article", articles2Model);
            startActivity(new Intent(intent));
        } else if (v == binding.cardNaturalHair) {
            Intent intent = new Intent(this, ArticleDetails2Activity.class);
            articles2Model = new Article2Model("66", R.drawable.kids_natural, getString(R.string.hair_care), getString(R.string.natural_recipes2), getString(R.string.natural_recipes_hair_care), "natural");
            intent.putExtra("Article", articles2Model);
            startActivity(new Intent(intent));
        } else if (v == binding.cardDescriptionDiaper) {
            Intent intent = new Intent(this, ArticleDetails2Activity.class);
            articles2Model = new Article2Model("67", R.drawable.diaper_lowquality, getString(R.string.diaper_area), getString(R.string.description), getString(R.string.detailsDiaperArea), "description");
            intent.putExtra("Article", articles2Model);
            startActivity(new Intent(intent));
        } else if (v == binding.cardTipsDiaper) {
            Intent intent = new Intent(this, ArticleDetails2Activity.class);
            articles2Model = new Article2Model("68", R.drawable.tips_lowquality, getString(R.string.diaper_area), getString(R.string.tips), getString(R.string.tipsDiaperArea), "tips");
            intent.putExtra("Article", articles2Model);
            startActivity(new Intent(intent));
        } else if (v == binding.cardNaturalDiaper) {
            Intent intent = new Intent(this, ArticleDetails2Activity.class);
            articles2Model = new Article2Model("69", R.drawable.kids_natural, getString(R.string.diaper_area), getString(R.string.natural_recipes2), getString(R.string.natural_recipes_dipper_area), "natural");
            intent.putExtra("Article", articles2Model);
            startActivity(new Intent(intent));
        }


    }


}
