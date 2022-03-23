package com.gifting.kefi.ui.activities.finished_order;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.ui.adapters.FinishedOrderAdapter;
import com.gifting.kefi.R;
import com.gifting.kefi.databinding.ActivityFinishedOrdersBinding;
import com.gifting.kefi.util.Language;

public class FinishedOrdersActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityFinishedOrdersBinding binding;
    FinishedOrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_finished_orders);

        Language.changeBackDependsLanguage(binding.backImage,getApplicationContext());


        binding.cartsRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        adapter = new FinishedOrderAdapter(this);
        binding.cartsRecycler.setAdapter(adapter);

        binding.back.setOnClickListener(this);
        binding.doneButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == binding.back) {
            onBackPressed();
        } else if(v == binding.doneButton){
            onBackPressed();
        }
    }
}