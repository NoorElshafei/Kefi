package com.gifting.kefi.ui.activities.upload_media;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.gifting.kefi.R;
import com.gifting.kefi.databinding.ActivityIntroUploadBinding;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.activities.upload_media.upload_form.UploadVideoFormActivity;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.Language;

public class IntroUploadActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityIntroUploadBinding binding;
    private static final int RESULT_LOAD_VIDEO = 101;
    private static final int accessFineStorage = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro_upload);

        Language.changeBackDependsLanguage(binding.backImage,getApplicationContext());


        onClickListener();
        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);

    }

    private void onClickListener() {
        binding.addButton.setOnClickListener(v -> {
            requestImage();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_VIDEO && resultCode == RESULT_OK && data.getData() != null) {
            Uri fileUri = data.getData();
           /* Cursor returnCursor =
                    getContentResolver().query(fileUri, null, null, null, null);
            int sizeIndex =returnCursor.getColumnIndex(OpenableColumns.SIZE);
            long size= returnCursor.getLong(sizeIndex);*/
            long size=Long.parseLong(getRealSizeFromUri(getApplicationContext(),fileUri));
            if ((size / 1000000) > 200) {
                Toast.makeText(getApplicationContext(), getString(R.string.the_file_must_not_be_larger_than_200_MB), Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(getApplicationContext(), UploadVideoFormActivity.class);
                intent.putExtra("VideoUri", fileUri);
                startActivity(intent);
            }
        } else {
            //Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private String getRealSizeFromUri(Context context, Uri uri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Audio.Media.SIZE };
            cursor = context.getContentResolver().query(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == accessFineStorage) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.you_should_allow_storage_permission_to_upload_profile_picture), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void requestImage() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                selectImage();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, accessFineStorage);
            }
        } else {
            selectImage();
        }
    }


    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("video/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
        startActivityForResult(intent, RESULT_LOAD_VIDEO);


         /*Intent intent = new Intent();
        intent.setType("video/mp4");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), RESULT_LOAD_VIDEO);*/

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
        }

    }
}