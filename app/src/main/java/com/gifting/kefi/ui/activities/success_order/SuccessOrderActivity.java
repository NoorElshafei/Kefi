package com.gifting.kefi.ui.activities.success_order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.gifting.kefi.R;
import com.gifting.kefi.databinding.ActivitySuccessOrderBinding;
import com.gifting.kefi.ui.activities.main.MainActivity;
import com.gifting.kefi.ui.activities.my_order.MyOrderActivity;
import com.gifting.kefi.util.Language;

public class SuccessOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySuccessOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_success_order);



        binding.myOrderLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == binding.myOrderLayout) {
            Intent intent = new Intent(this, MyOrderActivity.class);
            intent.putExtra("FromSuccessPage", true);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}