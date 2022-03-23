package com.gifting.kefi.util;

import static com.gifting.kefi.util.App.CHANNEL_ID;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.ReelVideoModel;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.ui.activities.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class ExampleService extends IntentService {


    private StorageReference storageReference;
    private NotificationManager mNotificationManager;
    private ReelVideoModel reelVideoModel;
    private NotificationCompat.Builder notification;
    private PendingIntent pendingIntent;
    public static final String TAG = "MyServiceTag";
    private String ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE";
    private UploadTask task;
    private static boolean isKilled = false;
    private PendingIntent pendingIntentContent;
    private Intent notificationIntent;


    public ExampleService() {
        super("ExampleIntentService");
        setIntentRedelivery(false);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isKilled = false;
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent stopSelf = new Intent(this, ExampleService.class);
        stopSelf.setAction(this.ACTION_STOP_SERVICE);

        pendingIntent = PendingIntent.getService(this, 0, stopSelf, PendingIntent.FLAG_CANCEL_CURRENT);

        notificationIntent = new Intent(this, MainActivity.class);
        pendingIntentContent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);


        notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Prepare Upload")
                .setContentText("")
                .setOngoing(true)
                .setContentIntent(pendingIntentContent)
                .addAction(android.R.drawable.ic_menu_close_clear_cancel, "Cancel", pendingIntent)
                .setProgress(100, 0, true)
                .setSmallIcon(android.R.drawable.stat_sys_upload);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(1, notification.build());

        } else {
            mNotificationManager.notify(1, notification.build());
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (ACTION_STOP_SERVICE.equals(intent.getAction())) {
            Log.d(TAG, "called to cancel service");
            isKilled = true;
            mNotificationManager.cancelAll();
            stopSelf();

            UserSharedPreference userSharedPreference = new UserSharedPreference(this);
            userSharedPreference.getStorageReference();
            storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(userSharedPreference.getStorageReference());

            task = storageReference.getActiveUploadTasks().get(0);
            task.cancel();

           /* notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Canceling Upload")
                    .setContentText("")
                    .setOngoing(true)
                    .setProgress(100, 0, true)
                    .setSmallIcon(android.R.drawable.stat_sys_upload);*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                stopForeground(true);
            }
            //mNotificationManager.notify(2, notification.build());

            userSharedPreference.setStorageReference("");
            Intent intent1 = new Intent("uploadingBroadcasting");
            sendBroadcast(intent1);
            return;
        }


        if (intent.getParcelableExtra("ReelVideo") != null) {
            reelVideoModel = intent.getParcelableExtra("ReelVideo");
            String storageReferenceString = intent.getStringExtra("StorageRefrence");
            Uri videoUri = intent.getParcelableExtra("VideoUri");

            storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(storageReferenceString);
            task = storageReference.putFile(videoUri);
        }


        task.addOnSuccessListener(taskSnapshot -> {
            storageReference.getDownloadUrl().addOnSuccessListener(videoUri1 -> {
                DatabaseReference idVideo = FirebaseDatabase.getInstance().getReference().child("ReelVideos").child(FirebaseAuth.getInstance().getUid()).push();
                reelVideoModel.setLink(videoUri1.toString());
                reelVideoModel.setId(idVideo.getKey());
                reelVideoModel.setTime_created(Calendar.getInstance().getTimeInMillis());
                idVideo.setValue(reelVideoModel);
                Toast.makeText(getApplicationContext(), getString(R.string.video_upload_successfully), Toast.LENGTH_SHORT).show();

                notification.setContentText("Uploading finished")
                        .setProgress(0, 0, false)
                        .setOngoing(false)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntentContent)
                        .setSmallIcon(android.R.drawable.stat_sys_upload_done);
                mNotificationManager.notify(1, notification.build());

                UserSharedPreference userSharedPreference = new UserSharedPreference(this);
                userSharedPreference.setStorageReference("");

            });
        }).addOnFailureListener(e -> {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }).addOnCanceledListener(() -> {
            task.cancel();
            //mNotificationManager.cancel(2);
            mNotificationManager.cancelAll();
            stopSelf();
        }).addOnProgressListener(snapshot -> {
            long progress = 100 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount();

            if (!isKilled) {
                notification.setContentTitle(reelVideoModel.getTitle())
                        .setContentText((int) progress + "% completed")
                        .setOngoing(true)
                        .setContentIntent(pendingIntentContent)
                        .setProgress(100, (int) progress, false);
                Log.d("dsadsadsadsa", "progress still");
                mNotificationManager.notify(1, notification.build());
            } else {
                mNotificationManager.cancel(1);
                stopSelf();
            }
        });
    }
}
