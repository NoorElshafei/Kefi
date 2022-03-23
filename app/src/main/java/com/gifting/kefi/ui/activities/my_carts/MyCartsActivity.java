package com.gifting.kefi.ui.activities.my_carts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.local_database.AppRoomDatabase;
import com.gifting.kefi.databinding.ActivityMyCartsBinding;
import com.gifting.kefi.ui.activities.checkout.CheckOutActivity;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.adapters.MyCartAdapter;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.Language;
import com.gifting.kefi.util.TotalCartInterFace;

public class MyCartsActivity extends AppCompatActivity implements View.OnClickListener, TotalCartInterFace {
    private ActivityMyCartsBinding binding;
    private MyCartAdapter adapter;
    private AppRoomDatabase db;
    private int total = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_carts);

        Language.changeBackDependsLanguage(binding.backImage,getApplicationContext());
        Language.changeBackDependsLanguage(binding.checkoutImage,getApplicationContext());



        binding.cartsRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        adapter = new MyCartAdapter(this, this);
        binding.cartsRecycler.setAdapter(adapter);

        db = AppRoomDatabase.getDatabase(this);


        binding.checkOutButton.setOnClickListener(this);
        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == binding.checkOutButton) {
            if (db.userDao().getAllSingle().size() > 0) {
                startActivity(new Intent(MyCartsActivity.this, CheckOutActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.carts_is_empty_please_add_at_least_one_product_to_cart), Toast.LENGTH_LONG).show();
            }


        } else if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.notificationLayout) {
            startActivity(new Intent(this, NotificationsActivity.class));
        } else if (v == binding.back) {
            onBackPressed();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.clear();
        if (db.userDao().getAllSingle() != null && db.userDao().getAllSingle().size() != 0) {
            binding.noCartText.setVisibility(View.GONE);
            adapter.addAll(db.userDao().getAllSingle());
        } else
            binding.noCartText.setVisibility(View.VISIBLE);

    }

    @Override
    public void addToTatal(int price) {
        total += price;
        binding.totalCarts.setText(total + getString(R.string.l_e));
    }

    @Override
    public void minusfromTatal(int price) {
        total -= price;
        binding.totalCarts.setText(total + getString(R.string.l_e));
        if(total==0){
            binding.noCartText.setVisibility(View.VISIBLE);
        }
    }

}