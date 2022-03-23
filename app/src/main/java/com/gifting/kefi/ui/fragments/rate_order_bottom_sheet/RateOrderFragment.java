package com.gifting.kefi.ui.fragments.rate_order_bottom_sheet;

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
import com.gifting.kefi.data.model.RateOrderModel;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.FragmentRateOrderBinding;
import com.gifting.kefi.util.DialogCustom;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class RateOrderFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private RateOrderViewModel mViewModel;
    private FragmentRateOrderBinding binding;
    private DialogCustom dialogCustom;
    private UserSharedPreference userSharedPreference;
    private String orderId, userName;


    public static RateOrderFragment newInstance() {
        return new RateOrderFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rate_order, container, false);
        binding.sendRateButton.setOnClickListener(this);
        return binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RateOrderViewModel.class);
        // TODO: Use the ViewModel

        userSharedPreference = new UserSharedPreference(getContext());

        orderId = getArguments().getString("ORDER_ID");
        userName = getArguments().getString("USER_NAME");

    }

    @Override
    public void onClick(View v) {
        dialogCustom = new DialogCustom(getContext());
        if (v == binding.sendRateButton) {
         //   if (binding.ratingBar.getRating() != 0f) {
                if (!binding.commentEditText.getText().toString().isEmpty()) {
                    dialogCustom.showDialog();
                    mViewModel.addRateInDatabase(new RateOrderModel(userSharedPreference.getUserDetails().getId(), orderId, binding.commentEditText.getText().toString(),  "5", userName));
                    mViewModel.getSuccessText().observe(getViewLifecycleOwner(), success -> {
                        dialogCustom.dismissDialog();
                        dismiss();
                        Toast.makeText(getContext(),getString( R.string.your_rate_added_successfully), Toast.LENGTH_SHORT).show();

                    });
                    mViewModel.getFailText().observe(getViewLifecycleOwner(), failText -> {
                        dialogCustom.dismissDialog();
                        Toast.makeText(getContext(), failText, Toast.LENGTH_SHORT).show();
                    });
                } else {
                    Toast.makeText(getContext(), getString(R.string.please_write_your_comment), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), getString(R.string.please_choose_your_rate), Toast.LENGTH_SHORT).show();

            }
       // }
    }
}