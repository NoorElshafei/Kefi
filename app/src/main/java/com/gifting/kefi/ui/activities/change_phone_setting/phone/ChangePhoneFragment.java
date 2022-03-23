package com.gifting.kefi.ui.activities.change_phone_setting.phone;

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
import androidx.navigation.Navigation;

import com.gifting.kefi.R;
import com.gifting.kefi.databinding.FragmentPhoneBinding;
import com.gifting.kefi.util.Language;


public class ChangePhoneFragment extends Fragment implements View.OnClickListener {

    private ChangePhoneViewModel mViewModel;
    private FragmentPhoneBinding binding;

    public static ChangePhoneFragment newInstance() {
        return new ChangePhoneFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_phone, container, false);
        binding.sendMeButton.setOnClickListener(this);
        binding.back.setOnClickListener(this);

        Language.changeBackDependsLanguage(binding.backImage,getContext().getApplicationContext());

        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ChangePhoneViewModel.class);
        // TODO: Use the ViewModel

    }

    @Override
    public void onClick(View v) {
        if (v == binding.back) {
            getActivity().onBackPressed();
        }
        if (v == binding.sendMeButton) {
            if (validatePhoneNumber(binding.phoneEditText.getText().toString())) {
                Bundle bundle = new Bundle();
                bundle.putString("phone_text", binding.phoneEditText.getText().toString());
                Navigation.findNavController(v).navigate(R.id.action_changePhoneFragment_to_changeVerificationPhoneFragment, bundle);
            } else {
                Toast.makeText(getContext().getApplicationContext(), getString(R.string.please_enter_valid_phone_number), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validatePhoneNumber(String phone) {
        return phone.length() == 11;
    }
}