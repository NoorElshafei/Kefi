package com.gifting.kefi.ui.fragments.edit_head_line;

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

public class EditHeadLineFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private EditHeadLineViewModel mViewModel;
    private FragmentEditNameBinding binding;
    private UserSharedPreference userSharedPreference;
    private DialogCustom dialogCustom;

    public static EditHeadLineFragment newInstance() {
        return new EditHeadLineFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
      //  getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
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
        mViewModel = new ViewModelProvider(this).get(EditHeadLineViewModel.class);
        // TODO: Use the ViewModel
        userSharedPreference = new UserSharedPreference(getContext());
        binding.titleName.setText(R.string.add_headline);
        binding.nameEditText.setHint("Ex: YouTube, Blogger, Travel Creator");
        String editHeadlineString = getArguments().getString("editHeadline");
        binding.nameEditText.setText(editHeadlineString);

    }

    @Override
    public void onClick(View v) {
        dialogCustom = new DialogCustom(getContext());

        if (v == binding.saveName) {
            int sizeHeadLine = binding.nameEditText.getText().toString().length();
            ///////// question very important

            if (sizeHeadLine < 35 && sizeHeadLine > 0) {
                dialogCustom.showDialog();
                mViewModel.editHeadLine(binding.nameEditText.getText().toString());
                mViewModel.getSuccessText().observe(getViewLifecycleOwner(), headLine -> {
                    mViewModel.getSuccessText().removeObservers(getViewLifecycleOwner());
                    dialogCustom.dismissDialog();
                    User user = userSharedPreference.getUserDetails();
                    user.setHeadLine(headLine);
                    userSharedPreference.addUser(user);
                    Toast.makeText(getContext(), getString(R.string.headLine_updated_successfully), Toast.LENGTH_SHORT).show();
                    getParentFragment().onStart();
                    dismiss();
                });
            } else {
                if (sizeHeadLine == 0)
                    Toast.makeText(getContext(), getString(R.string.please_write_a_headline), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), getString(R.string.Please_write_less_than_35_character), Toast.LENGTH_SHORT).show();
            }

        }
    }
}