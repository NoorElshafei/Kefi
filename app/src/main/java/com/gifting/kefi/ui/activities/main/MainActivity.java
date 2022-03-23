package com.gifting.kefi.ui.activities.main;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.gifting.kefi.R;
import com.gifting.kefi.databinding.ActivityMainBinding;
import com.gifting.kefi.databinding.FragmentSearchBinding;
import com.gifting.kefi.ui.fragments.exit_bottom_sheet.ExitAppFragment;
import com.gifting.kefi.ui.fragments.home.HomeFragment;
import com.gifting.kefi.ui.fragments.liked.LikedFragment;
import com.gifting.kefi.ui.fragments.profile.ProfileFragment;
import com.gifting.kefi.ui.fragments.store.StoreFragment;
import com.gifting.kefi.util.CustomLanguage;
import com.gifting.kefi.util.Language;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    private boolean isHome = true, isStore, isReel, isLiked, isProfile;
    private String searchText;
    private FragmentSearchBinding binding1;

    public ActivityMainBinding getBinding() {
        return binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().addToBackStack("home")
                .replace(R.id.container, HomeFragment.newInstance(), "home").commit();

        Language.changeBackDependsLanguage1(binding.storeImage, getApplicationContext());


        binding.profile.setOnClickListener(this);
        binding.home.setOnClickListener(this);
        binding.stor.setOnClickListener(this);
        binding.liked.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        CustomLanguage.checkLanguage(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.profile && !isProfile) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_form_right_d3, R.anim.exit_from_right_d3)
                    .replace(R.id.container, ProfileFragment.newInstance(), "profile").commit();
            isProfile = true;
            isReel = false;
            isStore = false;
            isLiked = false;
            isHome = false;
        } else if (v == binding.home && !isHome) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_form_left_d3, R.anim.exit_from_left_d3)
                    .replace(R.id.container, HomeFragment.newInstance(), "home").commit();
            isHome = true;
            isReel = false;
            isStore = false;
            isLiked = false;
            isProfile = false;
        } else if (v == binding.stor && !isStore) {
            if (isHome) {
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_form_right_d3, R.anim.exit_from_right_d3)
                        .replace(R.id.container, StoreFragment.newInstance(), "store").commit();
                isHome = false;
            } else {
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_form_left_d3, R.anim.exit_from_left_d3)
                        .replace(R.id.container, StoreFragment.newInstance(), "store").commit();
                isReel = false;
                isStore = false;
                isLiked = false;
                isProfile = false;
            }

            isStore = true;
        } else if (v == binding.liked && !isLiked) {
            if (isProfile) {
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_form_left_d3, R.anim.exit_from_left_d3)
                        .replace(R.id.container, LikedFragment.newInstance(), "liked").commit();
                isProfile = false;
            } else {
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_form_right_d3, R.anim.exit_from_right_d3)
                        .replace(R.id.container, LikedFragment.newInstance(), "liked").commit();
                isHome = false;
                isStore = false;
                isReel = false;
            }
            isLiked = true;
        }
    }

    @Override
    public void onBackPressed() {
        int d = getSupportFragmentManager().getBackStackEntryCount();
        if (d > 1) {
            if (getSupportFragmentManager().findFragmentByTag("search") != null) {
                if (binding1.searchEditText.getText().toString().trim().isEmpty()) {
                    super.onBackPressed();
                } else {
                    binding1.searchEditText.setText("");
                }

            } else {
                super.onBackPressed();
            }

        } else {
            ExitAppFragment exitAppFragment = ExitAppFragment.newInstance();
            exitAppFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(FragmentSearchBinding binding) {
        binding1 = binding;
    }

    public void setHome(boolean home) {
        isHome = home;
    }

    public void setStore(boolean store) {
        isStore = store;
    }

    public void setReel(boolean reel) {
        isReel = reel;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public void setProfile(boolean profile) {
        isProfile = profile;
    }
}