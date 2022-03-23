package com.gifting.kefi.ui.navigation_fragments.login;

import android.content.Intent;
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
import com.gifting.kefi.databinding.FragmentLoginBinding;
import com.gifting.kefi.ui.activities.main.MainActivity;
import com.gifting.kefi.ui.activities.questions_container.QuestionsActivity;
import com.gifting.kefi.util.DialogCustom;
import com.gifting.kefi.util.KeyboardCustomization;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private FragmentLoginBinding binding;
    private LoginViewModel mViewModel;
    boolean checkRemember = false;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private boolean passIsVisible = false;
    private DialogCustom dialogCustom;
    private UserSharedPreference userSharedPreference;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        View view = binding.getRoot();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();

        binding.forgetPassword.setOnClickListener(this);
        binding.rememberLayout.setOnClickListener(this);
        binding.loginButton.setOnClickListener(this);
        binding.eyePassImage.setOnClickListener(this);

       /* CustomizeKeyboard.editKeyboardEmail(binding.emailEditText, binding.scrollView);
        CustomizeKeyboard.editKeyboardPassword(binding.passwordEditText, binding.scrollView);*/

        KeyboardCustomization keyboardCustomization = new KeyboardCustomization(getActivity());
        keyboardCustomization.setupKeyboard(binding.scrollView);


        userSharedPreference = new UserSharedPreference(getContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        mViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            dialogCustom.dismissDialog();

            if (user != null) {
                userSharedPreference.addUser(user);
                userSharedPreference.setIsLogin(true);
                Intent intent;
                if (user.isQuestionAnswered()) {
                    intent = new Intent(getContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().overridePendingTransition(R.anim.enter_form_right, R.anim.exit_from_right);
                } else {
                    intent = new Intent(getContext(), QuestionsActivity.class);
                    getActivity().overridePendingTransition(R.anim.enter_form_right_d3, R.anim.exit_from_right_d3);
                    //Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_questionsActivity);
                }
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "user is empty", Toast.LENGTH_SHORT).show();
            }

        });

        mViewModel.getFail().observe(getViewLifecycleOwner(), failText -> {
            dialogCustom.dismissDialog();
            Toast.makeText(getContext(), failText, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onClick(View v) {
        if (v == binding.forgetPassword) {
            Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_forgetPasswordFragment);
        } else if (v == binding.rememberLayout) {
            if (checkRemember) {
                binding.checkBoxRemember.setImageResource(R.drawable.ic_check_box);
                checkRemember = false;
            } else {
                binding.checkBoxRemember.setImageResource(R.drawable.square_stroke_bink);
                checkRemember = true;
            }
        } else if (v == binding.loginButton) {
            login();
        } else if (v == binding.eyePassImage) {
            if (passIsVisible) {
                binding.eyePassImage.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                //binding.passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                binding.passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
                passIsVisible = false;
            } else {
                binding.eyePassImage.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                //binding.passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                binding.passwordEditText.setTransformationMethod(null);
                passIsVisible = true;
            }
            binding.passwordEditText.setSelection(binding.passwordEditText.length());
        }
    }

    private void login() {
        dialogCustom = new DialogCustom(getContext());
        dialogCustom.showDialog();
        dialogCustom.getDialog().setCancelable(false);

        if (!binding.emailEditText.getText().toString().isEmpty() && !binding.passwordEditText.getText().toString().isEmpty()) {
            mViewModel.login(binding.emailEditText.getText().toString(), binding.passwordEditText.getText().toString());

        } else {
            Toast.makeText(getContext(), getString(R.string.please_fill_all_information), Toast.LENGTH_LONG).show();
            dialogCustom.dismissDialog();
        }
    }
}