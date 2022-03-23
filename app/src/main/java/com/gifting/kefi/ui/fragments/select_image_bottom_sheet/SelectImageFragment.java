package com.gifting.kefi.ui.fragments.select_image_bottom_sheet;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.User;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.SelectImageFragmentBinding;
import com.gifting.kefi.ui.activities.edit_profile.EditProfileActivity;
import com.gifting.kefi.ui.activities.edit_profile.EditProfileViewModel;
import com.gifting.kefi.util.DialogCustom;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SelectImageFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private EditProfileViewModel editProfileViewModel;
    private SelectImageFragmentBinding binding;
    private DialogCustom dialogCustom;
    private int accessFineStorage = 1;
    private UserSharedPreference userSharedPreference;

    public static SelectImageFragment newInstance() {
        return new SelectImageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);


        userSharedPreference = new UserSharedPreference(getContext());

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.select_image_fragment, container, false);
        binding.selectPhotoButton.setOnClickListener(this);
        binding.deletePhotoButton.setOnClickListener(this);
        return binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editProfileViewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        // TODO: Use the ViewModel
        if(userSharedPreference.getUserDetails().getImageURL().equals("default"))
        {
            binding.deletePhotoButton.setVisibility(View.GONE);
        }
        else {
            binding.deletePhotoButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        dialogCustom = new DialogCustom(getContext());
        if (v == binding.selectPhotoButton){
            requestImage();
            dismiss();
        }else if(v == binding.deletePhotoButton){
            dialogCustom.showDialog();
            editProfileViewModel.deleteImage();
            editProfileViewModel.getSuccessDeleteText().observe(this, successDeleteImage -> {
                editProfileViewModel.getSuccessDeleteText().removeObservers(this);
                dialogCustom.dismissDialog();
                Toast.makeText(getContext(), R.string.image_deleted_successfully, Toast.LENGTH_SHORT).show();
                User user = userSharedPreference.getUserDetails();
                user.setImageURL("default");
                userSharedPreference.addUser(user);
                dismiss();
                ((EditProfileActivity)getActivity()).updateImageProfile();
            });
        }
    }

    private void requestImage() {
        //if (Build.VERSION.SDK_INT >= 23) {
         /*   if (getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
               // selectImage();
            } else {
                //shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE);
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, accessFineStorage);
            }*/
      //  } else {
      //      selectImage();
      //  }
    }

   /* private void selectImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(getActivity());
    }*/


    @Override
            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /*if (requestCode == accessFineStorage) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                Toast.makeText(getContext(), getString(R.string.you_should_allow_storage_permission_to_upload_profile_picture), Toast.LENGTH_SHORT).show();
            }

        }*/
    }
}