package com.gifting.kefi.ui.fragments.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.gifting.kefi.R;
import com.gifting.kefi.data.local_database.AppRoomDatabase;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.FragmentLogOutMessageBinding;
import com.gifting.kefi.ui.activities.first_container.FirstContainerActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LogOutMessageFragment extends DialogFragment implements View.OnClickListener {
    private FragmentLogOutMessageBinding binding;
    private UserSharedPreference userSharedPreference;
    private FirebaseAuth firebaseAuth;
    private AppRoomDatabase db;


    public LogOutMessageFragment() {
    }


    public static LogOutMessageFragment newInstance() {
        LogOutMessageFragment fragment = new LogOutMessageFragment();
        fragment.setStyle(android.R.style.Theme_Translucent_NoTitleBar_Fullscreen, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.backButton.setOnClickListener(this);
        binding.exitButton.setOnClickListener(this);
        binding.relativeLayout.setOnClickListener(this);
        binding.constraintLayout.setOnClickListener(this);

        db = AppRoomDatabase.getDatabase(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_log_out_message, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userSharedPreference = new UserSharedPreference(getContext());
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backButton) {
            dismiss();
        } else if (v == binding.exitButton) {
            userSharedPreference.removeData();
            firebaseAuth.signOut();

            db.clearAllTables();
            userSharedPreference.setStorageReference("");
            Intent intent = new Intent(getContext(), FirstContainerActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (v == binding.relativeLayout) {
            dismiss();
        } else if (v == binding.constraintLayout) {
        }
    }
}