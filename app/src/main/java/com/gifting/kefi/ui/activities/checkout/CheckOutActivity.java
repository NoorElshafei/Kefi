package com.gifting.kefi.ui.activities.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.local_database.AppRoomDatabase;
import com.gifting.kefi.data.model.OrderRequestModel;
import com.gifting.kefi.data.model.RoomCarts;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivityCheckOutBinding;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.activities.payment_method_checkout.PaymentMethodCheckoutActivity;
import com.gifting.kefi.ui.activities.shipping_address_checkout.ShippingAddressCheckoutActivity;
import com.gifting.kefi.ui.activities.success_order.SuccessOrderActivity;
import com.gifting.kefi.ui.adapters.CheckoutOrderAdapter;
import com.gifting.kefi.ui.adapters.ReorderAdapter;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.DialogCustom;
import com.gifting.kefi.util.Language;
import com.gifting.kefi.util.TotalCartInterFace;

import java.util.List;

public class CheckOutActivity extends AppCompatActivity implements View.OnClickListener, TotalCartInterFace {

    private ActivityCheckOutBinding binding;
    private CheckoutOrderAdapter adapter;
    private ReorderAdapter reorderAdapter;
    private AppRoomDatabase db;
    private int total = 0;
    private UserSharedPreference userSharedPreference;
    private List<RoomCarts> roomCarts;
    private CheckOutViewModel checkOutViewModel;
    private boolean isFromMyOrder = true;
    private DialogCustom dialogCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_out);

        Language.changeBackDependsLanguage(binding.backImage, getApplicationContext());
        Language.changeBackDependsLanguage(binding.addressArrowImage, getApplicationContext());
        Language.changeBackDependsLanguage(binding.paymentArrowImage, getApplicationContext());
        Language.changeBackDependsLanguage(binding.imageView21, getApplicationContext());
        Language.changeBackDependsLanguage(binding.placeOrderArrowImage, getApplicationContext());


        checkOutViewModel = new ViewModelProvider(this).get(CheckOutViewModel.class);

        binding.placeOrderButton.setOnClickListener(this);

        userSharedPreference = new UserSharedPreference(getApplicationContext());


        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);
        binding.addressLayout.setOnClickListener(this);
        binding.paymentLayout.setOnClickListener(this);
        binding.cardView2.setOnClickListener(this);
        binding.promoLayout.setOnClickListener(this);
        binding.imageView21.setOnClickListener(this);

        binding.itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));


        if (getIntent().getParcelableArrayListExtra("ORDER_REQUEST") != null) {
            reorderAdapter = new ReorderAdapter(this, this);
            binding.itemsRecyclerView.setAdapter(reorderAdapter);
            roomCarts = getIntent().getParcelableArrayListExtra("ORDER_REQUEST");
            reorderAdapter.addAll(roomCarts);
            isFromMyOrder = true;

        } else {
            adapter = new CheckoutOrderAdapter(this, this);
            binding.itemsRecyclerView.setAdapter(adapter);
            db = AppRoomDatabase.getDatabase(getApplicationContext());
            roomCarts = db.userDao().getAllSingle();
            adapter.addAll(roomCarts);
            isFromMyOrder = false;
        }


    }


    @Override
    public void onClick(View v) {
        dialogCustom = new DialogCustom(this);
        if (v == binding.placeOrderButton) {

            if (!userSharedPreference.getSelectedAddress().equals("default")) {

                dialogCustom.showDialog();
                OrderRequestModel orderRequestModel = new OrderRequestModel("",
                        userSharedPreference.getUserDetails().getId(),
                        userSharedPreference.getUserDetails().getName(),
                        userSharedPreference.getSelectedPaymentMethod(),
                        "unpaid",
                        userSharedPreference.getUserDetails().getPhoneNumber(),
                        total + "", System.currentTimeMillis() + "",
                        "inProgress",
                        roomCarts,
                        userSharedPreference.getSelectedAddress()
                );
                checkOutViewModel.saveRequestInFirebase(orderRequestModel);
                checkOutViewModel.getUserLiveData().observe(this, orderRequestModel1 -> {
                    dialogCustom.dismissDialog();
                    if (!isFromMyOrder) {
                        db.userDao().deleteAll();
                    }
                    startActivity(new Intent(getApplicationContext(), SuccessOrderActivity.class));
                });
                checkOutViewModel.getFail().observe(this, failText -> {
                    Toast.makeText(this, failText, Toast.LENGTH_SHORT).show();
                });

            } else {
                Toast.makeText(this, getString(R.string.please_add_address), Toast.LENGTH_SHORT).show();

            }


        } else if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.notificationLayout) {
            startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));

        } else if (v == binding.addressLayout) {
            startActivity(new Intent(getApplicationContext(), ShippingAddressCheckoutActivity.class));
        } else if (v == binding.paymentLayout) {
            startActivity(new Intent(getApplicationContext(), PaymentMethodCheckoutActivity.class));
        } else if (v == binding.back) {
            onBackPressed();
        }
        if (v == binding.promoLayout) {
            Toast.makeText(this, getString(R.string.not_available_now), Toast.LENGTH_SHORT).show();
        }
        if (v == binding.imageView21) {
            Toast.makeText(this, getString(R.string.not_available_now), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (userSharedPreference.getSelectedAddress().equals("default")) {
            binding.shippingValue.setText(getString(R.string.please_add_your_address_checkout_page));
            binding.paymentMethodText.setText(getString(R.string.cash_on_delivery_checkout_page));
        } else {
            binding.shippingValue.setText(userSharedPreference.getSelectedAddress());
        }
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
    }
}