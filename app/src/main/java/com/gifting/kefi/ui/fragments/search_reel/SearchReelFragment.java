package com.gifting.kefi.ui.fragments.search_reel;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.ReelVideoModel;
import com.gifting.kefi.databinding.SearchReelFragmentBinding;
import com.gifting.kefi.ui.activities.main.MainActivity;
import com.gifting.kefi.ui.adapters.ReelAdapter;
import com.gifting.kefi.ui.fragments.reel.ReelViewModel;
import com.gifting.kefi.util.KeyboardCustomization;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class SearchReelFragment extends Fragment {

    private SearchReelViewModel mViewModel;
    private ReelViewModel reelVideoModel;
    private SearchReelFragmentBinding binding;
    private ReelAdapter adapter;
    private ArrayList<ReelVideoModel> reelVideoModels;
    private ArrayList<String> listIds;
    private String searchTermGlobal;


    public static SearchReelFragment newInstance() {
        return new SearchReelFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_reel_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchReelViewModel.class);
        reelVideoModel = new ViewModelProvider(this).get(ReelViewModel.class);

        KeyboardCustomization keyboardCustomization = new KeyboardCustomization(getActivity());
        keyboardCustomization.setupKeyboard(binding.rootConstraint);

        reelVideoModels = getArguments().getParcelableArrayList("ReelList");

        binding.reelRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter = new ReelAdapter(getContext(), this);
        binding.reelRecyclerView.setAdapter(adapter);


        binding.searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(binding.searchEditText.getText().toString());
                hideKeyboard(getActivity());
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

                    adapter.removeAllData();
                    binding.loadProgress.setVisibility(View.GONE);
                    binding.noItemText.setVisibility(View.GONE);

                } else {
                    performSearch(searchTerm);
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

    private void performSearch(String searchTerm) {
        searchTermGlobal = searchTerm;
        if (!searchTerm.equals("")) {
            ArrayList<ReelVideoModel> reelVideoModels1 = new ArrayList<>();
            if (reelVideoModels != null && reelVideoModels.size() != 0) {
                for (ReelVideoModel reelVideoModel : reelVideoModels) {
                    if (reelVideoModel.getTitle().contains(searchTerm)) {
                        reelVideoModels1.add(reelVideoModel);
                    }
                }
            } else {
                getFollowingIds();
            }
            if (reelVideoModels1.size() == 0)
                binding.noItemText.setVisibility(View.VISIBLE);

            adapter.setReelAdapters(reelVideoModels1);
        }

    }


    @Override
    public void onStart() {
        super.onStart();
       /* ((MainActivity) getActivity()).getBinding().reelText.setVisibility(View.GONE);
        ((MainActivity) getActivity()).getBinding().reel2.setVisibility(View.GONE);
        ((MainActivity) getActivity()).getBinding().reel1.setVisibility(View.GONE);*/
        ((MainActivity) getActivity()).getBinding().bottomViewNavigation.setVisibility(View.GONE);

    }

    @Override
    public void onStop() {
        super.onStop();
      /*  ((MainActivity) getActivity()).getBinding().reelText.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).getBinding().reel2.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).getBinding().reel1.setVisibility(View.VISIBLE);*/
        ((MainActivity) getActivity()).getBinding().bottomViewNavigation.setVisibility(View.VISIBLE);
    }


    private void getFollowingIds() {
        reelVideoModel.getUserFollowingIds(FirebaseAuth.getInstance().getUid());
        reelVideoModel.getFollowingNumberLiveData().observe(getViewLifecycleOwner(), strings -> {
            reelVideoModel.getFollowingNumberLiveData().removeObservers(getViewLifecycleOwner());
            listIds = strings;

            getPostsOfUsers(listIds);

        });
        reelVideoModel.getFailText().observe(getViewLifecycleOwner(), s -> {
            reelVideoModel.getFailText().removeObservers(getViewLifecycleOwner());
            Toast.makeText(getContext(), getString(R.string.follow_blummers_to_see_theirs_reels), Toast.LENGTH_SHORT).show();
            binding.loadingLayout.setVisibility(View.GONE);
        });
    }

    private void getPostsOfUsers(ArrayList<String> listIds) {
        adapter.removeAllData();
        reelVideoModel.getPostsOfUsers(listIds);
        reelVideoModel.getArrayListMutableLiveData().observe(getViewLifecycleOwner(), reelVideoModels -> {
            reelVideoModel.getArrayListMutableLiveData().removeObservers(getViewLifecycleOwner());
            this.reelVideoModels = reelVideoModels;
            binding.noItemText.setVisibility(View.GONE);
            performSearch(searchTermGlobal);

        });
        reelVideoModel.getFailText().observe(getViewLifecycleOwner(), s -> {
            reelVideoModel.getFailText().removeObservers(getViewLifecycleOwner());

            Toast.makeText(getContext(), getString(R.string.no_videos_exist_now), Toast.LENGTH_SHORT).show();
            binding.loadingLayout.setVisibility(View.GONE);

        });
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}