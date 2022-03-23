package com.gifting.kefi.ui.activities.hair;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.Article2Model;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivityHairBinding;
import com.gifting.kefi.ui.activities.articles_details_.ArticleDetails2Activity;
import com.gifting.kefi.ui.activities.face.FaceViewModel;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.adapters.HairBestProductAdapter;
import com.gifting.kefi.ui.adapters.HairVideosAdapter;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.Language;


public class HairActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityHairBinding binding;
    HairVideosAdapter adapter1;
    HairBestProductAdapter adapter2;
    boolean checkHairLoss = false;
    boolean checkHairDamage = false;
    boolean checkDandruff = false;
    private Article2Model articles2Model;
    private FaceViewModel mViewModel;
    private UserSharedPreference userSharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hair);
        mViewModel = new ViewModelProvider(this).get(FaceViewModel.class);

        Language.changeBackDependsLanguage(binding.backImage,getApplicationContext());

        userSharedPreference = new UserSharedPreference(getApplicationContext());



        binding.videosRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        adapter1 = new HairVideosAdapter(this);
        binding.videosRecycler.setAdapter(adapter1);


        binding.productsRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        adapter2 = new HairBestProductAdapter(HairActivity.this);
        binding.productsRecycler.setAdapter(adapter2);

        binding.linearLayoutHairDandruff.setVisibility(View.GONE);
        binding.linearLayoutHairDamage.setVisibility(View.GONE);
        binding.linearLayoutHairLoss.setVisibility(View.GONE);

        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);
        binding.hairLossLayout.setOnClickListener(this);
        binding.hairDamageLayout.setOnClickListener(this);
        binding.dandruffLayout.setOnClickListener(this);
        binding.cardDescriptionLoss.setOnClickListener(this);
        binding.cardReasonLoss.setOnClickListener(this);
        binding.cardTipsLoss.setOnClickListener(this);
        binding.cardNaturalLoss.setOnClickListener(this);
        binding.cardDescriptionDamage.setOnClickListener(this);
        binding.cardReasonDamage.setOnClickListener(this);
        binding.cardTipsDamage.setOnClickListener(this);
        binding.cardNaturalDamage.setOnClickListener(this);
        binding.cardDescriptionDandruff.setOnClickListener(this);
        binding.cardReasonDandruff.setOnClickListener(this);
        binding.cardTipsDandruff.setOnClickListener(this);
        binding.cardNaturalDandruff.setOnClickListener(this);


        mViewModel.getFaceVideos("hair",userSharedPreference.getLanguage());
        mViewModel.getFaceRepo().observe(this, videoModels -> {
            adapter1.setVideoModels(videoModels);
            binding.loadProgress.setVisibility(View.GONE);
        });

        mViewModel.getFaceProducts("hair",userSharedPreference.getLanguage());
        mViewModel.getFaceRepo1().observe(this, products -> {
            adapter2.setProductModels(products);
            binding.loadProgress1.setVisibility(View.GONE);
        });

        mViewModel.getFailText().observe(this, s -> {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        });

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
        if (v == binding.hairLossLayout) {
            binding.hairDamageLayout.setForeground(null);
            collapse(binding.linearLayoutHairDamage);
            binding.dandruffLayout.setForeground(null);
            collapse(binding.linearLayoutHairDandruff);
            checkHairDamage = false;
            checkDandruff = false;
            if (checkHairLoss) {
                binding.hairLossLayout.setForeground(null);
                collapse(binding.linearLayoutHairLoss);
                checkHairLoss = false;
            } else {
                binding.hairLossLayout.setForeground(getResources().getDrawable(R.drawable.stroke_button_bink_r8));
                expand(binding.linearLayoutHairLoss);
                checkHairLoss = true;
            }
        } else if (v == binding.hairDamageLayout) {
            binding.hairLossLayout.setForeground(null);
            collapse(binding.linearLayoutHairLoss);
            binding.dandruffLayout.setForeground(null);
            collapse(binding.linearLayoutHairDandruff);
            checkHairLoss = false;
            checkDandruff = false;
            if (checkHairDamage) {
                binding.hairDamageLayout.setForeground(null);
                collapse(binding.linearLayoutHairDamage);
                checkHairDamage = false;
            } else {
                binding.hairDamageLayout.setForeground(getResources().getDrawable(R.drawable.stroke_button_bink_r8));
                expand(binding.linearLayoutHairDamage);
                checkHairDamage = true;
            }
        } else if (v == binding.dandruffLayout) {
            binding.hairLossLayout.setForeground(null);
            collapse(binding.linearLayoutHairLoss);
            binding.hairDamageLayout.setForeground(null);
            collapse(binding.linearLayoutHairDamage);
            checkHairLoss = false;
            checkHairDamage = false;
            if (checkDandruff) {
                binding.dandruffLayout.setForeground(null);
                collapse(binding.linearLayoutHairDandruff);
                checkDandruff = false;
            } else {
                binding.dandruffLayout.setForeground(getResources().getDrawable(R.drawable.stroke_button_bink_r8));
                expand(binding.linearLayoutHairDandruff);
                checkDandruff = true;
            }
        } else if (v == binding.cardDescriptionLoss) {
            Intent intent=new Intent(this, ArticleDetails2Activity.class);
            articles2Model=new Article2Model("49",R.drawable.hair_loss,getString(R.string.hair_loss),getString(R.string.description),getString(R.string.description_hair_loss),"description");
            intent.putExtra("Article",articles2Model);
            startActivity(new Intent(intent));
        }
        else if (v == binding.cardReasonLoss) {
            Intent intent=new Intent(HairActivity.this, ArticleDetails2Activity.class);

            articles2Model=new Article2Model("50",R.drawable.resones_lowquality,getString(R.string.hair_loss),getString(R.string.reasons),getString(R.string.hair_loss_reasons),"description");
            intent.putExtra("Article",articles2Model);
            startActivity(new Intent(intent));
        }
        else if (v == binding.cardTipsLoss) {
            Intent intent=new Intent(HairActivity.this, ArticleDetails2Activity.class);
            articles2Model=new Article2Model("51",R.drawable.tips_hair2,getString(R.string.hair_loss),getString(R.string.tips),getString(R.string.tipsHairLoss),"tips");
            intent.putExtra("Article",articles2Model);
            startActivity(new Intent(intent));
        }
        else if (v == binding.cardNaturalLoss) {
            Intent intent=new Intent(HairActivity.this, ArticleDetails2Activity.class);
            articles2Model=new Article2Model("52",R.drawable.natural_hair,getString(R.string.hair_loss),getString(R.string.natural_recipes2),getString(R.string.natural_recipes_hair_loss),"natural");
            intent.putExtra("Article",articles2Model);
            startActivity(new Intent(intent));
        }
        else if (v == binding.cardDescriptionDamage) {
            Intent intent=new Intent(HairActivity.this, ArticleDetails2Activity.class);
            articles2Model=new Article2Model("53",R.drawable.hair_damage,getString(R.string.hair_damage),getString(R.string.description),getString(R.string.hair_damage_description),"description");
            intent.putExtra("Article",articles2Model);
            startActivity(new Intent(intent));
        }
        else if (v == binding.cardReasonDamage) {
            Intent intent=new Intent(HairActivity.this, ArticleDetails2Activity.class);
            articles2Model=new Article2Model("54",R.drawable.resones_lowquality,getString(R.string.hair_damage),getString(R.string.reasons),getString(R.string.hair_damage_resaons),"description");
            intent.putExtra("Article",articles2Model);
            startActivity(new Intent(intent));
        }
        else if (v == binding.cardTipsDamage) {
            Intent intent=new Intent(HairActivity.this, ArticleDetails2Activity.class);
            articles2Model=new Article2Model("55",R.drawable.tips_hair2,getString(R.string.hair_damage),getString(R.string.tips),getString(R.string.tipsHairDamage),"tips");
            intent.putExtra("Article",articles2Model);
            startActivity(new Intent(intent));
        }
        else if (v == binding.cardNaturalDamage) {
            Intent intent=new Intent(HairActivity.this, ArticleDetails2Activity.class);
            articles2Model=new Article2Model("56",R.drawable.natural_hair,getString(R.string.hair_damage),getString(R.string.natural_recipes2),getString(R.string.natural_recipes_hair_damage),"natural");
            intent.putExtra("Article",articles2Model);
            startActivity(new Intent(intent));
        }
        else if (v == binding.cardDescriptionDandruff) {
            Intent intent=new Intent(HairActivity.this, ArticleDetails2Activity.class);
            articles2Model=new Article2Model("57",R.drawable.dandruff2,getString(R.string.dandruff),getString(R.string.description),getString(R.string.dandruff_description),"description");
            intent.putExtra("Article",articles2Model);
            startActivity(new Intent(intent));
        }
        else if (v == binding.cardReasonDandruff) {
            Intent intent=new Intent(HairActivity.this, ArticleDetails2Activity.class);
            articles2Model=new Article2Model("58",R.drawable.resones_lowquality,getString(R.string.dandruff),getString(R.string.reasons),getString(R.string.dandruff_reasons),"description");
            intent.putExtra("Article",articles2Model);
            startActivity(new Intent(intent));
        }
        else if (v == binding.cardTipsDandruff) {
            Intent intent=new Intent(HairActivity.this, ArticleDetails2Activity.class);
            articles2Model=new Article2Model("59",R.drawable.tips_hair2,getString(R.string.dandruff),getString(R.string.tips),getString(R.string.tipsDandruff),"tips");
            intent.putExtra("Article",articles2Model);
            startActivity(new Intent(intent));
        }
        else if (v == binding.cardNaturalDandruff) {
            Intent intent=new Intent(HairActivity.this, ArticleDetails2Activity.class);
            articles2Model=new Article2Model("60",R.drawable.natural_hair,getString(R.string.dandruff),getString(R.string.natural_recipes2),getString(R.string.natural_recipes_dandruff),"natural");
            intent.putExtra("Article",articles2Model);
            startActivity(new Intent(intent));
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
