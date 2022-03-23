package com.gifting.kefi.ui.activities.upload_media.upload_form;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.gifting.kefi.R;
import com.gifting.kefi.data.local_database.AppRoomDatabase;
import com.gifting.kefi.data.model.ReelVideoModel;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivityUploadVideoFormBinding;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.activities.upload_media.uploading_page.UploadingActivity;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.DialogCustom;
import com.gifting.kefi.util.ExampleService;
import com.gifting.kefi.util.Language;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class UploadVideoFormActivity extends AppCompatActivity implements View.OnClickListener {
    private Uri videoUri;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase firebaseDatabase;
    private ReelVideoModel reelVideoModel;
    private ActivityUploadVideoFormBinding binding;
    private String duration;
    private static final int RESULT_LOAD_Picture = 1;
    private static final int accessFineStorage = 1;
    private Uri picUri;
    private UserSharedPreference userSharedPreference;
    private ArrayList<String> selectedTagsList;
    private ArrayList<String> allTagsList;
    private AppRoomDatabase db;
    private DialogCustom dialogCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_upload_video_form);

        Language.changeBackDependsLanguage(binding.backImage,this);


        userSharedPreference = new UserSharedPreference(getApplicationContext());
        db = AppRoomDatabase.getDatabase(getApplicationContext());

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);


        if (getIntent().getParcelableExtra("VideoUri") != null) {
            videoUri = getIntent().getParcelableExtra("VideoUri");
        }

        getVideoDuration();

        onClickListener();

        selectedTagsList = new ArrayList<>();

       // binding.multiSelection.setItems(getTagsItems());


/*
        binding.multiSelection.setOnItemSelectedListener(new MultiSelectionSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, boolean isSelected, int position) {
                if (isSelected) {
                    selectedTagsList.add(allTagsList.get(position));
                    Log.d("sadsasad", "onItemSelected: added " + binding.multiSelection.getSelectedItems());
                } else {
                    int unSelectedPosition = selectedTagsList.indexOf(allTagsList.get(position));
                    selectedTagsList.remove(unSelectedPosition);
                    Log.d("sadsasad", "onItemSelected: deleted " + binding.multiSelection.getSelectedItems());

                }
            }

            @Override
            public void onSelectionCleared() {
                selectedTagsList.clear();
            }
        });
*/

    }

    private List getTagsItems() {
        allTagsList = new ArrayList<>();
        allTagsList.add("hair");
        allTagsList.add("face");
        allTagsList.add("body");
        allTagsList.add("kids");
        allTagsList.add("hair loss");
        allTagsList.add("hair damage");
        allTagsList.add("dandruff");
        allTagsList.add("eyes");
        allTagsList.add("dark circles");
        allTagsList.add("fine lines");
        allTagsList.add("puffy eyes");
        allTagsList.add("lips");
        allTagsList.add("chapped lips");
        allTagsList.add("cracked corner");
        allTagsList.add("sudden swollen");
        allTagsList.add("sunburned");
        allTagsList.add("dry skin");
        allTagsList.add("oily skin");
        allTagsList.add("mixed skin");
        allTagsList.add("normal skin");
        allTagsList.add("foot and hand");
        allTagsList.add("knee and elbow");
        allTagsList.add("nails");
        allTagsList.add("sensitive area");
        allTagsList.add("skin care");
        allTagsList.add("hair care");
        allTagsList.add("diaper area");
        allTagsList.add("bikini area");
        allTagsList.add("under_arm");
        Collections.sort(allTagsList);
        return allTagsList;
    }

    private void onClickListener() {
        binding.addThumbnail.setOnClickListener(v -> {
            requestImage();
        });

        binding.linearLayout189.setOnClickListener(v -> {
            dialogCustom = new DialogCustom(this);
            if (picUri != null) {
                if (!binding.titleEditText.getText().toString().equals("")) {
                    if (!(selectedTagsList.size() == 0)) {
                        if (!binding.descriptionEditText.getText().toString().equals("")) {
                            if (userSharedPreference.getStorageReference().equals("")) {
                                dialogCustom.showDialog();
                                dialogCustom.getDialog().setCancelable(false);
                                uploadImage();
                            } else {
                                dialogCustom.dismissDialog();
                                Toast.makeText(getApplicationContext(), getString(R.string.please_wait_until_recent_video_uploaded), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.add_your_description), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),getString( R.string.add_your_tags), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),getString( R.string.add_your_title), Toast.LENGTH_SHORT).show();
                }
            } else
                Toast.makeText(getApplicationContext(), getString( R.string.add_your_thumbnail), Toast.LENGTH_SHORT).show();
        });
    }


    private void getVideoDuration() {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        //use one of overloaded setDataSource() functions to set your data source
        retriever.setDataSource(this, videoUri);
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long timeInMilliSec = Long.parseLong(time);
        long minutes = (timeInMilliSec / 1000) / 60;
        long seconds = (timeInMilliSec / 1000) % 60;
        duration = (minutes < 10 ? "0" : "") + minutes + ":" + (seconds == 0 ? "00" : ((seconds < 10 ? "0" : "") + seconds));
        Log.d("dfsfdsds", "getVideoDuration: " + minutes + ":" + seconds);
        retriever.release();
    }

    private void uploadVideo(Uri picUri1) {
        if (videoUri != null) {
            String videoName = UUID.randomUUID().toString();
            StorageReference videoFolder = storageReference.child("videos/" + videoName);


            //DatabaseReference idVideo = databaseReference.child("ReelVideos").child(FirebaseAuth.getInstance().getUid()).push();

            //if (userSharedPreference.getStorageReference().equals("")) {
            userSharedPreference.setStorageReference(videoFolder.toString());


            reelVideoModel = new ReelVideoModel(binding.descriptionEditText.getText().toString(), duration,
                    "", picUri1.toString(), "", binding.titleEditText.getText().toString(),
                    selectedTagsList.toString(), userSharedPreference.getUserDetails().getName(),
                    userSharedPreference.getUserDetails().getImageURL(), Calendar.getInstance().getTimeInMillis(), userSharedPreference.getUserDetails().getId(),"pending");


            Intent serviceIntent = new Intent(this, ExampleService.class);
            serviceIntent.putExtra("StorageRefrence", videoFolder.toString());
            serviceIntent.putExtra("VideoUri", videoUri);
            serviceIntent.putExtra("ReelVideo", reelVideoModel);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ContextCompat.startForegroundService(this, serviceIntent);
            } else {
                startService(serviceIntent);
            }


            Intent intent = new Intent(this, UploadingActivity.class);
            intent.putExtra("StorageReference", videoFolder.toString());
            intent.putExtra("ReelVideo", reelVideoModel);
            intent.putExtra("Service", serviceIntent);
            startActivity(intent);
            finishAndRemoveTask();
            dialogCustom.dismissDialog();

            /*} else {
                dialogCustom.dismissDialog();
                Toast.makeText(this, "please wait until recent video uploaded", Toast.LENGTH_SHORT).show();
            }*/

        } else {
            Toast.makeText(getApplicationContext(), getString( R.string.please_fill_all_information), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_Picture && resultCode == RESULT_OK && data != null && data.getData() != null) {
            picUri = data.getData();
            //uploadImage();
        }
    }

    private void uploadImage() {
        String imageName = UUID.randomUUID().toString();
        StorageReference imageFolder = storageReference.child("videos_thumbnail/" + imageName);
        imageFolder.putFile(picUri).addOnSuccessListener(taskSnapshot -> imageFolder.getDownloadUrl().addOnSuccessListener(this::uploadVideo));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == accessFineStorage) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                Toast.makeText(getApplicationContext(), getString( R.string.you_should_allow_storage_permission_to_upload_profile_picture), Toast.LENGTH_SHORT).show();
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
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
        startActivityForResult(intent, RESULT_LOAD_Picture);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.notificationLayout) {
            startActivity(new Intent(this, NotificationsActivity.class));
        } else if (v == binding.back) {
            onBackPressed();
        }
    }
}