package com.gifting.kefi.ui.activities.edit_profile;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.User;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivityEditProfileBinding;
import com.gifting.kefi.ui.activities.change_phone_setting.ChangePhoneActivity;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.ui.fragments.select_image_bottom_sheet.SelectImageFragment;
import com.gifting.kefi.util.DialogCustom;
import com.gifting.kefi.util.EditTextV2;
import com.gifting.kefi.util.Language;
import com.bumptech.glide.Glide;


public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityEditProfileBinding binding;
    private UserSharedPreference userSharedPreference;
    private EditProfileViewModel editProfileViewModel;
    private DialogCustom dialogCustom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);

        Language.changeBackDependsLanguage(binding.backImage, getApplicationContext());


        editProfileViewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);


        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.saveButton.setOnClickListener(this);

        binding.selectImage.setOnClickListener(this);
        binding.changePhoneText.setOnClickListener(this);
        binding.eyePassImage11.setOnClickListener(this);
        binding.eyePassImage1.setOnClickListener(this);
        binding.repeatEyePassImage.setOnClickListener(this);
        binding.back.setOnClickListener(this);

        userSharedPreference = new UserSharedPreference(getApplicationContext());

        binding.phoneText.setText(userSharedPreference.getUserDetails().getPhoneNumber());
    }

    @Override
    public void onClick(View v) {
        dialogCustom = new DialogCustom(this);
        if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.notificationLayout) {
            startActivity(new Intent(EditProfileActivity.this, NotificationsActivity.class));
        } else if (v == binding.saveButton) {
            if (!binding.oldPasswordEditText.getText().toString().trim().isEmpty() && !binding.oldPasswordEditText.getText().toString().trim().isEmpty() && !binding.oldPasswordEditText.getText().toString().trim().isEmpty()) {
                if (binding.newPasswordEditText.getText().toString().equals(binding.repeatPasswordEditText.getText().toString())) {
                    dialogCustom.showDialog();
                    editProfileViewModel.editPassword(userSharedPreference.getUserDetails().getEmail(), binding.oldPasswordEditText.getText().toString(), binding.newPasswordEditText.getText().toString());
                    editProfileViewModel.getUserLiveData().observe(this, s -> {
                        editProfileViewModel.getUserLiveData().removeObservers(this);
                        Toast.makeText(getApplicationContext(), getString(R.string.password_updated_successfully), Toast.LENGTH_SHORT).show();
                        dialogCustom.dismissDialog();
                    });
                    editProfileViewModel.getFail().observe(this, s -> {
                        editProfileViewModel.getFail().removeObservers(this);
                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                        dialogCustom.dismissDialog();
                    });

                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.the_two_password_not_match), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.please_fill_all_information), Toast.LENGTH_SHORT).show();
            }
        } else if (v == binding.selectImage) {
            SelectImageFragment selectImageFragment = SelectImageFragment.newInstance();
            selectImageFragment.show(getSupportFragmentManager(), "selectImageFragment");
        } else if (v == binding.changePhoneText) {
            startActivity(new Intent(EditProfileActivity.this, ChangePhoneActivity.class));
        } else if (v == binding.eyePassImage1) {
            passEye(binding.eyePassImage1, binding.newPasswordEditText);
        } else if (v == binding.eyePassImage11) {
            passEye(binding.eyePassImage11, binding.oldPasswordEditText);
        } else if (v == binding.repeatEyePassImage) {
            passEye(binding.repeatEyePassImage, binding.repeatPasswordEditText);
        } else if (v == binding.back) {
            onBackPressed();
        }
    }

    private void passEye(ImageView imageView, EditTextV2 editTextV2) {
        if (editTextV2.getTransformationMethod() == null) {
            imageView.setImageResource(R.drawable.ic_baseline_visibility_off_24);
            editTextV2.setTransformationMethod(new PasswordTransformationMethod());
        } else {
            imageView.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
            editTextV2.setTransformationMethod(null);
        }
        editTextV2.setSelection(editTextV2.length());
    }


    @Override
    protected void onStart() {
        super.onStart();
        Glide.with(getApplicationContext()).load(userSharedPreference.getUserDetails().getImageURL()).placeholder(R.drawable.avatar).into(binding.userImage);
        binding.phoneText.setText(userSharedPreference.getUserDetails().getPhoneNumber());
    }

    public void updateImageProfile() {
        Glide.with(getApplicationContext()).load(userSharedPreference.getUserDetails().getImageURL()).placeholder(R.drawable.avatar).into(binding.userImage);
    }


    private void uploadImage(Uri uri) {
        dialogCustom.showDialog();
        editProfileViewModel.uploadImage(uri);
        editProfileViewModel.getUrlImageLiveData().observe(this, imageUrl -> {
            editProfileViewModel.getUrlImageLiveData().removeObservers(this);
            dialogCustom.dismissDialog();
            Toast.makeText(this, getString(R.string.image_updated_successfully), Toast.LENGTH_SHORT).show();
            User user = userSharedPreference.getUserDetails();
            user.setImageURL(imageUrl);
            userSharedPreference.addUser(user);
            Glide.with(this).load(imageUrl).into(binding.userImage);
        });
        editProfileViewModel.getFail().observe(this, text -> {
            //editProfileViewModel.getFail().removeObservers(this);
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        });
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = result.getUriContent();
                uploadImage(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, getString(R.string.error) + result.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //selectImage();
            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                //ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE);
                Toast.makeText(this, getString(R.string.you_should_allow_storage_permission_to_upload_profile_picture), Toast.LENGTH_SHORT).show();
            }

        }

    }

   /* private void selectImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }*/
}