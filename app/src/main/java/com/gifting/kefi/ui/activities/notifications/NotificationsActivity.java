package com.gifting.kefi.ui.activities.notifications;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.databinding.ActivityNotificationsBinding;
import com.gifting.kefi.ui.adapters.NotificationsAdapter;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.DialogCustom;
import com.gifting.kefi.util.Language;

public class NotificationsActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityNotificationsBinding binding;
    private NotificationsAdapter adapter;
    private NotificationsViewModel notificationsViewModel;
    private DialogCustom dialogCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notifications);
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        Language.changeBackDependsLanguage(binding.backImage,this);


        binding.notificationAdapter.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new NotificationsAdapter(this);
        binding.notificationAdapter.setAdapter(adapter);

        dialogCustom = new DialogCustom(this);
        dialogCustom.showDialog();
        notificationsViewModel.getAllNotifications();
        notificationsViewModel.getNotificationsRepo().observe(this, notificationModels -> {
            binding.noNotificationText.setVisibility(View.GONE);
            dialogCustom.dismissDialog();
            adapter.setNotificationModels(notificationModels);
        });

        notificationsViewModel.getFailText().observe(this,s -> {
            binding.noNotificationText.setVisibility(View.VISIBLE);
            dialogCustom.dismissDialog();
        });


        binding.option.setOnClickListener(this);
        binding.back.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.back) {
            onBackPressed();
        }
    }
}