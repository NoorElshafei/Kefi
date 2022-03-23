package com.gifting.kefi.ui.fragments.exit_bottom_sheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.gifting.kefi.R;
import com.gifting.kefi.databinding.FragmentExitAppBinding;


public class ExitAppFragment extends DialogFragment implements View.OnClickListener {
    FragmentExitAppBinding binding;


    public ExitAppFragment() {

    }

    public static ExitAppFragment newInstance() {
        ExitAppFragment exitAppFragment = new ExitAppFragment();
        exitAppFragment.setStyle(android.R.style.Theme_Translucent_NoTitleBar_Fullscreen, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        return exitAppFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_exit_app, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.backButton.setOnClickListener(this);
        binding.exitButton.setOnClickListener(this);
        binding.relativeLayout.setOnClickListener(this);
        binding.constraintLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.backButton) {
            dismiss();
        } else if (v == binding.exitButton) {
            getActivity().finishAndRemoveTask();
        } else if (v == binding.relativeLayout) {
            dismiss();
        } else if (v == binding.constraintLayout) {
        }
    }


}