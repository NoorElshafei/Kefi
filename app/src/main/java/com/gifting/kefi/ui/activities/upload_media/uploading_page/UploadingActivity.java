package com.gifting.kefi.ui.activities.upload_media.uploading_page;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.ReelVideoModel;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivityUploadingBinding;
import com.gifting.kefi.ui.activities.main.MainActivity;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.Language;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

public class UploadingActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityUploadingBinding binding;
    private StorageReference mStorageRef;
    private ReelVideoModel reelVideoModel;
    private int splashTime = 0;
    private ConnectivityManager cm;
    private UploadTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_uploading);

        Language.changeBackDependsLanguage(binding.backImage,getApplicationContext());


        registerReceiver(mMessageReceiver, new IntentFilter("uploadingBroadcasting"));


        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        NetworkCapabilities nc = cm.getNetworkCapabilities(cm.getActiveNetwork());
        int downSpeed = nc.getLinkDownstreamBandwidthKbps();


        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);

        String storageReference = getIntent().getStringExtra("StorageReference");


        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(storageReference);
        reelVideoModel = getIntent().getParcelableExtra("ReelVideo");
        // Find all UploadTasks under this StorageReference (in this example, there should be one)
        List<UploadTask> tasks = mStorageRef.getActiveUploadTasks();
        if (tasks.size() > 0) {
            // Get the task monitoring the upload
            task = tasks.get(0);

            // Add new listeners to the task using an Activity scope
            task.addOnSuccessListener(this, taskSnapshot -> {
                mStorageRef.getDownloadUrl().addOnSuccessListener(videoUri1 -> {
                 /*   DatabaseReference idVideo = FirebaseDatabase.getInstance().getReference().child("ReelVideos").child(FirebaseAuth.getInstance().getUid()).push();
                    reelVideoModel.setLink(videoUri1.toString());
                    reelVideoModel.setId(idVideo.getKey());
                    idVideo.setValue(reelVideoModel);*/
                    UserSharedPreference userSharedPreference = new UserSharedPreference(getApplicationContext());
                    userSharedPreference.setStorageReference("");
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    //Toast.makeText(getApplicationContext(),getString( R.string.video_upload_successfully), Toast.LENGTH_SHORT).show();

                });
            }).addOnProgressListener(snapshot -> {
                long progress = 100 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount();
                binding.sizeFileText.setText(getString(R.string.file_size) + (snapshot.getTotalByteCount() / 1000000) + getString(R.string.migabyte));

                int upSpeed = nc.getLinkUpstreamBandwidthKbps();
                int estimatedTime = (int) (snapshot.getTotalByteCount() - snapshot.getBytesTransferred()) / upSpeed;
                if (estimatedTime < 60)
                    binding.estimatedTimeText.setText(getString(R.string.estimated_time) + estimatedTime + getString(R.string.seconds));
                else if (estimatedTime > 60)
                    binding.estimatedTimeText.setText(getString(R.string.estimated_time) + estimatedTime / 60 + getString(R.string.mimute));
                else if (estimatedTime > (60 * 60))
                    binding.estimatedTimeText.setText(getString(R.string.estimated_time) + estimatedTime / (60 * 60) + getString(R.string.hours));

                binding.progressBar2.setProgress((int) progress);
                binding.progressText.setText((int) progress + "");


                //setProgressAnimate(binding.progressBar2, (int) progress);
                // setProgressAnimateText(binding.progressText, (int) progress);


            });
        }

       /* while (true) {

            sleep(100);
            if (splashTime < 2000) {
                binding.uploadingText.setText("Uploading.");
            } else if (splashTime >= 2000 && splashTime < 4000) {
                binding.uploadingText.setText("Uploading..");
            } else if (splashTime >= 4000) {
                binding.uploadingText.setText("Uploading...");
            } else if (splashTime >= 5000) {
                binding.uploadingText.setText("Uploading....");
            }
            splashTime = splashTime + 100;
        }*/
    }

    private void setProgressAnimate(ProgressBar pb, int progressTo) {
        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", pb.getProgress(), progressTo);
        if (progressTo - pb.getProgress() < 5) {
            animation.setDuration(5500);
        } else if (progressTo - pb.getProgress() < 10) {
            animation.setDuration(10500);
        } else if (progressTo - pb.getProgress() < 30) {
            animation.setDuration(15500);
        } else if (progressTo - pb.getProgress() < 40) {
            animation.setDuration(25500);
        } else if (progressTo - pb.getProgress() > 40) {
            animation.setDuration(40500);
        }

        animation.setAutoCancel(true);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (pb.getProgress() == 100) {
                   /* Intent intent = new Intent(UploadingActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(UploadingActivity.this, "Video update Successfully", Toast.LENGTH_SHORT).show();*/
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setProgressAnimateText(TextView textView, int textTo) {
        int from = Integer.parseInt(textView.getText().toString());
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(from, textTo);// here you set the range, from 0 to "count" value
        animator.addUpdateListener(animation -> textView.setText(String.valueOf(animation.getAnimatedValue())));
        if (textTo - from < 5) {
            animator.setDuration(5000);
        } else if (textTo - from < 10) {
            animator.setDuration(10000);
        } else if (textTo - from < 30) {
            animator.setDuration(15000);
        } else if (textTo - from < 40) {
            animator.setDuration(25000);
        } else if (textTo - from > 40) {
            animator.setDuration(40000);
        }
        animator.start();
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

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent serviceIntent = getIntent().getParcelableExtra("Service");
            stopService(serviceIntent);
            if(task!=null) {
                task.cancel();
            }
            finish();
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMessageReceiver);

    }
}