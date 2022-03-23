package com.gifting.kefi.ui.fragments.store;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gifting.kefi.R;
import com.gifting.kefi.data.local_database.AppRoomDatabase;
import com.gifting.kefi.data.model.Products;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.FragmentStoreBinding;
import com.gifting.kefi.ui.activities.main.MainActivity;
import com.gifting.kefi.ui.activities.my_carts.MyCartsActivity;
import com.gifting.kefi.ui.adapters.StoreProductAdapter;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.ui.fragments.search.SearchFragment;
import com.gifting.kefi.util.CheckInternet;
import com.gifting.kefi.util.Language;
import com.gifting.kefi.util.PaginationScrollListener;
import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class StoreFragment extends Fragment implements View.OnClickListener {

    private StoreViewModel mViewModel;
    private FragmentStoreBinding binding;
    private FlexboxLayoutManager flexboxLayoutManager;
    private StoreProductAdapter adapter;
    private ArrayList<Products> productsArrayList;
    private UserSharedPreference userSharedPreference;



    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private boolean isLoading = false;

    // If current page is the last page (Pagination will stop after this page load)
    private boolean isLastPage = false;
    private AppRoomDatabase db;


    public static StoreFragment newInstance() {
        return new StoreFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        userSharedPreference = new UserSharedPreference(getContext().getApplicationContext());
        if (container != null) {
            container.removeAllViews();
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false);

        Language.changeBackDependsLanguage1(binding.cart, getContext().getApplicationContext());

        View view = binding.getRoot();
        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StoreViewModel.class);
        // TODO: Use the ViewModel
        Glide.with(this).load(R.drawable.loading).into(binding.loadProgress);
        binding.swipeContainer.setColorSchemeResources(R.color.bink, R.color.bink, R.color.bink);


        binding.swipeContainer.setOnRefreshListener(() -> loadFirstPage());

        checkInternet();

        db = AppRoomDatabase.getDatabase(getContext().getApplicationContext());
        db.userDao().getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(roomCarts1 -> {

            binding.cartNumber.setText(roomCarts1.size() + "");
        });
        flexboxLayoutManager = new FlexboxLayoutManager(getContext().getApplicationContext(), FlexDirection.ROW, FlexWrap.WRAP);
        flexboxLayoutManager.setJustifyContent(JustifyContent.SPACE_BETWEEN);
        binding.recyclerViewStor.setLayoutManager(flexboxLayoutManager);
        adapter = new StoreProductAdapter(getContext());
        binding.recyclerViewStor.setAdapter(adapter);


        binding.recyclerViewStor.addOnScrollListener(new PaginationScrollListener(flexboxLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                //Increment page index to load the next one
                loadNextPage();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        loadFirstPage();

    }

    private void loadFirstPage() {
        isLastPage=false;
        adapter.clear();
        productsArrayList = new ArrayList<>();
        mViewModel.setLoadFirstProducts(userSharedPreference.getLanguage());
        mViewModel.getDataSnapshotLiveData().observe(getViewLifecycleOwner(), productsList -> {
            mViewModel.getDataSnapshotLiveData().removeObservers(getViewLifecycleOwner());
            binding.loadProgress.setVisibility(View.GONE);
            binding.loadingText.setVisibility(View.GONE);
            productsArrayList.addAll(productsList);
            adapter.addAll(productsArrayList);
            if (productsList.size() == 10) adapter.addLoadingFooter();
            else isLastPage = true;
            binding.swipeContainer.setRefreshing(false);

        });

    }

    private void loadNextPage() {
        String idProduct = productsArrayList.get(productsArrayList.size() - 1).getId();
        mViewModel.setLoadNextProducts(idProduct, userSharedPreference.getLanguage());
        mViewModel.getDataSnapshotLiveData1().observe(getViewLifecycleOwner(), productsList -> {
            mViewModel.getDataSnapshotLiveData1().removeObservers(getViewLifecycleOwner());
            adapter.removeLoadingFooter(); // 2
            isLoading = false; // 3
            productsArrayList.addAll(productsList);
            adapter.addAll(productsList);
            if (productsList.size() == 10) adapter.addLoadingFooter();
            else isLastPage = true;

        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.option.setOnClickListener(this);
        binding.cartsLayout.setOnClickListener(this);

        binding.searchEditText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null)
                        .replace(R.id.container, SearchFragment.newInstance(), "search").commit();
                return true;
            }
            return false;
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).getBinding().storeImage.setImageResource(R.drawable.ic_store_active);
        ((MainActivity) getActivity()).getBinding().storeText.setTextColor(getResources().getColor(R.color.bink));

        String[] myName = userSharedPreference.getUserDetails().getName().split(" ");
        binding.title.setText(getString(R.string.hi) + " " + myName[0].toUpperCase().charAt(0) + myName[0].substring(1).toLowerCase() + " " + getString(R.string.comma));
    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity) getActivity()).getBinding().storeImage.setImageResource(R.drawable.ic_store_un_active);
        ((MainActivity) getActivity()).getBinding().storeText.setTextColor(getResources().getColor(R.color.bink_lighter));

    }

    @Override
    public void onClick(View v) {
        if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getChildFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.cartsLayout) {
            startActivity(new Intent(getContext(), MyCartsActivity.class));

        }
    }

    private void checkInternet() {
        CheckInternet.hasInternetConnection().subscribe((hasInternet) -> {
            if (!hasInternet&& getContext() != null) {
                Toast.makeText(getContext(), getString(R.string.please_check_your_internet), Toast.LENGTH_LONG).show();
            }
        });
    }


}