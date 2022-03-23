package com.gifting.kefi.ui.navigation_fragments.welcome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.gifting.kefi.R;

public class WelcomeFragment extends Fragment {

    private WelcomeViewModel mViewModel;

    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        view.findViewById(R.id.getStarted_button).setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_welcomeFragment_to_phoneFragment);
        });

        view.findViewById(R.id.signIn_text).setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_welcomeFragment_to_loginFragment);
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WelcomeViewModel.class);
        // TODO: Use the ViewModel
    }

}