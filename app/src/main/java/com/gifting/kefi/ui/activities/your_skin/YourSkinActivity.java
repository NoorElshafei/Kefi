package com.gifting.kefi.ui.activities.your_skin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.User;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivityYourSkinBinding;
import com.gifting.kefi.ui.activities.main.MainActivity;
import com.gifting.kefi.util.DialogCustom;

public class YourSkinActivity extends AppCompatActivity implements View.OnClickListener {

    private UserSharedPreference userSharedPreference;
    private int skinResult;
    private ActivityYourSkinBinding binding;
    private YourSkinViewModel mViewModel;
    private User user;
    private DialogCustom dialogCustom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_your_skin);
        mViewModel= new ViewModelProvider(this).get(YourSkinViewModel.class);

        userSharedPreference = new UserSharedPreference(getApplicationContext());


        mViewModel.getUserLiveData().observe(this, user -> {
            mViewModel.getUserLiveData().removeObservers(this);
            dialogCustom.dismissDialog();
            Intent intent = new Intent(YourSkinActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });



        skinResult = userSharedPreference.getUserSkin();
        checkSkin();

        if (skinResult == 0) {
            binding.skinImage.setImageResource(R.drawable.ic_oily_face_big);
            binding.skinText.setText(getString(R.string.oily));

        } else if (skinResult == 1) {
            binding.skinImage.setImageResource(R.drawable.ic_normal_face_big);
            binding.skinText.setText(getString(R.string.normal));

        } else if (skinResult == 2) {
            binding.skinImage.setImageResource(R.drawable.ic_dry_face_big);
            binding.skinText.setText(getString(R.string.dry));

        } else if (skinResult == 3) {
            binding.skinImage.setImageResource(R.drawable.ic_mixed_face_big);
            binding.skinText.setText(getString(R.string.mixed));
        }

        binding.letsGoButton.setOnClickListener(this);



    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {

        if (v == binding.letsGoButton) {
            dialogCustom = new DialogCustom(this);
            dialogCustom.showDialog();
            userSharedPreference.setIsQuestionsAnswered(true);
            user = userSharedPreference.getUserDetails();
            mViewModel.storeUserData(user);
        }
    }



    private void checkSkin() {
        if (userSharedPreference.getLanguage().equals("ar")) {
            if (userSharedPreference.getUserDetails().getSkinType().equals("DRY")) {
                binding.skinText.setText(" جافة");
            } else if (userSharedPreference.getUserDetails().getSkinType().equals("OILY")) {
                binding.skinText.setText(" دهنية");
            } else if (userSharedPreference.getUserDetails().getSkinType().equals("NORMAL")) {
                binding.skinText.setText(" عادية");
            } else {
                binding.skinText.setText(" مختلطة");
            }
        } else
            binding.skinText.setText(userSharedPreference.getUserDetails().getSkinType());


    }

}