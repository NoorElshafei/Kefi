package com.gifting.kefi.ui.fragments.search;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gifting.kefi.R;
import com.gifting.kefi.data.local_database.AppRoomDatabase;
import com.gifting.kefi.data.model.RecentViewedProduct;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.FragmentSearchBinding;
import com.gifting.kefi.ui.activities.main.MainActivity;
import com.gifting.kefi.ui.adapters.SearchAdapter;
import com.gifting.kefi.ui.adapters.SearchRecentAdapter;
import com.gifting.kefi.util.KeyboardCustomization;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements View.OnClickListener {

    private SearchViewModel mViewModel;
    private FragmentSearchBinding binding;
    private FlexboxLayoutManager flexboxLayoutManager;
    private SearchAdapter adapter1;
    private SearchRecentAdapter adapter;
    private AppRoomDatabase db;
    private List<RecentViewedProduct> recentViewedProducts;


    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.searchAction.setOnClickListener(this);
        binding.clearText.setOnClickListener(this);
        binding.firstRecommend.setOnClickListener(this);
        binding.secondRecommend.setOnClickListener(this);
        binding.thirdRecommend.setOnClickListener(this);
        binding.fourthRecommend.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        // TODO: Use the ViewModel

        KeyboardCustomization keyboardCustomization = new KeyboardCustomization(getActivity());
        keyboardCustomization.setupKeyboard(binding.rootConstraint);

        db = AppRoomDatabase.getDatabase(getContext());

        EventBus.getDefault().postSticky(binding);


        if (db.userDao().getAllRecentSingle().size() == 0) {
            binding.recentlyRecyclerView.setVisibility(View.GONE);
            binding.recentLayout.setVisibility(View.GONE);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.recentlyRecyclerView.setLayoutManager(layoutManager);
        adapter = new SearchRecentAdapter(getContext(), recentViewedProducts);
        binding.recentlyRecyclerView.setAdapter(adapter);

        flexboxLayoutManager = new FlexboxLayoutManager(getContext(), FlexDirection.ROW, FlexWrap.WRAP);
        flexboxLayoutManager.setJustifyContent(JustifyContent.SPACE_BETWEEN);
        binding.searchRecycler.setLayoutManager(flexboxLayoutManager);
        adapter1 = new SearchAdapter(getContext(), new ArrayList<>());
        binding.searchRecycler.setAdapter(adapter1);


        binding.searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(binding.searchEditText.getText().toString());
                return false;
            }
            return false;
        });
        binding.searchEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                binding.noItemText.setVisibility(View.GONE);

                binding.loadProgress.setVisibility(View.VISIBLE);
                String searchTerm = s.toString();

                if (searchTerm.trim().isEmpty()) {
                    binding.searchRecycler.setVisibility(View.GONE);
                    binding.layoutRecent.setVisibility(View.VISIBLE);
                    adapter1.removeAllData();
                    adapter1.notifyDataSetChanged();
                    binding.loadProgress.setVisibility(View.GONE);
                    binding.noItemText.setVisibility(View.GONE);

                } else {
                    performSearch(searchTerm);
                    binding.searchRecycler.setVisibility(View.VISIBLE);
                    binding.layoutRecent.setVisibility(View.GONE);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        binding.searchEditText.requestFocus();

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(binding.searchEditText, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public void onStart() {
        super.onStart();
        recentViewedProducts = db.userDao().getAllRecentSingle();
        adapter.setData(recentViewedProducts);
        EventBus.getDefault().register(getContext());
/*        ((MainActivity) getActivity()).getBinding().reelText.setVisibility(View.GONE);
        ((MainActivity) getActivity()).getBinding().reel2.setVisibility(View.GONE);
        ((MainActivity) getActivity()).getBinding().reel1.setVisibility(View.GONE);*/
        ((MainActivity) getActivity()).getBinding().bottomViewNavigation.setVisibility(View.GONE);

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(getContext());
        // mViewModel.getDataSnapshotLiveData().removeObservers(getViewLifecycleOwner());
        //.. mViewModel.getNoItemText().removeObservers(getViewLifecycleOwner());

/*        ((MainActivity) getActivity()).getBinding().reelText.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).getBinding().reel2.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).getBinding().reel1.setVisibility(View.VISIBLE);*/
        ((MainActivity) getActivity()).getBinding().bottomViewNavigation.setVisibility(View.VISIBLE);
    }

    private void performSearch(String searchTerm) {
        UserSharedPreference userSharedPreference = new UserSharedPreference(getContext());
        mViewModel.setLoadSearchProducts(searchTerm, userSharedPreference.getLanguage());
        mViewModel.getDataSnapshotLiveData().observe(getViewLifecycleOwner(), products -> {
            mViewModel.getDataSnapshotLiveData().removeObservers(getViewLifecycleOwner());
            binding.noItemText.setVisibility(View.GONE);
            binding.loadProgress.setVisibility(View.GONE);
            adapter1.setData(products);
        });
        mViewModel.getNoItemText().observe(getViewLifecycleOwner(), text -> {
            binding.loadProgress.setVisibility(View.GONE);
            adapter1.removeAllData();
            adapter1.notifyDataSetChanged();
            if (!binding.searchEditText.getText().toString().isEmpty())
                binding.noItemText.setVisibility(View.VISIBLE);
            // Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();

        });
    }

    @Override
    public void onClick(View v) {
        if (v == binding.searchAction) {
            performSearch(binding.searchEditText.getText().toString());
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            binding.searchEditText.clearFocus();
            if (imm != null) {
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
            }
        } else if (v == binding.clearText) {
            db.userDao().deleteAllRecent();
            recentViewedProducts = db.userDao().getAllRecentSingle();
            adapter.setData(recentViewedProducts);
        } else if (v == binding.firstRecommend) {
            binding.searchEditText.setText(binding.firstRecommend.getText().toString());
        } else if (v == binding.secondRecommend) {
            binding.searchEditText.setText(binding.secondRecommend.getText().toString());
        } else if (v == binding.thirdRecommend) {
            binding.searchEditText.setText(binding.thirdRecommend.getText().toString());
        } else if (v == binding.fourthRecommend) {
            binding.searchEditText.setText(binding.fourthRecommend.getText().toString());
        }
    }

    public FragmentSearchBinding getBinding() {
        return binding;
    }
}