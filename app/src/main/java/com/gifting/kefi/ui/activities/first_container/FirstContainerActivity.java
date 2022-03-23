package com.gifting.kefi.ui.activities.first_container;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gifting.kefi.R;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.util.CustomLanguage;
import com.google.firebase.messaging.FirebaseMessaging;

public class FirstContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_container);

        CustomLanguage.checkLanguage(this);

        UserSharedPreference userSharedPreference = new UserSharedPreference(this);

        if (userSharedPreference.getPushNotification().equals("first_time")) {
            FirebaseMessaging.getInstance().subscribeToTopic("all");
            userSharedPreference.setPushNotification("true");
        }

    }
}