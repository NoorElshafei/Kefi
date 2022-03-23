package com.gifting.kefi.ui.fragments.reel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.ReelVideoModel;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.FragmentReelBinding;
import com.gifting.kefi.ui.activities.blumers.BlumersActivity;
import com.gifting.kefi.ui.activities.main.MainActivity;
import com.gifting.kefi.ui.activities.upload_media.IntroUploadActivity;
import com.gifting.kefi.ui.adapters.ReelAdapter;
import com.gifting.kefi.ui.fragments.search_reel.SearchReelFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;

public class ReelFragment extends Fragment implements View.OnClickListener {

    private ReelViewModel mViewModel;
    private FragmentReelBinding binding;
    private ReelAdapter adapter;
    private boolean filterIsVisible = false;
    private UserSharedPreference userSharedPreference;
    private ArrayList<ReelVideoModel> reelVideoModelArrayList;
    private ArrayList<String> listIds;


    public static ReelFragment newInstance() {
        return new ReelFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reel, container, false);
        View view = binding.getRoot();
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReelViewModel.class);
        userSharedPreference = new UserSharedPreference(getContext().getApplicationContext());

        getFollowingIds("recently");

        Glide.with(getContext().getApplicationContext()).load(R.drawable.loading).into(binding.loadProgress);


        binding.filterLayout.uploadTimeConstraintContainer.setVisibility(View.GONE);
        binding.filterLayout.durationConstraintContainer.setVisibility(View.GONE);
        binding.filterLayout.categoryConstraintContainer.setVisibility(View.GONE);
        binding.filterLayout.sortByConstraintContainer.setVisibility(View.GONE);

        binding.reelRecyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext(), RecyclerView.VERTICAL, false));
        adapter = new ReelAdapter(getContext(), this);
        binding.reelRecyclerView.setAdapter(adapter);

        binding.recentlyLayout.setCardElevation(6);
        binding.mostViewedLayout.setCardElevation(0);

        binding.menu.setOnMenuButtonClickListener(v -> {
            if (binding.frameBehindMenu.getVisibility() == View.GONE) {
                binding.menu.open(true);
                binding.frameBehindMenu.setVisibility(View.VISIBLE);
            } else {
                binding.menu.close(true);
                binding.frameBehindMenu.setVisibility(View.GONE);
            }

        });


        binding.searchEditText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("ReelList", reelVideoModelArrayList);
                Fragment fragment = SearchReelFragment.newInstance();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("null")
                        .replace(R.id.container, fragment, "searchReel").commit();
                return true;
            }
            return false;
        });
    }

    private void getFollowingIds(String recently) {
        mViewModel.getUserFollowingIds(userSharedPreference.getUserDetails().getId());
        mViewModel.getFollowingNumberLiveData().observe(getViewLifecycleOwner(), strings -> {
            mViewModel.getFollowingNumberLiveData().removeObservers(getViewLifecycleOwner());
            binding.followBlummersLayout.setVisibility(View.GONE);
            listIds = strings;
            if (recently.equals("recently"))
                getRecentlyPostsOfUsers(listIds);
            else
                getMostViewedPostsOfUsers(listIds);
        });
        mViewModel.getFailText().observe(getViewLifecycleOwner(), s -> {
            mViewModel.getFailText().removeObservers(getViewLifecycleOwner());
            binding.followBlummersLayout.setVisibility(View.VISIBLE);
            //Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            binding.loadingLayout.setVisibility(View.GONE);
        });
    }

    private void getRecentlyPostsOfUsers(ArrayList<String> listIds) {
        adapter.removeAllData();
        mViewModel.getPostsOfUsers(listIds);
        mViewModel.getArrayListMutableLiveData().observe(getViewLifecycleOwner(), reelVideoModels -> {
            reelVideoModelArrayList = reelVideoModels;
            mViewModel.getArrayListMutableLiveData().removeObservers(getViewLifecycleOwner());
            displayRecentlyPosts(reelVideoModels);
        });
        mViewModel.getFailText().observe(getViewLifecycleOwner(), s -> {
            mViewModel.getFailText().removeObservers(getViewLifecycleOwner());
            Toast.makeText(getContext().getApplicationContext(), getString(R.string.no_videos_exist_now), Toast.LENGTH_SHORT).show();
            binding.loadingLayout.setVisibility(View.GONE);

        });
    }

    private void getMostViewedPostsOfUsers(ArrayList<String> listIds) {
        adapter.removeAllData();
        mViewModel.getPostsOfUsers(listIds);
        mViewModel.getArrayListMutableLiveData().observe(getViewLifecycleOwner(), reelVideoModels -> {
            mViewModel.getArrayListMutableLiveData().removeObservers(getViewLifecycleOwner());
            displayMostViewedPosts(reelVideoModels);
        });
        mViewModel.getFailText().observe(getViewLifecycleOwner(), s -> {
            mViewModel.getFailText().removeObservers(getViewLifecycleOwner());
            Toast.makeText(getContext().getApplicationContext(), getString(R.string.no_videos_exist_now), Toast.LENGTH_SHORT).show();
            binding.loadingLayout.setVisibility(View.GONE);

        });
    }

    private void displayRecentlyPosts(ArrayList<ReelVideoModel> reelVideoModelArrayList) {
        Collections.sort(reelVideoModelArrayList, (o1, o2) -> Long.compare(o2.getTime_created(), o1.getTime_created()));
        adapter.setReelAdapters(reelVideoModelArrayList);
        binding.loadingLayout.setVisibility(View.GONE);

    }

    private void displayMostViewedPosts(ArrayList<ReelVideoModel> reelVideoModelArrayList) {
        Collections.sort(reelVideoModelArrayList, (o1, o2) -> o1.getTotal_views() - o2.getTotal_views());
        Collections.reverse(reelVideoModelArrayList);

        adapter.setReelAdapters(reelVideoModelArrayList);
        binding.loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recentlyLayout.setOnClickListener(this);
        binding.mostViewedLayout.setOnClickListener(this);
        binding.filterButton.setOnClickListener(this);
        binding.filterLayout.uploadTimeConstraint.setOnClickListener(this);
        binding.filterLayout.durationConstraint.setOnClickListener(this);
        binding.filterLayout.categoryConstraint.setOnClickListener(this);
        binding.filterLayout.sortByConstraint.setOnClickListener(this);
        binding.filterLayout.anytimeText.setOnClickListener(this);
        binding.filterLayout.lastHourText.setOnClickListener(this);
        binding.filterLayout.thisYearText.setOnClickListener(this);
        binding.filterLayout.todayText.setOnClickListener(this);
        binding.filterLayout.thisWeekText.setOnClickListener(this);
        binding.filterLayout.thisMonthText.setOnClickListener(this);
        binding.filterLayout.shortText.setOnClickListener(this);
        binding.filterLayout.longText.setOnClickListener(this);
        binding.filterLayout.faceText.setOnClickListener(this);
        binding.filterLayout.hairText.setOnClickListener(this);
        binding.filterLayout.bodyText.setOnClickListener(this);
        binding.filterLayout.fitnessText.setOnClickListener(this);
        binding.filterLayout.uploadDateText.setOnClickListener(this);
        binding.filterLayout.viewCountText.setOnClickListener(this);
        binding.filterLayout.clearText.setOnClickListener(this);
        binding.addVideoButton.setOnClickListener(this);
        //binding.myUploadButton.setOnClickListener(this);
        binding.filterLayout.placeOrderButton1.setOnClickListener(this);
        binding.blummersButton.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
      /*  ((MainActivity) getActivity()).getBinding().reelImage.setImageResource(R.drawable.ic_reel_active);
        ((MainActivity) getActivity()).getBinding().reelText.setTextColor(getResources().getColor(R.color.bink));*/
    }

    @Override
    public void onStop() {
        super.onStop();
      /*  ((MainActivity) getActivity()).getBinding().reelImage.setImageResource(R.drawable.ic_reel_un_active);
        ((MainActivity) getActivity()).getBinding().reelText.setTextColor(getResources().getColor(R.color.bink_lighter));*/
    }

    @Override
    public void onClick(View v) {
        if (v == binding.recentlyLayout) {
            binding.loadingLayout.setVisibility(View.VISIBLE);
            binding.followBlummersLayout.setVisibility(View.GONE);
            if (listIds != null)
                getRecentlyPostsOfUsers(listIds);
            else
                getFollowingIds("recently");

            binding.recentlyLayout.setCardElevation(6);
            binding.mostViewedLayout.setCardElevation(0);

        } else if (v == binding.mostViewedLayout) {
            binding.followBlummersLayout.setVisibility(View.GONE);
            binding.loadingLayout.setVisibility(View.VISIBLE);
            binding.recentlyLayout.setCardElevation(0);
            binding.mostViewedLayout.setCardElevation(6);
            if (listIds != null)
                getMostViewedPostsOfUsers(listIds);
            else
                getFollowingIds("most_viwed");
        } /*else if (v == binding.filterButton) {
            if (filterIsVisible) {
                AnimationCustomization.hideViewFromLeftToRight(binding.filterLayout.filterConstraint, getContext());
                AnimationCustomization.showViewFromLeftToRight(binding.reelContainer, getContext());
                filterIsVisible = false;

            } else {
                AnimationCustomization.hideViewFromRightToLeft(binding.reelContainer, getContext());
                AnimationCustomization.showViewFromRightToLeft(binding.filterLayout.filterConstraint, getContext());
                filterIsVisible = true;
            }
            *//*startActivity(new Intent(getContext(), FilterReelActivity.class));
            getActivity().overridePendingTransition(R.anim.enter_form_right, R.anim.exit_from_right);*//*
        } */else if (v == binding.filterLayout.uploadTimeConstraint) {
            checkVisibility(binding.filterLayout.uploadTimeConstraintContainer, binding.filterLayout.uploadTimeImage, 250);
        } else if (v == binding.filterLayout.durationConstraint) {
            checkVisibility(binding.filterLayout.durationConstraintContainer, binding.filterLayout.durationConstraintImage, 70);
        } else if (v == binding.filterLayout.categoryConstraint) {
            checkVisibility(binding.filterLayout.categoryConstraintContainer, binding.filterLayout.categoryConstraintImage, 250);
        } else if (v == binding.filterLayout.sortByConstraint) {
            checkVisibility(binding.filterLayout.sortByConstraintContainer, binding.filterLayout.sortByConstraintImage, 70);
        }
        //
        else if (v == binding.filterLayout.anytimeText) {
            binding.filterLayout.uploadTimeVariable.setText("any time");
            binding.filterLayout.uploadTimeVariable.setTextColor(getResources().getColor(R.color.bink));
            checkVisibility(binding.filterLayout.uploadTimeConstraintContainer, binding.filterLayout.uploadTimeImage, 250);
        } else if (v == binding.filterLayout.lastHourText) {
            binding.filterLayout.uploadTimeVariable.setText("last hour");
            binding.filterLayout.uploadTimeVariable.setTextColor(getResources().getColor(R.color.bink));
            checkVisibility(binding.filterLayout.uploadTimeConstraintContainer, binding.filterLayout.uploadTimeImage, 250);
        } else if (v == binding.filterLayout.todayText) {
            binding.filterLayout.uploadTimeVariable.setText("today");
            binding.filterLayout.uploadTimeVariable.setTextColor(getResources().getColor(R.color.bink));
            checkVisibility(binding.filterLayout.uploadTimeConstraintContainer, binding.filterLayout.uploadTimeImage, 250);
        } else if (v == binding.filterLayout.thisWeekText) {
            binding.filterLayout.uploadTimeVariable.setText("this week");
            binding.filterLayout.uploadTimeVariable.setTextColor(getResources().getColor(R.color.bink));
            checkVisibility(binding.filterLayout.uploadTimeConstraintContainer, binding.filterLayout.uploadTimeImage, 250);

        } else if (v == binding.filterLayout.thisMonthText) {
            binding.filterLayout.uploadTimeVariable.setText("this month");
            binding.filterLayout.uploadTimeVariable.setTextColor(getResources().getColor(R.color.bink));
            checkVisibility(binding.filterLayout.uploadTimeConstraintContainer, binding.filterLayout.uploadTimeImage, 250);

        } else if (v == binding.filterLayout.thisYearText) {
            binding.filterLayout.uploadTimeVariable.setText("this year");
            binding.filterLayout.uploadTimeVariable.setTextColor(getResources().getColor(R.color.bink));
            checkVisibility(binding.filterLayout.uploadTimeConstraintContainer, binding.filterLayout.uploadTimeImage, 250);
        }
        //
        else if (v == binding.filterLayout.shortText) {
            binding.filterLayout.durationVariable.setText("short");
            binding.filterLayout.durationVariable.setTextColor(getResources().getColor(R.color.bink));
            checkVisibility(binding.filterLayout.durationConstraintContainer, binding.filterLayout.durationConstraintImage, 70);
        } else if (v == binding.filterLayout.longText) {
            binding.filterLayout.durationVariable.setText("long");
            binding.filterLayout.durationVariable.setTextColor(getResources().getColor(R.color.bink));
            checkVisibility(binding.filterLayout.durationConstraintContainer, binding.filterLayout.durationConstraintImage, 70);
        }
        //
        else if (v == binding.filterLayout.faceText) {
            binding.filterLayout.categoryVariable.setText("face");
            binding.filterLayout.categoryVariable.setTextColor(getResources().getColor(R.color.bink));
            checkVisibility(binding.filterLayout.categoryConstraintContainer, binding.filterLayout.categoryConstraintImage, 250);
        } else if (v == binding.filterLayout.hairText) {
            binding.filterLayout.categoryVariable.setText("hair");
            binding.filterLayout.categoryVariable.setTextColor(getResources().getColor(R.color.bink));
            checkVisibility(binding.filterLayout.categoryConstraintContainer, binding.filterLayout.categoryConstraintImage, 250);

        } else if (v == binding.filterLayout.bodyText) {
            binding.filterLayout.categoryVariable.setText("body");
            binding.filterLayout.categoryVariable.setTextColor(getResources().getColor(R.color.bink));
            checkVisibility(binding.filterLayout.categoryConstraintContainer, binding.filterLayout.categoryConstraintImage, 250);

        } else if (v == binding.filterLayout.fitnessText) {
            binding.filterLayout.categoryVariable.setText("fitness");
            binding.filterLayout.categoryVariable.setTextColor(getResources().getColor(R.color.bink));
            checkVisibility(binding.filterLayout.categoryConstraintContainer, binding.filterLayout.categoryConstraintImage, 250);
        }

        //
        else if (v == binding.filterLayout.uploadDateText) {
            binding.filterLayout.sortByVariable.setText("upload date");
            binding.filterLayout.sortByVariable.setTextColor(getResources().getColor(R.color.bink));
            checkVisibility(binding.filterLayout.sortByConstraintContainer, binding.filterLayout.sortByConstraintImage, 70);

        } else if (v == binding.filterLayout.viewCountText) {
            binding.filterLayout.sortByVariable.setText("view count");
            binding.filterLayout.sortByVariable.setTextColor(getResources().getColor(R.color.bink));
            checkVisibility(binding.filterLayout.sortByConstraintContainer, binding.filterLayout.sortByConstraintImage, 70);
        } else if (v == binding.filterLayout.clearText) {
            binding.filterLayout.uploadTimeVariable.setText("any time");
            binding.filterLayout.uploadTimeVariable.setTextColor(getResources().getColor(R.color.bink_alpha_40));

            binding.filterLayout.durationVariable.setText("any");
            binding.filterLayout.durationVariable.setTextColor(getResources().getColor(R.color.bink_alpha_40));

            binding.filterLayout.categoryVariable.setText("any");
            binding.filterLayout.categoryVariable.setTextColor(getResources().getColor(R.color.bink_alpha_40));

            binding.filterLayout.sortByVariable.setText("any");
            binding.filterLayout.sortByVariable.setTextColor(getResources().getColor(R.color.bink_alpha_40));
        } else if (v == binding.addVideoButton) {
            startActivity(new Intent(getContext().getApplicationContext(), IntroUploadActivity.class));
        } else if (v == binding.blummersButton) {
            startActivity(new Intent(getContext().getApplicationContext(), BlumersActivity.class));
        }


    }


    private void expand(ConstraintLayout constraintLayout, int targetHeight) {
               /* //set Visible
        int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        constraintLayout.measure(widthSpec, heightSpec);

        ValueAnimator mAnimator = slideAnimator(0, constraintLayout.getMeasuredHeight(), constraintLayout);
        mAnimator.start();*/


        int prevHeight = constraintLayout.getHeight();

        constraintLayout.setVisibility(View.VISIBLE);
        int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        constraintLayout.measure(widthSpec, heightSpec);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, constraintLayout.getMeasuredHeight());
        valueAnimator.addUpdateListener(animation -> {
            constraintLayout.getLayoutParams().height = (int) animation.getAnimatedValue();
            constraintLayout.requestLayout();
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(400);
        valueAnimator.start();

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


    private void checkVisibility(ConstraintLayout constraintLayout, ImageView imageView, int targetHeight) {
        if (constraintLayout.getVisibility() == View.GONE) {
            constraintLayout.setVisibility(View.VISIBLE);
            TransitionManager.beginDelayedTransition(constraintLayout, new AutoTransition());
            expand(constraintLayout, targetHeight);
            imageView.setImageResource(R.drawable.ic_expand_icon);
        } else {
            //TransitionManager.beginDelayedTransition(trip_timeLine_cardView,AutoTransition())
            collapse(constraintLayout);
            imageView.setImageResource(R.drawable.ic_forword11);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        binding.frameBehindMenu.setVisibility(View.GONE);
        binding.menu.close(true);
    }
}


//ValueAnimator animator = ValueAnimator.ofInt(0, constraintLayout.getMeasuredHeight());
       /* animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });


          mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                binding.textView67.setVisibility(View.VISIBLE);
                binding.textView67.setVisibility(View.VISIBLE);
                binding.textView67.setVisibility(View.VISIBLE);
                binding.textView67.setVisibility(View.VISIBLE);
                binding.textView67.setVisibility(View.VISIBLE);
            }
        });*/


        /*constraintLayout.setVisibility(View.VISIBLE);
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
        constraintLayout.startAnimation(a);*/