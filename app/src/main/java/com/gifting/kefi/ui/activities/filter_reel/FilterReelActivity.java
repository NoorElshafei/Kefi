package com.gifting.kefi.ui.activities.filter_reel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.gifting.kefi.R;
import com.gifting.kefi.databinding.ActivityFilterReelBinding;

public class FilterReelActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityFilterReelBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter_reel);
        binding.uploadTimeConstraint.setOnClickListener(this);
        binding.durationConstraint.setOnClickListener(this);
        binding.categoryConstraint.setOnClickListener(this);
        binding.sortByConstraint.setOnClickListener(this);
        binding.placeOrderButton.setOnClickListener(this);
        binding.placeOrderButton1.setOnClickListener(this);

        binding.uploadTimeImage.setOnClickListener(this);
        binding.uploadTimeImage1.setOnClickListener(this);



    }

    private void expand(ConstraintLayout constraintLayout) {
        //set Visible
        constraintLayout.setVisibility(View.VISIBLE);
/*
        int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        constraintLayout.measure(widthSpec, heightSpec);

        //ValueAnimator mAnimator = slideAnimator(0, constraintLayout.getMeasuredHeight(), constraintLayout);
        ValueAnimator animator = ValueAnimator.ofInt(0, constraintLayout.getMeasuredHeight());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });*/


        /*  mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
              *//**//*  binding.textView67.setVisibility(View.VISIBLE);
                binding.textView67.setVisibility(View.VISIBLE);
                binding.textView67.setVisibility(View.VISIBLE);
                binding.textView67.setVisibility(View.VISIBLE);
                binding.textView67.setVisibility(View.VISIBLE);*//**//*
            }
        });*//*
        animator.start();*/


        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) constraintLayout.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        constraintLayout.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = constraintLayout.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        constraintLayout.getLayoutParams().height = 1;
        //constraintLayout.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                constraintLayout.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                constraintLayout.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration((int) (targetHeight / constraintLayout.getContext().getResources().getDisplayMetrics().density));
        constraintLayout.startAnimation(a);
    }

    private void collapse(ConstraintLayout constraintLayout) {
        int finalHeight = constraintLayout.getHeight();
        ValueAnimator mAnimator = slideAnimator(finalHeight, 0, constraintLayout);
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //Height=0, but it set visibility to GONE
                constraintLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    private ValueAnimator slideAnimator(int start, int end, ConstraintLayout constraintLayout) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(animation -> {
            //Update Height
            int value = (int) animation.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = constraintLayout.getLayoutParams();
            layoutParams.height = value;
            constraintLayout.setLayoutParams(layoutParams);
        });


        return animator;
    }

    @Override
    public void onClick(View v) {
        if (v == binding.uploadTimeConstraint) {
            checkVisibility(binding.uploadTimeConstraintContainer, binding.uploadTimeImage);
        } else if (v == binding.durationConstraint) {
            checkVisibility(binding.durationConstraintContainer, binding.durationConstraintImage);
        } else if (v == binding.categoryConstraint) {
            checkVisibility(binding.categoryConstraintContainer, binding.categoryConstraintImage);
        } else if (v == binding.sortByConstraint) {
            checkVisibility(binding.sortByConstraintContainer, binding.sortByConstraintImage);
        }

    }

    private void checkVisibility(ConstraintLayout constraintLayout, ImageView imageView) {
        if (constraintLayout.getVisibility() == View.GONE) {
            //TransitionManager.beginDelayedTransition(constraintLayout,new AutoTransition());
            expand(constraintLayout);
            imageView.setImageResource(R.drawable.ic_expand_icon);
        } else {
            //TransitionManager.beginDelayedTransition(trip_timeLine_cardView,AutoTransition())
            collapse(constraintLayout);
            imageView.setImageResource(R.drawable.ic_forword11);
        }

    }
}