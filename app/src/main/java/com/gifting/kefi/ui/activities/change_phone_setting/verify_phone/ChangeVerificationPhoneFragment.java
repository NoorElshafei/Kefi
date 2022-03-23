package com.gifting.kefi.ui.activities.change_phone_setting.verify_phone;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.User;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.FragmentVerificationPhoneBinding;
import com.gifting.kefi.util.DialogCustom;
import com.gifting.kefi.util.Language;

public class ChangeVerificationPhoneFragment extends Fragment implements View.OnClickListener {

    private ChangeVerificationPhoneViewModel mViewModel;
    private FragmentVerificationPhoneBinding binding;
    private String phone;
    private DialogCustom dialogCustom;
    private CountDownTimer count;


    public static ChangeVerificationPhoneFragment newInstance() {
        return new ChangeVerificationPhoneFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_verification_phone, container, false);

        Language.changeBackDependsLanguage(binding.backImage, getContext().getApplicationContext());

        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this, new ChangeVerificationPhoneViewModelFactory(getActivity())).get(ChangeVerificationPhoneViewModel.class);
        dialogCustom = new DialogCustom(getContext());

        binding.continueButton.setOnClickListener(this);
        binding.back.setOnClickListener(this);
        phone = getArguments().getString("phone_text");
        binding.phoneText.setText(getContext().getString(R.string.we_will_text_you) + phone);
        mViewModel.sendVerificationCode("+2" + phone);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        mViewModel.getFailText().observe(getViewLifecycleOwner(), s -> {
            dialogCustom.dismissDialog();
            Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();

        });
        mViewModel.getCodeVerification().observe(getViewLifecycleOwner(), s -> {
            binding.firstPinView.setText(s);
        });
        mViewModel.getSuccessText().observe(getViewLifecycleOwner(), phone -> {
            dialogCustom.dismissDialog();
            UserSharedPreference userSharedPreference = new UserSharedPreference(getContext());
            User user = userSharedPreference.getUserDetails();
            user.setPhoneNumber(phone);
            userSharedPreference.addUser(user);
            Toast.makeText(getContext(), getString(R.string.phone_updated_successfully), Toast.LENGTH_SHORT).show();
            getActivity().finish();
        });

        setTimer();

    }

    @Override
    public void onClick(View v) {
        dialogCustom = new DialogCustom(getContext());
        if (v == binding.continueButton) {
            if (binding.firstPinView.getText().toString().length() == 6) {
                dialogCustom.showDialog();
                mViewModel.verifyVerificationCode(binding.firstPinView.getText().toString());
            } else {
                Toast.makeText(getContext(), getString(R.string.please_enter_valid_code), Toast.LENGTH_SHORT).show();
            }
        } else if (v == binding.sendNewCode) {
            Toast.makeText(getContext(), getString(R.string.we_will_send_you_an_new_code_in_sms), Toast.LENGTH_LONG).show();

            mViewModel.sendVerificationCode("+2" + phone);
            setTimer();
            binding.timerText.setVisibility(View.VISIBLE);
            binding.sendNewCode.setVisibility(View.GONE);
        } else if (v == binding.back) {
            getActivity().onBackPressed();
        }
    }

    public void setTimer() {
        count = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                binding.timerText.setText(getContext().getString(R.string.send_new_code) + millisUntilFinished / 1000);
            }

            public void onFinish() {
                binding.timerText.setVisibility(View.GONE);
                binding.sendNewCode.setVisibility(View.VISIBLE);

            }
        };
        count.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        count.cancel();
    }
}