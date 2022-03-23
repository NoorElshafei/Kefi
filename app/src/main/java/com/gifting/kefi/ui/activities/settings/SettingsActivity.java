package com.gifting.kefi.ui.activities.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.gifting.kefi.R;
import com.gifting.kefi.data.local_database.AppRoomDatabase;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivitySettingsBinding;
import com.gifting.kefi.ui.activities.customer_support.CustomerSupportActivity;
import com.gifting.kefi.ui.activities.edit_profile.EditProfileActivity;
import com.gifting.kefi.ui.activities.first_container.FirstContainerActivity;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.activities.privacy.PrivacyActivity;
import com.gifting.kefi.ui.fragments.language_bottom_sheet.LanguageBottomSheet;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.Language;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySettingsBinding binding;
    private UserSharedPreference userSharedPreference;
    private FirebaseAuth firebaseAuth;
    private AppRoomDatabase db;
    private AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        Language.changeBackDependsLanguage(binding.backImage, getApplicationContext());
        Language.changeBackDependsLanguage(binding.editProfileImage2, getApplicationContext());
        Language.changeBackDependsLanguage(binding.logOutImage2, getApplicationContext());
        Language.changeBackDependsLanguage(binding.privacyImage2, getApplicationContext());
        Language.changeBackDependsLanguage(binding.customerImage2, getApplicationContext());
        Language.changeBackDependsLanguage(binding.inviteImage2, getApplicationContext());
        Language.changeBackDependsLanguage(binding.deleteMyAccountImage2, getApplicationContext());


        userSharedPreference = new UserSharedPreference(getApplicationContext());
        firebaseAuth = FirebaseAuth.getInstance();
        db = AppRoomDatabase.getDatabase(getApplicationContext());

        if (userSharedPreference.getLanguage().equals("en")) {
            binding.languageTextValue.setText("English");
        } else {
            binding.languageTextValue.setText("العربية");
        }

       /* if (userSharedPreference.getPushNotification().equals("true")) {
            binding.notificationSwitch.setImageResource(R.drawable.ic_switch_on);
            binding.notificationSwitch.setTag("on");
        } else {
            binding.notificationSwitch.setImageResource(R.drawable.ic_switch_off);
            binding.notificationSwitch.setTag("off");
        }*/

        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.customerLayout.setOnClickListener(this);
        //binding.notificationLayout1.setOnClickListener(this);
        binding.editProfileLayout.setOnClickListener(this);
        binding.deleteMyAccountLayout.setOnClickListener(this);
        binding.privacyLayout.setOnClickListener(this);
        binding.inviteLayout.setOnClickListener(this);
        //binding.notificationSwitch.setOnClickListener(this);
        binding.languageLayout.setOnClickListener(this);
        binding.englishText.setOnClickListener(this);
        binding.arabicText.setOnClickListener(this);
        binding.back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.editProfileLayout) {
            startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
        } else if (v == binding.notificationLayout) {
            startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
        } else if (v == binding.customerLayout) {
            startActivity(new Intent(getApplicationContext(), CustomerSupportActivity.class));

        } /*else if (v == binding.notificationLayout1) {
            CheckSwitch(binding.notificationSwitch);

        } */else if (v == binding.privacyLayout) {
            startActivity(new Intent(getApplicationContext(), PrivacyActivity.class));

        } else if (v == binding.languageLayout) {
            LanguageBottomSheet fragment = new LanguageBottomSheet();
            fragment.show(getSupportFragmentManager(), "TAG");

        } else if (v == binding.inviteLayout) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            final String appPackageName = getPackageName();
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Kefi google play link:" + " https://play.google.com/store/apps/details?id=" + appPackageName);
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);

        } else if (v == binding.deleteMyAccountLayout) {
            alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(getString(R.string.delete_account));
            alertDialog.setMessage(getString(R.string.do_you_really_want_todelete_account));
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    userSharedPreference.removeData();
                    firebaseAuth.signOut();
                    db.clearAllTables();
                    userSharedPreference.setStorageReference("");
                    Toast.makeText(getApplicationContext(), getString(R.string.your_account_deleted_successfully), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SettingsActivity.this, FirstContainerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            });
            alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = alertDialog.create();
            dialog.show();


        } /*else if (v == binding.notificationSwitch) {
            CheckSwitch(binding.notificationSwitch);
        } */else if (v == binding.back) {
            onBackPressed();
        }
    }

    private void CheckSwitch(ImageView imageView) {
        if (imageView.getTag().equals("on")) {
            imageView.setImageResource(R.drawable.ic_switch_off);
            imageView.setTag("off");
            FirebaseMessaging.getInstance().unsubscribeFromTopic("all");
            userSharedPreference.setPushNotification("false");

        } else if (imageView.getTag().equals("off")) {
            imageView.setImageResource(R.drawable.ic_switch_on);
            imageView.setTag("on");
            FirebaseMessaging.getInstance().subscribeToTopic("all");
            userSharedPreference.setPushNotification("true");
        }
    }
}