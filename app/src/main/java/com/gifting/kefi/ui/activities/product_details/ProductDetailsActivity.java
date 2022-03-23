package com.gifting.kefi.ui.activities.product_details;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gifting.kefi.R;
import com.gifting.kefi.data.local_database.AppRoomDatabase;
import com.gifting.kefi.data.model.Products;
import com.gifting.kefi.data.model.RecentViewedProduct;
import com.gifting.kefi.data.model.ReviewModel;
import com.gifting.kefi.data.model.RoomCarts;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivityProductDetailsBinding;
import com.gifting.kefi.ui.activities.my_carts.MyCartsActivity;
import com.gifting.kefi.ui.adapters.ProductReviewAdapter;
import com.gifting.kefi.ui.fragments.add_comment_product.AddCommentFragment;
import com.gifting.kefi.ui.fragments.full_screen.FullScreenFragment;
import com.gifting.kefi.util.CheckInternet;
import com.gifting.kefi.util.DialogCustom;
import com.gifting.kefi.util.Language;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Collections;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityProductDetailsBinding binding;
    private Products products;
    private ProductDetailsViewModel productDetailsViewModel;
    private ArrayList<ReviewModel> reviewModelArrayList;
    private ArrayList<ReviewModel> notSortedReviewModelArrayList;
    private ProductReviewAdapter adapter;
    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private boolean isLoadingMain = true;
    private boolean isLoadingFromFirst = false;
    // If current page is the last page (Pagination will stop after this page load)
    private boolean isLastPage = false;
    private AppRoomDatabase db;
    private ArrayList<RecentViewedProduct> recentViewedProducts;
    private DialogCustom dialogCustom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details);
        productDetailsViewModel = new ViewModelProvider(this).get(ProductDetailsViewModel.class);

        Language.changeBackDependsLanguage(binding.backImage, getApplicationContext());
        Language.changeBackDependsLanguage1(binding.cart, getApplicationContext());


        db = AppRoomDatabase.getDatabase(getApplicationContext());
        recentViewedProducts = new ArrayList<>();
        recentViewedProducts.addAll(db.userDao().getAllRecentSingle());


        db.userDao().getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(roomCarts1 -> {
            //Toast.makeText(this, "cart size is :  " + roomCarts1.size(), Toast.LENGTH_SHORT).show();
            binding.cartNumber.setText(roomCarts1.size() + "");
        });


        binding.reviews.setOnClickListener(this);
        binding.details.setOnClickListener(this);
        binding.back.setOnClickListener(this);
        binding.cartLayout.setOnClickListener(this);
        binding.addToCart.setOnClickListener(this);
        binding.evaluateButton.setOnClickListener(this);
        binding.itemImage.setOnClickListener(this);


        if (getIntent().getParcelableExtra("product_details") != null) {
            products = getIntent().getParcelableExtra("product_details");
            setDataUI(products);
        } else {
            dialogCustom = new DialogCustom(this);
            dialogCustom.showDialog();
            String productId = getIntent().getStringExtra("product_id");
            UserSharedPreference userSharedPreference = new UserSharedPreference(this);
            productDetailsViewModel.getProductDetails(productId, userSharedPreference.getLanguage());
            productDetailsViewModel.getProductsMutableLiveData().observe(this, products1 -> {
                dialogCustom.dismissDialog();
                products = products1;
                setDataUI(products);
            });
            productDetailsViewModel.getFailText().observe(this, s -> {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

            });
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        binding.reviewRecycler.setLayoutManager(linearLayoutManager);
        adapter = new ProductReviewAdapter(this);
        binding.reviewRecycler.setAdapter(adapter);


        binding.nestedScroll.getViewTreeObserver()
                .addOnScrollChangedListener(() -> {
                    if (binding.detailsLayout.getVisibility() == View.GONE && !isLastPage && isLoadingFromFirst) {
                        if (binding.nestedScroll.getChildAt(0).getBottom() <= (binding.nestedScroll.getHeight() + binding.nestedScroll.getScrollY())) {
                            //scroll view is at bottom
                            loadNextPage();
                            Log.d("noooor", "onScrollChanged: ");
                        }
                    }
                });
    }

    private void loadFirstPage() {
        adapter.clear();
        reviewModelArrayList = new ArrayList<>();
        notSortedReviewModelArrayList = new ArrayList<>();
        productDetailsViewModel.setLoadFirstReviews(products.getId());
        productDetailsViewModel.getFirstReviewsLiveData().observe(this, reviewModels -> {
            productDetailsViewModel.getFirstReviewsLiveData().removeObservers(this);
            binding.noCommentText.setVisibility(View.GONE);
            binding.loadProgress.setVisibility(View.GONE);




          /*  ArrayList<ReviewModel> reversed = new ArrayList<>();
            reversed.addAll(reviewModels);
            Collections.reverse(reversed);*/
            reviewModelArrayList.addAll(reviewModels);
            notSortedReviewModelArrayList.addAll(reviewModels);
            Collections.sort(reviewModelArrayList, (o1, o2) -> Long.compare(o2.getReviewCreatedAt(), o1.getReviewCreatedAt()));

            adapter.clear();
            adapter.addAll(reviewModelArrayList);
            isLoadingFromFirst = true;
            if (reviewModels.size() == 10) adapter.addLoadingFooter();
            else isLastPage = true;

        });
        productDetailsViewModel.getFailText().observe(this, s -> {
            binding.noCommentText.setVisibility(View.VISIBLE);
            binding.loadProgress.setVisibility(View.GONE);
        });

    }

    private void loadNextPage() {
        String userId = notSortedReviewModelArrayList.get(notSortedReviewModelArrayList.size() - 1).getUserID();
        productDetailsViewModel.setLoadNextReviews(userId, products.getId());
        productDetailsViewModel.getNextReviewsLiveData1().observe(this, reviewModels -> {
            productDetailsViewModel.getNextReviewsLiveData1().removeObservers(this);
            adapter.removeLoadingFooter(); // 2

           /* ArrayList<ReviewModel> reversed = new ArrayList<>();
            reversed.addAll(reviewModels);
            Collections.reverse(reversed);
            */

            reviewModelArrayList.addAll(reviewModels);
            notSortedReviewModelArrayList.addAll(reviewModels);
            Collections.sort(reviewModelArrayList, (o1, o2) -> Long.compare(o2.getReviewCreatedAt(), o1.getReviewCreatedAt()));

            adapter.clear();
            adapter.addAll(reviewModelArrayList);


            if (reviewModels.size() == 10) adapter.addLoadingFooter();
            else isLastPage = true;

        });
        productDetailsViewModel.getFailText().observe(this, s -> {
            isLoadingFromFirst = false;
            adapter.removeLoadingFooter();

        });
    }

    private void setDataUI(Products products) {
        binding.nameItem.setText(products.getName());
        binding.priceItem.setText(products.getPrice().toString() + getString(R.string.l_e));
        binding.rateItem.setText(products.getRate().toString());
        binding.itemName1.setText(products.getBrand());
        binding.condition.setText(products.getCondition());
        binding.categoryText.setText(products.getCategory());
        binding.serialText.setText(products.getSerial());
        binding.material.setText(products.getMaterial());
        binding.description.setText(products.getDescription());
        Glide.with(getApplicationContext()).load(products.getPicture()).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                binding.loadProgress2.setVisibility(View.GONE);
                return false;
            }
        }).into(binding.itemImage);


        if (!db.userDao().findRecentById(products.getId())) {
            RecentViewedProduct recentViewedProduct = new RecentViewedProduct(products.getId(), products.getName(), products.getBrand(), products.getCategory(), products.getCondition(), products.getMaterial(), products.getPicture(), products.getPrice(), products.getRate(), products.getSearch(), products.getDescription(), products.getDescription(), products.getInventoryNumber());
            if (recentViewedProducts.size() >= 5) {
                db.userDao().delete(recentViewedProducts.get(0));
            }
            db.userDao().insertItem(recentViewedProduct);
        } else {
            RecentViewedProduct recentViewedProduct = db.userDao().getRecentProduct(products.getId());
            db.userDao().delete(recentViewedProduct);
            recentViewedProduct.setXid(null);
            db.userDao().insertItem(recentViewedProduct);
        }


    }

    public void updateComment() {
        isLoadingFromFirst = false;
        isLastPage = false;
        loadFirstPage();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.reviews) {
            binding.loadProgress.setVisibility(View.VISIBLE);
            adapter.clear();
            checkInternet();
            loadFirstPage();
            binding.reviews.setCardElevation(5);
            binding.details.setCardElevation(0);
            binding.detailsLayout.setVisibility(View.GONE);
            binding.buttonsLayout.setVisibility(View.GONE);
            binding.reviewRecycler.setVisibility(View.VISIBLE);

        } else if (v == binding.details) {
            adapter.clear();
            isLastPage = false;
            isLoadingFromFirst = false;
            binding.reviews.setCardElevation(0);
            binding.details.setCardElevation(5);
            binding.detailsLayout.setVisibility(View.VISIBLE);
            binding.buttonsLayout.setVisibility(View.VISIBLE);
            binding.reviewRecycler.setVisibility(View.GONE);
            binding.loadProgress.setVisibility(View.INVISIBLE);
            binding.noCommentText.setVisibility(View.GONE);

        } else if (v == binding.back) {
            onBackPressed();
        } else if (v == binding.cartLayout) {
            startActivity(new Intent(getApplicationContext(), MyCartsActivity.class));
        } else if (v == binding.addToCart) {
            if (products.getInventoryNumber() > 0) {
                if (!db.userDao().findById(products.getId())) {
                    RoomCarts roomCarts = new RoomCarts(products.getId(), products.getName(), products.getBrand(), products.getCategory(),
                            products.getCondition(), products.getMaterial(), products.getPicture(), products.getPrice(), products.getRate(),
                            products.getSearch(), products.getSerial(), products.getDescription(), products.getInventoryNumber());

                    db.userDao().insertItem(roomCarts);
                    Toast.makeText(getApplicationContext(), getString(R.string.added_to_cart), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.product_added_to_cart_already), Toast.LENGTH_SHORT).show();

                }
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.product_is_out_of_stock), Toast.LENGTH_SHORT).show();
            }

        } else if (v == binding.evaluateButton) {
            Bundle bundle = new Bundle();
            AddCommentFragment addCommentFragment = AddCommentFragment.newInstance();
            bundle.putString("PRODUCT_ID", products.getId());
            addCommentFragment.setArguments(bundle);
            addCommentFragment.show(getSupportFragmentManager(), "addCommentFragment");
        } else if (v == binding.itemImage) {
            Bundle bundle = new Bundle();
            FullScreenFragment fullScreenFragment = FullScreenFragment.newInstance();
            bundle.putString("PRODUCT_Picture", products.getPicture());
            fullScreenFragment.setArguments(bundle);
            fullScreenFragment.show(getSupportFragmentManager(), "addCommentFragment");
        }
    }

    private void checkInternet() {
        CheckInternet.hasInternetConnection().subscribe((hasInternet) -> {
            if (!hasInternet) {
                Toast.makeText(getApplicationContext(), getString(R.string.internet_may_be_unstable_or_not_connected), Toast.LENGTH_LONG).show();

            }
        });
    }
}


