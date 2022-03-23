package com.gifting.kefi.ui.fragments.full_screen;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.gifting.kefi.R;
import com.gifting.kefi.databinding.FragmentFullScreenBinding;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FullScreenFragment extends DialogFragment {

    private FullScreenViewModel mViewModel;
    private FragmentFullScreenBinding binding;

    public static FullScreenFragment newInstance() {
        return new FullScreenFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NO_TITLE, R.style.CustomDialogTheme);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        BigImageViewer.initialize(GlideImageLoader.with(getActivity().getApplicationContext()));
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_full_screen, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FullScreenViewModel.class);
        // TODO: Use the ViewModel
        String url = getArguments().getString("PRODUCT_Picture");
        binding.mBigImage.showImage(Uri.parse(url));
    }

}