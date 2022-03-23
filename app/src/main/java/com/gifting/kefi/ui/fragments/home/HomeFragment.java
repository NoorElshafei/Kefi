package com.gifting.kefi.ui.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gifting.kefi.R;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.HomeFragmentBinding;
import com.gifting.kefi.ui.activities.articles.ArticlesActivity;
import com.gifting.kefi.ui.activities.blumers.BlumersActivity;
import com.gifting.kefi.ui.activities.body.BodyActivity;
import com.gifting.kefi.ui.activities.face.FaceActivity;
import com.gifting.kefi.ui.activities.fitness.FitnessActivity;
import com.gifting.kefi.ui.activities.hair.HairActivity;
import com.gifting.kefi.ui.activities.kids.KidsActivity;
import com.gifting.kefi.ui.activities.main.MainActivity;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.activities.questions_container.QuestionsActivity;
import com.gifting.kefi.ui.activities.videos.VideosActivity;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.ui.fragments.profile.ProfileFragment;
import com.gifting.kefi.ui.fragments.search.SearchFragment;
import com.bumptech.glide.Glide;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel mViewModel;
    private HomeFragmentBinding binding;
    private UserSharedPreference userSharedPreference;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);

        return binding.getRoot();


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        userSharedPreference = new UserSharedPreference(getContext().getApplicationContext());


      /*  binding.slideButton.setOnSlideCompleteListener(slideToActView -> {
            //Toast.makeText(getContext(), "success", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getContext(), QuestionsActivity.class));
            binding.slideButton.resetSlider();
        });

        binding.slideButton.setScrollBarFadeDuration(200);*/

        String[] myName = userSharedPreference.getUserDetails().getName().split(" ");
        binding.title.setText(getString(R.string.hi) +" "+ myName[0].toUpperCase().charAt(0) + myName[0].substring(1).toLowerCase() +" "+ getString(R.string.comma));
      /*  binding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        adapter1 = new HomeAdapter(getContext());
        binding.homeRecyclerView.setAdapter(adapter1);*/

        Glide.with(this).load(userSharedPreference.getUserDetails().getImageURL()).placeholder(R.drawable.avatar).into(binding.userImageHome);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.imageProfile.setOnClickListener(this);
        binding.fitnessLayout.setOnClickListener(this);
        binding.articleLayout.setOnClickListener(this);
        binding.blumersLayout.setOnClickListener(this);
        binding.videosLayout.setOnClickListener(this);
        binding.hairLayout.setOnClickListener(this);
        binding.bodyLayout.setOnClickListener(this);
        binding.kidsLayout.setOnClickListener(this);
        binding.faceLayout.setOnClickListener(this);

        binding.searchEditText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("null")
                        .replace(R.id.container, SearchFragment.newInstance(), "search").commit();
                return true;
            }
            return false;
        });

    }

    @Override
    public void onClick(View v) {
        if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getChildFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.notificationLayout) {
            startActivity(new Intent(getContext(), NotificationsActivity.class));
        } else if (v == binding.imageProfile) {
            ((MainActivity) getActivity()).setHome(false);
            ((MainActivity) getActivity()).setLiked(false);
            ((MainActivity) getActivity()).setStore(false);
            ((MainActivity) getActivity()).setReel(false);
            ((MainActivity) getActivity()).setProfile(true);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ProfileFragment.newInstance(), "profile").commit();
        } else if (v == binding.fitnessLayout) {
            Intent intent = new Intent(getContext(), FitnessActivity.class);
            getActivity().overridePendingTransition(R.anim.enter_form_right_d3, R.anim.exit_from_right_d3);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        } else if (v == binding.articleLayout) {
            Intent intent = new Intent(getContext(), ArticlesActivity.class);
            getActivity().overridePendingTransition(R.anim.enter_form_right_d3, R.anim.exit_from_right_d3);
            startActivity(intent);
        } else if (v == binding.blumersLayout) {
            Intent intent = new Intent(getContext(), BlumersActivity.class);
            getActivity().overridePendingTransition(R.anim.enter_form_right_d3, R.anim.exit_from_right_d3);
            startActivity(intent);
        } else if (v == binding.videosLayout) {
            Intent intent = new Intent(getContext(), VideosActivity.class);
            getActivity().overridePendingTransition(R.anim.enter_form_right_d3, R.anim.exit_from_right_d3);
            startActivity(intent);
        } else if (v == binding.faceLayout) {
            Intent intent = new Intent(getContext(), FaceActivity.class);
            getActivity().overridePendingTransition(R.anim.enter_form_right_d3, R.anim.exit_from_right_d3);
            startActivity(intent);
        } else if (v == binding.hairLayout) {
            startActivity(new Intent(getContext(), HairActivity.class));
        } else if (v == binding.bodyLayout) {
            startActivity(new Intent(getContext(), BodyActivity.class));
        } else if (v == binding.kidsLayout) {
            startActivity(new Intent(getContext(), KidsActivity.class));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).getBinding().homeImage.setImageResource(R.drawable.ic_home_active);
        ((MainActivity) getActivity()).getBinding().homeText.setTextColor(getResources().getColor(R.color.bink));
    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity) getActivity()).getBinding().homeImage.setImageResource(R.drawable.ic_home_un_active);
        ((MainActivity) getActivity()).getBinding().homeText.setTextColor(getResources().getColor(R.color.bink_lighter));
    }



}