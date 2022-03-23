package com.gifting.kefi.util;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Path;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.gifting.kefi.R;

public class AnimationCustomization {

    public static void animationInFromRight(CardView indicator, CardView indicator2, CardView indicator3, Context context) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(indicator, "translationX", 0.0f, -indicator.getWidth());
        objectAnimator.setDuration(400);


        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d("noooor", "onAnimationStart: 1");
                indicator2.setCardBackgroundColor(context.getResources().getColor(R.color.bink));
                indicator3.setCardBackgroundColor(context.getResources().getColor(R.color.bink_light));
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        objectAnimator.start();
    }


    public static void animationInFromLeft(CardView indicator, CardView indicator2, CardView indicator3, Context context) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(indicator, "translationX", -indicator.getWidth(), 0);
        objectAnimator.setDuration(400);

        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d("noooor", "onAnimationStart: 0");

                indicator2.setCardBackgroundColor(context.getResources().getColor(R.color.bink_light));
                indicator3.setCardBackgroundColor(context.getResources().getColor(R.color.bink));
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.start();
    }


    public static void outToLeftAnimation(View v) {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(300);
        outtoLeft.setInterpolator(new AccelerateInterpolator());

        outtoLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.setAnimation(outtoLeft);
        //outtoLeft.start();


    }

    public static Animation inFromRightAnimation() {

        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +0.4f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(250);
        inFromRight.setInterpolator(new AccelerateInterpolator());

        return inFromRight;
    }

    public static void animationInFromBottom(ConstraintLayout layout, Context context) {
        Path path = new Path();
        path.arcTo(0f, 0f, 100f, 100f, 270f, -180f, true);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(layout, View.X, View.Y, path);
        objectAnimator.setDuration(2000);


        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d("noooor", "onAnimationStart: 1");

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        objectAnimator.start();
    }

    //done
    public static void hideViewFromRightToLeft(final View view, Context context) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.exit_from_right);
        animation.setDuration(400);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }
        });

        view.startAnimation(animation);
    }

    //done
    public static void showViewFromRightToLeft(final View view, Context context) {
        view.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.enter_form_right);
        animation.setDuration(400);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }
        });

        view.startAnimation(animation);
    }

    //done
    public static void hideViewFromLeftToRight(final View view, Context context) {

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.exit_from_left);
        animation.setDuration(400);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }
        });

        view.startAnimation(animation);
    }

    public static void showViewFromLeftToRight(final View view, Context context) {
        view.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.enter_form_left);
        animation.setDuration(400);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }
        });

        view.startAnimation(animation);
    }

}
