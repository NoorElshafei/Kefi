package com.gifting.kefi.ui.navigation_fragments.verify_phone;

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
import androidx.navigation.Navigation;

import com.gifting.kefi.R;
import com.gifting.kefi.databinding.FragmentVerificationPhoneBinding;
import com.gifting.kefi.util.DialogCustom;
import com.gifting.kefi.util.Language;

public class VerificationPhoneFragment extends Fragment implements View.OnClickListener {

    private VerificationPhoneViewModel mViewModel;
    private FragmentVerificationPhoneBinding binding;
    private String phone;
    private DialogCustom dialogCustom;
    private CountDownTimer countDownTimer;


    public static VerificationPhoneFragment newInstance() {
        return new VerificationPhoneFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_verification_phone, container, false);
        View view = binding.getRoot();

        Language.changeBackDependsLanguage(binding.backImage, getContext());

        binding.back.setOnClickListener(this);
        binding.sendNewCode.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this, new VerficationPhoneViewModelFactory(getActivity())).get(VerificationPhoneViewModel.class);
        dialogCustom = new DialogCustom(getContext());

        binding.continueButton.setOnClickListener(this);
        phone = getArguments().getString("phone_text");
        binding.phoneText.setText(getString(R.string.we_will_text_you) + phone);


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        mViewModel.getFailText().observe(getViewLifecycleOwner(), s -> {
            dialogCustom.dismissDialog();
            Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();

        });

        mViewModel.getCodeVerification().observe(getViewLifecycleOwner(), s -> {
            binding.firstPinView.setText(s);
        });

        mViewModel.getSuccessText().observe(getViewLifecycleOwner(), credential -> {
            dialogCustom.dismissDialog();
            Bundle bundle = new Bundle();
            bundle.putString("phone_text", phone);
            bundle.putParcelable("credential", credential);
            countDownTimer.cancel();
            Navigation.findNavController(getView()).navigate(R.id.action_verificationPhoneFragment_to_registerFragment, bundle);
        });

    }

    @Override
    public void onClick(View v) {
        if (v == binding.back) {
            getActivity().onBackPressed();
        }
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

            sendVerficationCode();
            binding.timerText.setVisibility(View.VISIBLE);
            binding.sendNewCode.setVisibility(View.GONE);
        }
    }

    public void setTimer() {
        countDownTimer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                binding.timerText.setText(getContext().getString(R.string.send_new_code) + millisUntilFinished / 1000);
            }

            public void onFinish() {
                binding.timerText.setVisibility(View.GONE);
                binding.sendNewCode.setVisibility(View.VISIBLE);
            }
        };
        countDownTimer.start();
    }

    private void sendVerficationCode() {
        mViewModel.sendVerificationCode("+2" + phone);
        setTimer();
    }

    @Override
    public void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }

    @Override
    public void onStart() {
        super.onStart();
        sendVerficationCode();
    }


}