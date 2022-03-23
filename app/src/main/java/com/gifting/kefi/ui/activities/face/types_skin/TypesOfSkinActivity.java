package com.gifting.kefi.ui.activities.face.types_skin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.Article2Model;
import com.gifting.kefi.data.model.VideoModel;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivityTypesOfSkinBinding;
import com.gifting.kefi.ui.activities.articles_details_.ArticleDetails2Activity;
import com.gifting.kefi.ui.activities.face.FaceViewModel;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.adapters.SkinTypesVideoAdapter;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.Language;

import java.util.ArrayList;

public class TypesOfSkinActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityTypesOfSkinBinding binding;
    private boolean dryVisibility = false, oilyVisibility = false, mixedVisibility = false, normalVisibility = false;

    private FaceViewModel mViewModel;
    private Article2Model article2Model;
    private Intent intent;
    private UserSharedPreference userSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_types_of_skin);
        mViewModel = new ViewModelProvider(this).get(FaceViewModel.class);

        Language.changeBackDependsLanguage(binding.backImage,this);

        userSharedPreference= new UserSharedPreference(this);


        intent = new Intent(TypesOfSkinActivity.this, ArticleDetails2Activity.class);
        binding.videosRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        SkinTypesVideoAdapter adapter = new SkinTypesVideoAdapter(this);
        binding.videosRecycler.setAdapter(adapter);

        intent = new Intent(TypesOfSkinActivity.this, ArticleDetails2Activity.class);


       /* binding.productsRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        SkinTypesProductAdapter adapter1 = new SkinTypesProductAdapter(this);
        binding.productsRecycler.setAdapter(adapter1);*/

        binding.dryItem.setOnClickListener(this);
        binding.oilyItem.setOnClickListener(this);
        binding.mixedItem.setOnClickListener(this);
        binding.normalItem.setOnClickListener(this);
        binding.cardView1177.setOnClickListener(this);
        binding.cardView11775.setOnClickListener(this);
        binding.cardView105.setOnClickListener(this);
        binding.cardView125.setOnClickListener(this);
        binding.cardView11776.setOnClickListener(this);
        binding.cardView106.setOnClickListener(this);
        binding.cardView126.setOnClickListener(this);
        binding.cardView10.setOnClickListener(this);
        binding.cardView12.setOnClickListener(this);
        binding.cardView11779.setOnClickListener(this);
        binding.cardView109.setOnClickListener(this);
        binding.cardView129.setOnClickListener(this);

        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);


        mViewModel.getFaceVideos("face",userSharedPreference.getLanguage());
        mViewModel.getFaceRepo().observe(this, videoModels -> {
            ArrayList<VideoModel> skinList = new ArrayList<>();
            skinList.add(videoModels.get(6));
            skinList.add(videoModels.get(4));
            skinList.add(videoModels.get(5));
            adapter.setVideoModels(skinList);
            binding.loadProgress.setVisibility(View.GONE);
        });

    }

    @Override
    public void onClick(View v) {
        if (v == binding.dryItem) {
            binding.oilyItem.setForeground(null);
            collapse(binding.oilyContainer);
            binding.normalItem.setForeground(null);
            collapse(binding.normalContainer);
            binding.mixedItem.setForeground(null);
            collapse(binding.mixedContainer);
            oilyVisibility = false;
            mixedVisibility = false;
            normalVisibility = false;
            if (dryVisibility) {
                binding.dryItem.setForeground(null);
                collapse(binding.dryContainer);
                dryVisibility = false;
            } else {
                binding.dryItem.setForeground(getResources().getDrawable(R.drawable.stroke_button_bink_r8));
                expand(binding.dryContainer);
                dryVisibility = true;
            }
        } else if (v == binding.oilyItem) {
            binding.dryItem.setForeground(null);
            collapse(binding.dryContainer);
            binding.normalItem.setForeground(null);
            collapse(binding.normalContainer);
            binding.mixedItem.setForeground(null);
            collapse(binding.mixedContainer);
            dryVisibility = false;
            mixedVisibility = false;
            normalVisibility = false;
            if (oilyVisibility) {
                binding.oilyItem.setForeground(null);
                collapse(binding.oilyContainer);
                oilyVisibility = false;
            } else {
                binding.oilyItem.setForeground(getResources().getDrawable(R.drawable.stroke_button_bink_r8));
                expand(binding.oilyContainer);
                oilyVisibility = true;
            }
        } else if (v == binding.mixedItem) {
            binding.dryItem.setForeground(null);
            collapse(binding.dryContainer);
            binding.normalItem.setForeground(null);
            collapse(binding.normalContainer);
            binding.oilyItem.setForeground(null);
            collapse(binding.oilyContainer);
            oilyVisibility = false;
            dryVisibility = false;
            normalVisibility = false;
            if (mixedVisibility) {
                binding.mixedItem.setForeground(null);
                collapse(binding.mixedContainer);
                mixedVisibility = false;
            } else {
                binding.mixedItem.setForeground(getResources().getDrawable(R.drawable.stroke_button_bink_r8));
                expand(binding.mixedContainer);
                mixedVisibility = true;
            }
        } else if (v == binding.normalItem) {
            binding.dryItem.setForeground(null);
            collapse(binding.dryContainer);
            binding.oilyItem.setForeground(null);
            collapse(binding.oilyContainer);
            binding.mixedItem.setForeground(null);
            collapse(binding.mixedContainer);
            dryVisibility = false;
            mixedVisibility = false;
            oilyVisibility = false;
            if (normalVisibility) {
                binding.normalItem.setForeground(null);
                collapse(binding.normalContainer);
                normalVisibility = false;
            } else {
                binding.normalItem.setForeground(getResources().getDrawable(R.drawable.stroke_button_bink_r8));
                expand(binding.normalContainer);
                normalVisibility = true;
            }

        } else if (v == binding.cardView1177) {
            article2Model = new Article2Model("37", R.drawable.dry_skin, getString(R.string.dry_skin), getString(R.string.description), getString(R.string.detailsDrySkin), "description");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView11775) {
            article2Model = new Article2Model("38", R.drawable.oily_skin1, getString(R.string.oily_skin), getString(R.string.description), getString(R.string.detailsOilySkin),"description");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView105) {
            article2Model = new Article2Model("39", R.drawable.tips_face, getString(R.string.oily_skin), getString(R.string.tips), getString(R.string.tipsOilySkin), "tips");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView125) {
            article2Model = new Article2Model("40", R.drawable.natural9, getString(R.string.oily_skin), getString(R.string.natural_recipes2), getString(R.string.natural_recipes_oily_skin),  "natural");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView11776) {
            article2Model = new Article2Model("41", R.drawable.tzone, getString(R.string.mixed_skin), getString(R.string.description), getString(R.string.detailsMixedSkin),"description");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView106) {
            article2Model = new Article2Model("42", R.drawable.tips_face, getString(R.string.mixed_skin), getString(R.string.tips), getString(R.string.tipsMixedSkin), "tips");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView126) {
            article2Model = new Article2Model("43", R.drawable.natural9, getString(R.string.mixed_skin), getString(R.string.natural_recipes2), getString(R.string.natural_recipes_mixed_skin), "natural");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView10) {
            article2Model = new Article2Model("44", R.drawable.tips_face, getString(R.string.dry_skin), getString(R.string.tips), getString(R.string.tipsDrySkin), "tips");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView12) {
            article2Model = new Article2Model("45", R.drawable.natural9, getString(R.string.dry_skin), getString(R.string.natural_recipes2), getString(R.string.natural_recipes_dry_skin), "natural");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView11779) {
            article2Model = new Article2Model("46", R.drawable.normal_skin1, getString(R.string.normal_skin), getString(R.string.description), getString(R.string.detailsNormalSkin), "description");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView109) {
            article2Model = new Article2Model("47", R.drawable.tips_face, getString(R.string.normal_skin), getString(R.string.tips), getString(R.string.tipsNormalSkin), "tips");
            intent.putExtra("Article", article2Model);
            startActivity(intent);
        } else if (v == binding.cardView129) {
            article2Model = new Article2Model("48", R.drawable.natural9, getString(R.string.normal_skin), getString(R.string.natural_recipes2), getString(R.string.natural_recipes_normal_skin),"natural");
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


    public static void expand(final View v) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ConstraintLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}