package com.gifting.kefi.ui.fragments.edit_name_bottom_sheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.User;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.FragmentEditNameBinding;
import com.gifting.kefi.util.DialogCustom;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class EditNameFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private EditNameViewModel mViewModel;
    private FragmentEditNameBinding binding;
    private UserSharedPreference userSharedPreference;
    private DialogCustom dialogCustom;


    public static EditNameFragment newInstance() {

        return new EditNameFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_name, container, false);
        binding.saveName.setOnClickListener(this);
        return binding.getRoot();


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditNameViewModel.class);
        // TODO: Use the ViewModel
        userSharedPreference = new UserSharedPreference(getContext());
        String editNameString = getArguments().getString("editName");
        binding.nameEditText.setText(editNameString);
    }

    @Override
    public void onClick(View v) {
        dialogCustom = new DialogCustom(getContext());

        if (v == binding.saveName) {
            String name1[] = binding.nameEditText.getText().toString().split(" ");
            if (name1.length > 1 && name1.length < 3) {
                dialogCustom.showDialog();
                String headLine = userSharedPreference.getHeadLine();
                User user = userSharedPreference.getUserDetails();
                user.setName(binding.nameEditText.getText().toString());
                user.setSearch(binding.nameEditText.getText().toString().toLowerCase());
                mViewModel.editName(user);
                mViewModel.getSuccessText().observe(getViewLifecycleOwner(), user1 -> {
                    mViewModel.getSuccessText().removeObservers(getViewLifecycleOwner());
                    dialogCustom.dismissDialog();
                    user.setHeadLine(headLine);
                    userSharedPreference.addUser(user);
                    Toast.makeText(getContext(), getString(R.string.name_updated_successfully), Toast.LENGTH_SHORT).show();
                    getParentFragment().onStart();
                    dismiss();
                });
            } else {
                Toast.makeText(getContext(), getString(R.string.please_enter_just_first_last_name), Toast.LENGTH_SHORT).show();
            }
        }
    }
}