package com.gifting.kefi.ui.fragments.add_comment_youtube_video;

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
import com.gifting.kefi.data.model.VideoCommentModel;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.FragmentAddCommentVideoBinding;
import com.gifting.kefi.ui.activities.video_details_youtube.VideoDetailsYoutubeActivity;
import com.gifting.kefi.util.CheckInternet;
import com.gifting.kefi.util.DialogCustom;
import com.gifting.kefi.util.KeyboardCustomization;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddCommentYoutubeVideoFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private FragmentAddCommentVideoBinding binding;
    private AddCommentVideoViewModel mViewModel;
    private UserSharedPreference userSharedPreference;
    private DialogCustom dialogCustom;
    private String commentText;
    private String commentID;
    private String videoId;


    public static AddCommentYoutubeVideoFragment newInstance() {
        return new AddCommentYoutubeVideoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_comment_video, container, false);
        binding.sendRateButton.setOnClickListener(this);

        KeyboardCustomization keyboardCustomization = new KeyboardCustomization(getActivity());
        keyboardCustomization.setupKeyboard(binding.constraintLayout4);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddCommentVideoViewModel.class);
        // TODO: Use the ViewModel

        if (getArguments().getString("VideoId") != null) {
            videoId = getArguments().getString("VideoId");
        } else if (getArguments().getString("VIDEO_ID") != null) {
            videoId = getArguments().getString("VIDEO_ID");
            commentText = getArguments().getString("COMMENT");
            commentID = getArguments().getString("COMMENT_ID");
            binding.commentEditText.setText(commentText);
        }
        userSharedPreference = new UserSharedPreference(getContext());

    }

    @Override
    public void onClick(View v) {
        dialogCustom = new DialogCustom(getContext());
        if (v == binding.sendRateButton) {
            addCommentToFirebase();
           // checkInternet();
        }
    }

    private void checkInternet() {

        CheckInternet.hasInternetConnection().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((hasInternet) -> {
            if (!hasInternet && getContext()!=null) {
                Toast.makeText(getContext(), getString(R.string.please_check_your_internet), Toast.LENGTH_LONG).show();
            } else {
                addCommentToFirebase();
            }

        });

    }

    private void addCommentToFirebase() {
        if (!binding.commentEditText.getText().toString().isEmpty()) {
            dialogCustom.showDialog();
            long commentTime;
            VideoCommentModel videoCommentModel = new VideoCommentModel(binding.commentEditText.getText().toString(),
                    videoId, userSharedPreference.getUserDetails().getId(),
                    userSharedPreference.getUserDetails().getName(),
                    0);
            if (getArguments().getString("EditVideo") != null) {
                commentTime = getArguments().getLong("VideoDate");
                videoCommentModel.setCommentID(commentID);
            } else {
                commentTime = Calendar.getInstance().getTimeInMillis();
            }
            videoCommentModel.setCommentDate(commentTime);

            mViewModel.addRateInDatabase(videoCommentModel);
            mViewModel.getSuccessText().observe(getViewLifecycleOwner(), success -> {
                dialogCustom.dismissDialog();
                dismiss();

                ((VideoDetailsYoutubeActivity) getActivity()).updateComment();

                if (getArguments().getString("EditVideo") != null) {
                    Toast.makeText(getContext(), getString(R.string.your_comment_edited_successfully), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), getString(R.string.your_comment_added_successfully), Toast.LENGTH_SHORT).show();
                }


            });
            mViewModel.getFailText().observe(getViewLifecycleOwner(), failText -> {
                dialogCustom.dismissDialog();
                Toast.makeText(getContext(), failText, Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(getContext(), getString(R.string.please_write_your_comment), Toast.LENGTH_SHORT).show();
        }
    }

}