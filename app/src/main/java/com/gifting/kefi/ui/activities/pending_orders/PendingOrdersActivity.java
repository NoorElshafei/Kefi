package com.gifting.kefi.ui.activities.pending_orders;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.gifting.kefi.ui.adapters.MyOrderAdapter;
import com.gifting.kefi.R;
import com.gifting.kefi.databinding.ActivityPendingOrdersBinding;
import com.gifting.kefi.util.Language;

public class PendingOrdersActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityPendingOrdersBinding binding;
    MyOrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_pending_orders);

        Language.changeBackDependsLanguage(binding.backImage,getApplicationContext());



       /* binding.cartsRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new MyOrderAdapter(this);
        binding.cartsRecycler.setAdapter(adapter);*/

        binding.back.setOnClickListener(this);
        binding.doneButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.back) {
            onBackPressed();
        } else if (v == binding.doneButton) {
            onBackPressed();
        }
    }
}