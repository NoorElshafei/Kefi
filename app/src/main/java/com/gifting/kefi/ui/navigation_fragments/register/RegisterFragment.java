package com.gifting.kefi.ui.navigation_fragments.register;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
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
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.FragmentRegisterBinding;
import com.gifting.kefi.ui.fragments.birth_date_dialog.BirthDateFragment;
import com.gifting.kefi.ui.fragments.birth_date_dialog.DateInterface;
import com.gifting.kefi.util.CustomizeKeyboard;
import com.gifting.kefi.util.DialogCustom;
import com.gifting.kefi.util.KeyboardCustomization;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class RegisterFragment extends Fragment implements View.OnClickListener, DateInterface {

    private RegisterViewModel mViewModel;
    private FragmentRegisterBinding binding;
    private boolean passIsVisible = false;
    boolean checkPrivacy = true;
    private DialogCustom dialogCustom;
    private FirebaseAuth firebaseAuth;
    private Calendar calendar;
    private UserSharedPreference sharedPreference;


    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        sharedPreference = new UserSharedPreference(getContext());

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.birthDateLayout.setOnClickListener(this);
        binding.register.setOnClickListener(this);
        binding.alreadyHaveAccount.setOnClickListener(this);
        binding.eyePassImage.setOnClickListener(this);
        binding.checkBoxPrivacy.setOnClickListener(this);
        binding.privacyText.setOnClickListener(this);

        KeyboardCustomization keyboardCustomization = new KeyboardCustomization(getActivity());
        keyboardCustomization.setupKeyboard(binding.scrollView);

        CustomizeKeyboard.editKeyboardName(binding.nameEditText, binding.scrollView);
        CustomizeKeyboard.editKeyboardName(binding.lastNameEditText, binding.scrollView);
        CustomizeKeyboard.editKeyboardEmail(binding.emailEditText, binding.scrollView);
        CustomizeKeyboard.editKeyboardPassword(binding.passwordEditText, binding.scrollView, getView(), getActivity());

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onClick(View v) {
        dialogCustom = new DialogCustom(getContext());
        if (v == binding.birthDateLayout) {
            // hideKeyboard(getActivity());
            BirthDateFragment birthDateFragment = BirthDateFragment.newInstance(this);
            birthDateFragment.show(getChildFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.register) {
            mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
            register(binding.nameEditText.getText().toString(), binding.lastNameEditText.getText().toString(), binding.emailEditText.getText().toString(), binding.passwordEditText.getText().toString(), binding.birthDate.getText().toString());
        } else if (v == binding.eyePassImage) {
            if (passIsVisible) {
                binding.eyePassImage.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                binding.passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
                passIsVisible = false;
            } else {
                binding.eyePassImage.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                binding.passwordEditText.setTransformationMethod(null);
                passIsVisible = true;
            }
            binding.passwordEditText.setSelection(binding.passwordEditText.length());
        } else if (v == binding.checkBoxPrivacy) {
            if (checkPrivacy) {
                binding.checkBoxPrivacy.setImageResource(R.drawable.ic_check_box);
                checkPrivacy = false;
            } else {
                binding.checkBoxPrivacy.setImageResource(R.drawable.square_stroke_bink);
                checkPrivacy = true;
            }
        } else if (v == binding.privacyText) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/1hndibOii9ZhkkMUQ_4YNgF91-yZcOjiO/view?usp=sharing"));
            startActivity(browserIntent);
        }/* else if (v == binding.alreadyHaveAccount) {
            Navigation.findNavController(getView()).navigate(R.id.action_registerFragment_to_loginFragment);
        }*/
    }


    private void register(String firstName, String lastName, String email, String password, String birthDate) {
        dialogCustom.showDialog();
        dialogCustom.getDialog().setCancelable(false);
        if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !password.isEmpty() && !birthDate.equals("birth date") && calendar != null) {
            if (!checkPrivacy) {
                firstName = firstName.replaceAll("\\s", "");
                lastName = lastName.replaceAll("\\s", "");
                email = email.replaceAll("\\s", "");

                mViewModel.register(firstName + " " + lastName, email, password, calendar.getTimeInMillis());

                mViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
                    dialogCustom.dismissDialog();
                    if (user != null) {
                        sharedPreference.addUser(user);
                        sharedPreference.setIsLogin(true);
                        Navigation.findNavController(getView()).navigate(R.id.action_registerFragment_to_mainActivity);
                    }
                });

                mViewModel.getFail().observe(getViewLifecycleOwner(), failText -> {
                    dialogCustom.dismissDialog();
                    Toast.makeText(getContext(), failText, Toast.LENGTH_SHORT).show();
                });

            } else {
                Toast.makeText(getContext(), getString(R.string.please_accept_our_terms_conditions), Toast.LENGTH_LONG).show();
                dialogCustom.dismissDialog();
            }
        } else {
            dialogCustom.dismissDialog();
            Toast.makeText(getContext(), getString(R.string.please_fill_all_information), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        dialogCustom = new DialogCustom(getContext());

    }

    @Override
    public void setDate(int cDay, int cMonth, int cYear) {
        binding.birthDate.setTextColor(getResources().getColor(R.color.bink));
        binding.birthDate.setText(cYear + " / " + (cMonth + 1) + " / " + cDay);
        calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 8);
        calendar.set(Calendar.YEAR, 2015);
        long when = calendar.getTimeInMillis();
    }
}