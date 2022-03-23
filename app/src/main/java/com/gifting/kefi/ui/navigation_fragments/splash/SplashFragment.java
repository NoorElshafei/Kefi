package com.gifting.kefi.ui.navigation_fragments.splash;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.NotificationModel;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.FragmentSplashBinding;
import com.gifting.kefi.ui.activities.main.MainActivity;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.util.DialogCustom;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

import java.util.Calendar;

public class SplashFragment extends Fragment {

    private SplashViewModel mViewModel;
    private UserSharedPreference userSharedPreference;
    private DialogCustom dialogCustom;
    private FragmentSplashBinding binding;
    private int MY_REQUEST_CODE = 1;

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false);

        userSharedPreference = new UserSharedPreference(getContext().getApplicationContext());

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SplashViewModel.class);




        /*
        float scale = getActivity().getApplicationContext().getResources().getDisplayMetrics().density;
        Toast.makeText(getContext(), "scale : " + scale, Toast.LENGTH_LONG).show();
        */

        binding.continueButton.setOnClickListener(v -> {
            AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(getContext().getApplicationContext());

            // Returns an intent object that you use to check for an update.
            Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

            // Checks that the platform will allow the specified type of update.
            appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        // This example applies an immediate update. To apply a flexible update
                        // instead, pass in AppUpdateType.FLEXIBLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(getContext().getApplicationContext().getString(R.string.update_app))
                            .setMessage(getContext().getApplicationContext().getString(R.string.you_should_update_app))
                            .setIcon(getContext().getApplicationContext().getDrawable(R.drawable.ic_baseline_update_24))
                            .setPositiveButton(getContext().getApplicationContext().getString(R.string.update), (dialog, whichButton) -> {
                                try {
                                    appUpdateManager.startUpdateFlowForResult(
                                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                                            appUpdateInfo,
                                            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                                            AppUpdateType.IMMEDIATE,
                                            // The current activity making the update request.
                                            getActivity(),
                                            // Include a request code to later monitor this update request.
                                            MY_REQUEST_CODE);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }

                            })
                            .setNegativeButton(getContext().getApplicationContext().getString(R.string.cancel), (dialog, which) -> {
                                dialog.dismiss();
                            }).show();
                } else {
                    nextCheck(v);
                }
            }).addOnFailureListener(command -> {
                Log.d("sadsadsadas", "onActivityCreated: " + command.getMessage());
                nextCheck(v);
            });
        });


    }


    private void nextCheck(View v) {
        if (userSharedPreference.getIsLogin()) {

            if (getActivity().getIntent().getStringExtra("message") != null) {

                dialogCustom = new DialogCustom(getContext());
                dialogCustom.showDialog();

                NotificationModel notificationModel = new NotificationModel(Calendar.getInstance().getTimeInMillis(),
                        getActivity().getIntent().getStringExtra("message"),
                        getActivity().getIntent().getStringExtra("table_name"),
                        getActivity().getIntent().getStringExtra("id_table"), "");
                mViewModel.setNotificationInFirebase(notificationModel);
                mViewModel.getSuccessMessage().observe(getViewLifecycleOwner(), s -> {
                    dialogCustom.dismissDialog();
                    Intent intent = new Intent(getContext().getApplicationContext(), NotificationsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });

            } else {
                Intent intent = new Intent(getContext().getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

        } else {
            Navigation.findNavController(v).navigate(R.id.action_splashFragment_to_welcomeFragment);
        }


    }


}