package com.gifting.kefi.ui.navigation_fragments.forget_password;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.gifting.kefi.R;
import com.gifting.kefi.databinding.FragmentForgetPasswordBinding;
import com.gifting.kefi.util.CustomizeKeyboard;
import com.gifting.kefi.util.DialogCustom;
import com.gifting.kefi.util.KeyboardCustomization;

public class ForgetPasswordFragment extends Fragment implements View.OnClickListener {

    private ForgetPasswordViewModel mViewModel;
    private FragmentForgetPasswordBinding binding;
    private DialogCustom dialogCustom;

    public static ForgetPasswordFragment newInstance() {
        return new ForgetPasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forget_password, container, false);

        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.sendMailButton.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ForgetPasswordViewModel.class);
        // TODO: Use the ViewModel

        KeyboardCustomization keyboardCustomization = new KeyboardCustomization(getActivity());
        keyboardCustomization.setupKeyboard(binding.scrollView);
        CustomizeKeyboard.editKeyboardEmail(binding.emailEditText, binding.scrollView);

        mViewModel.getSuccessText().observe(getViewLifecycleOwner(), s -> {
            dialogCustom.dismissDialog();
            Toast.makeText(getContext(), getString(R.string.email_sent_successfully), Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        });
        mViewModel.getFail().observe(getViewLifecycleOwner(), s -> {
            dialogCustom.dismissDialog();
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onClick(View v) {
        dialogCustom = new DialogCustom(getContext());
        if (v == binding.sendMailButton) {
            if (!binding.emailEditText.getText().toString().equals("")) {

                mViewModel.sendEmailReset(binding.emailEditText.getText().toString());

            }
            else {
                Toast.makeText(getContext(), getString(R.string.please_enter_your_email), Toast.LENGTH_SHORT).show();
            }
        }


    }
}