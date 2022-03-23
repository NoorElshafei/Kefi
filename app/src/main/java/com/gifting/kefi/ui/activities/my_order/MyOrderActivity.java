package com.gifting.kefi.ui.activities.my_order;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.OrderRequestModel;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivityMyOrderBinding;
import com.gifting.kefi.ui.activities.main.MainActivity;
import com.gifting.kefi.ui.adapters.MyOrderAdapter;
import com.gifting.kefi.util.Language;

import java.util.ArrayList;
import java.util.Collections;

public class MyOrderActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMyOrderBinding binding;
    private MyOrderAdapter adapter;
    private MyOrderViewModel myOrderViewModel;
    private ArrayList<OrderRequestModel> orderRequestModels;
    private UserSharedPreference userSharedPreference;
    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private boolean isLastPage = false;
    private boolean isFromSuccessPage = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_order);

        Language.changeBackDependsLanguage(binding.backImage, getApplicationContext());


        isFromSuccessPage = getIntent().getBooleanExtra("FromSuccessPage", false);

        myOrderViewModel = new ViewModelProvider(this).get(MyOrderViewModel.class);

        userSharedPreference = new UserSharedPreference(getApplicationContext());
        binding.cartsRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        adapter = new MyOrderAdapter(this, getSupportFragmentManager());
        binding.cartsRecycler.setAdapter(adapter);


        binding.back.setOnClickListener(this);


        binding.nestedScroll.getViewTreeObserver()
                .addOnScrollChangedListener(() -> {
                    if (!isLastPage) {
                        if (binding.nestedScroll.getChildAt(0).getBottom() <= (binding.nestedScroll.getHeight() + binding.nestedScroll.getScrollY())) {
                            //scroll view is at bottom
                            loadNextPage();
                            Log.d("noooor", "onScrollChanged: ");
                        }
                    }
                });


        loadFirstPage();

    }

    private void loadFirstPage() {

        orderRequestModels = new ArrayList<>();
        myOrderViewModel.setLoadFirstReviews();
        myOrderViewModel.getFirstReviewsLiveData().observe(this, reviewModels -> {
            binding.noOrderText.setVisibility(View.GONE);
            myOrderViewModel.getFirstReviewsLiveData().removeObservers(this);
            binding.loadProgress.setVisibility(View.GONE);


            ArrayList<OrderRequestModel> reversed = new ArrayList<>();
            reversed.addAll(reviewModels);
            Collections.reverse(reversed);

            orderRequestModels.addAll(reversed);

            adapter.addAll(reversed);


            if (reviewModels.size() == 10) adapter.addLoadingFooter();
            else isLastPage = true;

        });

        myOrderViewModel.getFailText().observe(this, s -> {
            binding.noOrderText.setVisibility(View.VISIBLE);
            binding.loadProgress.setVisibility(View.GONE);

        });


    }

    private void loadNextPage() {
        String requestId = orderRequestModels.get(orderRequestModels.size() - 1).getRequestId();
        myOrderViewModel.setLoadNextReviews(requestId);
        myOrderViewModel.getNextReviewsLiveData1().observe(this, reviewModels -> {
            myOrderViewModel.getNextReviewsLiveData1().removeObservers(this);
            adapter.removeLoadingFooter();


            ArrayList<OrderRequestModel> reversed = new ArrayList<>();
            reversed.addAll(reviewModels);
            Collections.reverse(reversed);

            orderRequestModels.addAll(reversed);

            adapter.addAll(reversed);
            //displayRecentlyOrder(orderRequestModels);
            if (reviewModels.size() == 10) adapter.addLoadingFooter();
            else isLastPage = true;

        });

    }


    public void onClick(View v) {
        if (v == binding.back) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        if (isFromSuccessPage) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            super.onBackPressed();
        }
    }
}