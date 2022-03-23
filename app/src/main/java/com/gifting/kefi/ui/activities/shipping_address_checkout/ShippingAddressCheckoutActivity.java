package com.gifting.kefi.ui.activities.shipping_address_checkout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.databinding.ActivityShippingAddressBinding;
import com.gifting.kefi.ui.activities.shipping_address_edit.EditShippingAddressActivity;
import com.gifting.kefi.ui.adapters.MyAddressCheckOutAdapter;
import com.gifting.kefi.util.Language;

public class ShippingAddressCheckoutActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityShippingAddressBinding binding;
    private MyAddressCheckOutAdapter adapter;
    private ShippingAddressCheckoutViewModel shippingAddressViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_shipping_address);

        Language.changeBackDependsLanguage(binding.backImage,getApplicationContext());
        Language.changeBackDependsLanguage(binding.imageView21,getApplicationContext());



        shippingAddressViewModel = new ViewModelProvider(this).get(ShippingAddressCheckoutViewModel.class);


        binding.addressRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        adapter = new MyAddressCheckOutAdapter(this, this);
        binding.addressRecyclerView.setAdapter(adapter);

        binding.newAddressLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);
        binding.relativeLayout6.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        shippingAddressViewModel.setAllAddresses();
        shippingAddressViewModel.getDataSnapshotLiveData().observe(this, addressModels -> {
            shippingAddressViewModel.getDataSnapshotLiveData().removeObservers(this);
            binding.loadProgress.setVisibility(View.GONE);
            adapter.clear();
            if (addressModels.size() == 0) {
                binding.noAddressText.setVisibility(View.VISIBLE);
            } else {
                binding.noAddressText.setVisibility(View.GONE);
            }
            adapter.addAll(addressModels);

        });
    }

    @Override
    public void onClick(View v) {
        if (v == binding.newAddressLayout) {
            startActivity(new Intent(ShippingAddressCheckoutActivity.this, EditShippingAddressActivity.class));
        } else if (v == binding.back) {
            onBackPressed();

        }
    }
}
