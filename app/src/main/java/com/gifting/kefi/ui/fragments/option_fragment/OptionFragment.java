package com.gifting.kefi.ui.fragments.option_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.gifting.kefi.R;
import com.gifting.kefi.databinding.FragmentOptionBinding;
import com.gifting.kefi.ui.activities.about.AboutActivity;
import com.gifting.kefi.ui.activities.calendar_container.CalendarActivity;
import com.gifting.kefi.ui.activities.rate_us.RateUsActivity;
import com.gifting.kefi.ui.activities.settings.SettingsActivity;
import com.gifting.kefi.ui.activities.settings.SettingsCartsActivity;
import com.gifting.kefi.ui.fragments.logout.LogOutMessageFragment;
import com.gifting.kefi.util.Language;


public class OptionFragment extends DialogFragment implements View.OnClickListener {
    FragmentOptionBinding binding;

    public OptionFragment() {
        // Required empty public constructor
    }

    public static OptionFragment newInstance() {
        OptionFragment fragment = new OptionFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.MyTheme);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.menuLayout.setOnClickListener(this);
        binding.menuCard.setOnClickListener(this);
        binding.setting.setOnClickListener(this);
        //binding.rateUsLayout.setOnClickListener(this);
        binding.aboutLayout.setOnClickListener(this);
        binding.logOutLayout.setOnClickListener(this);
        //binding.calendarLayout.setOnClickListener(this);
        binding.cartSettingLayout.setOnClickListener(this);
        //binding.logOutLayout.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmente
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_option, container, false);
        View view = binding.getRoot();
        //dialog = new dialog(context,android.R.style.Theme_Translucent_NoTitleBar);
        //getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        Language.changeBackDependsLanguage(binding.settingsImage2,getContext());
        Language.changeBackDependsLanguage(binding.cartSettingImage2,getContext());
        //Language.changeBackDependsLanguage(binding.rateUsImage2,getContext());
        Language.changeBackDependsLanguage(binding.aboutImage2,getContext());
        //Language.changeBackDependsLanguage(binding.calendarImage2,getContext());
        Language.changeBackDependsLanguage(binding.logOutImage2,getContext());

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == binding.menuLayout) {
            dismiss();
        } else if (v == binding.menuCard) {

        } else if (v == binding.setting) {
            startActivity(new Intent(getContext(), SettingsActivity.class));
            dismiss();
        } /*else if (v == binding.rateUsLayout) {
            startActivity(new Intent(getContext(), RateUsActivity.class));
            dismiss();
        }*/ else if (v == binding.aboutLayout) {
            startActivity(new Intent(getContext(), AboutActivity.class));
            dismiss();
        }/* else if (v == binding.calendarLayout) {
            startActivity(new Intent(getContext(), CalendarActivity.class));
            dismiss();
        }*/ else if (v == binding.cartSettingLayout) {
            startActivity(new Intent(getContext(), SettingsCartsActivity.class));
            dismiss();
        } else if (v == binding.logOutLayout) {
            LogOutMessageFragment logOutMessageFragment = LogOutMessageFragment.newInstance();
            logOutMessageFragment.show(getFragmentManager(), "logout");
            dismiss();
        }
    }
}