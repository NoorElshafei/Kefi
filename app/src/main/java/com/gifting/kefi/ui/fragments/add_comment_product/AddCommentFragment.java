package com.gifting.kefi.ui.fragments.add_comment_product;

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
import com.gifting.kefi.data.model.ReviewModel;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.FragmentAddCommentBinding;
import com.gifting.kefi.ui.activities.product_details.ProductDetailsActivity;
import com.gifting.kefi.util.CheckInternet;
import com.gifting.kefi.util.DialogCustom;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddCommentFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private AddCommentViewModel mViewModel;
    private FragmentAddCommentBinding binding;
    private String productId;
    private UserSharedPreference userSharedPreference;
    private DialogCustom dialogCustom;


    public static AddCommentFragment newInstance() {
        return new AddCommentFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_comment, container, false);
        binding.sendRateButton.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddCommentViewModel.class);
        // TODO: Use the ViewModel
        productId = getArguments().getString("PRODUCT_ID");
        userSharedPreference = new UserSharedPreference(getContext());

        if (getArguments().getString("COMMENT") != null) {
            String comment = getArguments().getString("COMMENT");
            binding.commentEditText.setText(comment);
            //binding.ratingBar.setRating(getArguments().getFloat("RATE", 0));
        }
    }

    @Override
    public void onClick(View v) {
        dialogCustom = new DialogCustom(getContext());
        if (v == binding.sendRateButton) {
          //  if (binding.ratingBar.getRating() != 0f) {
                if (!binding.commentEditText.getText().toString().isEmpty()) {
                    checkInternet();
                } else {
                    Toast.makeText(getContext(), getString(R.string.please_write_your_comment), Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getContext(), getString(R.string.please_choose_your_rate), Toast.LENGTH_SHORT).show();

         //   }
        }

    }

    private void checkInternet() {

        CheckInternet.hasInternetConnection().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((hasInternet) -> {
            if (!hasInternet) {
                Toast.makeText(getContext(), getString(R.string.please_check_your_internet), Toast.LENGTH_LONG).show();
            } else {
                addCommentToFirebase();
            }

        });

    }

    private void addCommentToFirebase() {
        dialogCustom.showDialog();
        mViewModel.addRateInDatabase(new ReviewModel(binding.commentEditText.getText().toString(),
                productId, 5f, userSharedPreference.getUserDetails().getId(),
                userSharedPreference.getUserDetails().getName(),
                Calendar.getInstance().getTimeInMillis()));
        mViewModel.getSuccessText().observe(getViewLifecycleOwner(), success -> {
            dialogCustom.dismissDialog();
            dismiss();
            ((ProductDetailsActivity) getActivity()).updateComment();
            if (getArguments().getString("COMMENT") != null) {
                Toast.makeText(getContext(), getString(R.string.your_review_edited_successfully), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), getString(R.string.your_review_added_successfully), Toast.LENGTH_SHORT).show();
            }
        });
        mViewModel.getFailText().observe(getViewLifecycleOwner(), failText -> {
            dialogCustom.dismissDialog();
            Toast.makeText(getContext(), failText, Toast.LENGTH_SHORT).show();
        });
    }
}