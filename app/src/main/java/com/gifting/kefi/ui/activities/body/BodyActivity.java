package com.gifting.kefi.ui.activities.body;

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
import com.gifting.kefi.databinding.ActivityBodyBinding;
import com.gifting.kefi.ui.activities.articles_details_.ArticleDetails2Activity;
import com.gifting.kefi.ui.activities.face.FaceViewModel;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.adapters.BodyProductAdapter;
import com.gifting.kefi.ui.adapters.BodyVideoAdapter;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.Language;

public class BodyActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityBodyBinding binding;
    boolean foot = false;
    boolean knee = false;
    boolean nails = false;
    boolean sensitive_area = false;
    boolean checkBikini = false, checkUnderArm = false;
    private Article2Model articles2Model;
    private FaceViewModel mViewModel;
    private UserSharedPreference userSharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_body);
        mViewModel = new ViewModelProvider(this).get(FaceViewModel.class);

        Language.changeBackDependsLanguage(binding.backImage, getApplicationContext());


        userSharedPreference = new UserSharedPreference(getApplicationContext());


        /*
        binding.linearLayoutFoot.setVisibility(View.GONE);
        binding.linearLayoutKnee.setVisibility(View.GONE);
        binding.linearLayoutNail.setVisibility(View.GONE);
        binding.linearLayoutSensitive.setVisibility(View.GONE);
        */

        binding.videosRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        BodyVideoAdapter adapter = new BodyVideoAdapter(this);
        binding.videosRecycler.setAdapter(adapter);


        binding.productsRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        BodyProductAdapter adapter1 = new BodyProductAdapter(this);
        binding.productsRecycler.setAdapter(adapter1);

        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);
        binding.bikini.setOnClickListener(this);
        binding.cardUnderArm.setOnClickListener(this);
        binding.footLayout.setOnClickListener(this);
        binding.kneeLayout.setOnClickListener(this);
        binding.nailLayout.setOnClickListener(this);
        binding.sensitiveAreaLayout.setOnClickListener(this);
        binding.cardReasonFoot.setOnClickListener(this);
        binding.cardTipsFoot.setOnClickListener(this);
        binding.cardNaturalFoot.setOnClickListener(this);
        binding.cardReasonKnee.setOnClickListener(this);
        binding.cardTipsKnee.setOnClickListener(this);
        binding.cardNaturalKnee.setOnClickListener(this);
        binding.cardReasonNail.setOnClickListener(this);
        binding.cardTipsNail.setOnClickListener(this);
        binding.cardNaturalNail.setOnClickListener(this);
        binding.cardReasonKnee.setOnClickListener(this);
        binding.cardTipsKnee.setOnClickListener(this);
        binding.cardNaturalKnee.setOnClickListener(this);
        binding.cardReasonSensitive.setOnClickListener(this);
        binding.cardNaturalSensitive.setOnClickListener(this);
        binding.cardTipsSensitive.setOnClickListener(this);
        binding.cardReasonUnderArm.setOnClickListener(this);
        binding.cardNaturalUnderArm.setOnClickListener(this);
        binding.cardTipsUnderArm.setOnClickListener(this);


        mViewModel.getFaceVideos("body", userSharedPreference.getLanguage());
        mViewModel.getFaceRepo().observe(this, videoModels -> {
            adapter.setVideoModels(videoModels);
            binding.loadProgress.setVisibility(View.GONE);
        });

        mViewModel.getFaceProducts("body", userSharedPreference.getLanguage());
        mViewModel.getFaceRepo1().observe(this, products -> {
            adapter1.setProductModels(products);
            binding.loadProgress1.setVisibility(View.GONE);
        });

        mViewModel.getFailText().observe(this, s -> {
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
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
        } else if (v == binding.bikini) {
            binding.cardUnderArm.setForeground(null);
            collapse(binding.underArmLayout1);
            checkUnderArm = false;
            if (checkBikini) {
                binding.bikini.setForeground(null);
                collapse(binding.sensitiveAreaLayout1);
                checkBikini = false;
            } else {
                binding.bikini.setForeground(getResources().getDrawable(R.drawable.stroke_button_bink_r10));
                expand(binding.sensitiveAreaLayout1);
                checkBikini = true;
            }
        } else if (v == binding.cardUnderArm) {
            binding.bikini.setForeground(null);
            collapse(binding.sensitiveAreaLayout1);
            checkBikini = false;
            if (checkUnderArm) {
                binding.cardUnderArm.setForeground(null);
                collapse(binding.underArmLayout1);
                checkUnderArm = false;
            } else {
                binding.cardUnderArm.setForeground(getResources().getDrawable(R.drawable.stroke_button_bink_r10));
                expand(binding.underArmLayout1);
                checkUnderArm = true;
            }
        } else if (v == binding.footLayout) {
            if (foot) {
                binding.linearLayoutFoot.setVisibility(View.GONE);
                binding.imageView455.setImageResource(R.drawable.ic_bottom_arrow);
                foot = false;

            } else {
                binding.linearLayoutFoot.setVisibility(View.VISIBLE);
                binding.imageView455.setImageResource(R.drawable.ic_top_arrow);
                foot = true;
            }

        } else if (v == binding.kneeLayout) {
            if (knee) {
                binding.linearLayoutKnee.setVisibility(View.GONE);
                binding.imageView4551.setImageResource(R.drawable.ic_bottom_arrow);
                knee = false;

            } else {
                binding.linearLayoutKnee.setVisibility(View.VISIBLE);
                knee = true;
                binding.imageView4551.setImageResource(R.drawable.ic_top_arrow);
            }


        } else if (v == binding.nailLayout) {
            if (nails) {
                binding.linearLayoutNail.setVisibility(View.GONE);

                binding.imageView4553.setImageResource(R.drawable.ic_bottom_arrow);
                nails = false;
            } else {
                binding.linearLayoutNail.setVisibility(View.VISIBLE);
                binding.imageView4553.setImageResource(R.drawable.ic_top_arrow);
                nails = true;
            }


        } else if (v == binding.sensitiveAreaLayout) {
            if (sensitive_area) {
                binding.linearLayoutSensitive.setVisibility(View.GONE);
                binding.sensitiveAreaLayout1.setVisibility(View.GONE);
                binding.underArmLayout1.setVisibility(View.GONE);
                binding.bikini.setForeground(null);
                binding.cardUnderArm.setForeground(null);
                binding.imageView4556.setImageResource(R.drawable.ic_bottom_arrow);

                checkBikini = false;
                checkUnderArm = false;
                sensitive_area = false;

            } else {
                binding.linearLayoutSensitive.setVisibility(View.VISIBLE);
                sensitive_area = true;
                binding.imageView4556.setImageResource(R.drawable.ic_top_arrow);
            }
        } else if (v == binding.cardReasonFoot) {
            Intent intent = new Intent(getApplicationContext(), ArticleDetails2Activity.class);

            articles2Model = new Article2Model("1", R.drawable.foot_hand44, getString(R.string.foot_hand), getString(R.string.problem), getString(R.string.detailsFootHand), "description");
            intent.putExtra("Article", articles2Model);
            startActivity(new Intent(intent));
        } else if (v == binding.cardTipsFoot) {
            Intent intent = new Intent(getApplicationContext(), ArticleDetails2Activity.class);
            articles2Model = new Article2Model("2", R.drawable.tips_body4, getString(R.string.foot_hand), getString(R.string.tips), getString(R.string.tipsFootHand), "tips");
            intent.putExtra("Article", articles2Model);
            startActivity(new Intent(intent));
        } else if (v == binding.cardNaturalFoot) {
            Intent intent = new Intent(getApplicationContext(), ArticleDetails2Activity.class);
            articles2Model = new Article2Model("3", R.drawable.foot_nautral4, getString(R.string.foot_hand), getString(R.string.natural_recipes2), getString(R.string.natural_recipes_foot_hand), "natural");
            intent.putExtra("Article", articles2Model);
            startActivity(new Intent(intent));
        } else if (v == binding.cardReasonKnee) {
            Intent intent = new Intent(getApplicationContext(), ArticleDetails2Activity.class);
            articles2Model = new Article2Model("4", R.drawable.elbow4, getString(R.string.knee_elbow), getString(R.string.problem), getString(R.string.detailsKneeElbow), "description");
            intent.putExtra("Article", articles2Model);
            startActivity(new Intent(intent));
        } else if (v == binding.cardTipsKnee) {
            Intent intent = new Intent(getApplicationContext(), ArticleDetails2Activity.class);
            articles2Model = new Article2Model("5", R.drawable.tips_body4, getString(R.string.knee_elbow), getString(R.string.tips), getString(R.string.tipsKneeElbow), "tips");
            intent.putExtra("Article", articles2Model);
            startActivity(new Intent(intent));
        } else if (v == binding.cardNaturalKnee) {
            Intent intent = new Intent(getApplicationContext(), ArticleDetails2Activity.class);
            articles2Model = new Article2Model("6", R.drawable.foot_nautral4, getString(R.string.knee_elbow), getString(R.string.natural_recipes2), getString(R.string.natural_recipes_knee_elbow), "natural");
            intent.putExtra("Article", articles2Model);

            startActivity(new Intent(intent));
        } else if (v == binding.cardReasonNail) {
            Intent intent = new Intent(getApplicationContext(), ArticleDetails2Activity.class);
            articles2Model = new Article2Model("7", R.drawable.nails1, getString(R.string.nails), getString(R.string.problem), getString(R.string.detailsNails), "description");
            intent.putExtra("Article", articles2Model);
            startActivity(new Intent(intent));
        } else if (v == binding.cardTipsNail) {
            Intent intent = new Intent(getApplicationContext(), ArticleDetails2Activity.class);
            articles2Model = new Article2Model("8", R.drawable.tips_body4, getString(R.string.nails), getString(R.string.tips), getString(R.string.tipsNails), "tips");
            intent.putExtra("Article", articles2Model);
            startActivity(new Intent(intent));
        } else if (v == binding.cardNaturalNail) {
            Intent intent = new Intent(getApplicationContext(), ArticleDetails2Activity.class);
            articles2Model = new Article2Model("9", R.drawable.foot_nautral4, getString(R.string.nails), getString(R.string.natural_recipes2), getString(R.string.natural_recipes_nails), "natural");
            intent.putExtra("Article", articles2Model);
            startActivity(new Intent(intent));
        } else if (v == binding.cardReasonSensitive) {
            Intent intent = new Intent(getApplicationContext(), ArticleDetails2Activity.class);
            articles2Model = new Article2Model("10", R.drawable.bikini3, getString(R.string.bikini_body), getString(R.string.problem), getString(R.string.detailsBikiniArea), "description");
            intent.putExtra("Article", articles2Model);
            startActivity(new Intent(intent));
        } else if (v == binding.cardTipsSensitive) {
            Intent intent = new Intent(getApplicationContext(), ArticleDetails2Activity.class);
            articles2Model = new Article2Model("11", R.drawable.tips_body4, getString(R.string.bikini_body), getString(R.string.tips), getString(R.string.tipsBikiniArea), "tips");
            intent.putExtra("Article", articles2Model);
            startActivity(new Intent(intent));
        } else if (v == binding.cardNaturalSensitive) {
            Intent intent = new Intent(getApplicationContext(), ArticleDetails2Activity.class);
            articles2Model = new Article2Model("12", R.drawable.sensitive_natural_lowquality4, getString(R.string.bikini_body), getString(R.string.natural_recipes2), getString(R.string.natural_recipes_bikini), "natural");
            intent.putExtra("Article", articles2Model);
            startActivity(new Intent(intent));
        } else if (v == binding.cardReasonUnderArm) {
            Intent intent = new Intent(getApplicationContext(), ArticleDetails2Activity.class);
            articles2Model = new Article2Model("13", R.drawable.underarm_lowquality4, getString(R.string.under_arm_body), getString(R.string.problem), getString(R.string.detailsUnderarmArea), "description");
            intent.putExtra("Article", articles2Model);
            startActivity(new Intent(intent));
        } else if (v == binding.cardTipsUnderArm) {
            Intent intent = new Intent(getApplicationContext(), ArticleDetails2Activity.class);
            articles2Model = new Article2Model("14", R.drawable.tips_body4, getString(R.string.under_arm_body), getString(R.string.tips), getString(R.string.tipsUnderarmArea), "tips");
            intent.putExtra("Article", articles2Model);
            startActivity(new Intent(intent));
        } else if (v == binding.cardNaturalUnderArm) {
            Intent intent = new Intent(getApplicationContext(), ArticleDetails2Activity.class);
            articles2Model = new Article2Model("15", R.drawable.sensitive_natural_lowquality4, getString(R.string.under_arm_body), getString(R.string.natural_recipes2), getString(R.string.natural_recipes_under_arm), "natural");
            intent.putExtra("Article", articles2Model);
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