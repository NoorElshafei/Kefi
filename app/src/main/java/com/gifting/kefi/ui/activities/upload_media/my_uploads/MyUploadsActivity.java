package com.gifting.kefi.ui.activities.upload_media.my_uploads;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.local_database.AppRoomDatabase;
import com.gifting.kefi.data.model.ReelVideoModel;
import com.gifting.kefi.data.model.UploadDetailsModel;
import com.gifting.kefi.data.model.UploadsModel;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivityMyUploadsBinding;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.adapters.UploadsAdapter;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MyUploadsActivity extends AppCompatActivity implements View.OnClickListener {
    private StorageReference storageReference;
    private ActivityMyUploadsBinding binding;
    private UserSharedPreference userSharedPreference;
    private AppRoomDatabase db;
    private List<UploadsModel> uploadsModels;
    private List<ReelVideoModel> reelVideoModels;
    private ArrayList<UploadDetailsModel> uploadDetailsModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_uploads);

        userSharedPreference = new UserSharedPreference(getApplicationContext());
        db = AppRoomDatabase.getDatabase(getApplicationContext());

        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);


        binding.uploadRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        UploadsAdapter adapter = new UploadsAdapter(this);
        binding.uploadRecycler.setAdapter(adapter);


        uploadsModels = db.userDao().getAllUploads();
        reelVideoModels = db.userDao().getAllReel();
        uploadDetailsModels = new ArrayList<>();

        int i = 0;
        for (UploadsModel uploadsModel : uploadsModels) {
            storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(uploadsModel.getVideoFolder());
            if (storageReference.getActiveUploadTasks().size() > 0) {
                uploadDetailsModels.add(new UploadDetailsModel(storageReference.getActiveUploadTasks().get(0), storageReference, reelVideoModels.get(i), uploadsModel));
                i++;
            }
        }

        adapter.setUploadTasks(uploadDetailsModels);



        /*if (tasks.size() > 0) {
            // Get the task monitoring the upload
            UploadTask task = tasks.get(0);

            // Add new listeners to the task using an Activity scope

        }*/


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

    @Override
    public void onBackPressed() {
        for (UploadDetailsModel uploadDetailsModel : uploadDetailsModels) {
            uploadDetailsModel.getUploadTask().pause();
        }
        super.onBackPressed();
    }
}