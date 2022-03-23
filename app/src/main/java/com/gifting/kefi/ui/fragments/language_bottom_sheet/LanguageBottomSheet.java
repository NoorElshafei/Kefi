package com.gifting.kefi.ui.fragments.language_bottom_sheet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.gifting.kefi.R;
import com.gifting.kefi.data.local_database.AppRoomDatabase;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.FragmentLanguageBottomSheetBinding;
import com.gifting.kefi.ui.activities.first_container.FirstContainerActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class LanguageBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {
    FragmentLanguageBottomSheetBinding binding;
    UserSharedPreference userSharedPreference;
    private AlertDialog.Builder alertDialog;
    private AppRoomDatabase database;


    public LanguageBottomSheet() {
        // Required empty public constructor
    }


    public static LanguageBottomSheet newInstance() {
        LanguageBottomSheet fragment = new LanguageBottomSheet();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userSharedPreference = new UserSharedPreference(getContext());
        database=AppRoomDatabase.getDatabase(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_language_bottom_sheet, container, false);

        binding.selectArabicButton.setOnClickListener(this);
        binding.selectEnglishButton.setOnClickListener(this);


        // Inflate the layout for this fragment
        return binding.getRoot();
    }


    @Override
    public void onClick(View v) {

        alertDialog = new AlertDialog.Builder(getContext());


        if (v == binding.selectArabicButton) {
            alertDialog.setTitle(R.string.change_the_language);
            alertDialog.setMessage(R.string.language_message);
            alertDialog.setCancelable(false);
            userSharedPreference.saveLanguage("ar");
            alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getContext(), FirstContainerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    database.clearAllTables();
                }
            });
            alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = alertDialog.create();
            dialog.show();

        } else if (v == binding.selectEnglishButton) {
            alertDialog.setTitle(R.string.change_the_language);
            alertDialog.setMessage(R.string.language_message);
            alertDialog.setCancelable(false);
            userSharedPreference.saveLanguage("en");
            alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getContext(), FirstContainerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    database.clearAllTables();
                }
            });

            alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = alertDialog.create();
            dialog.show();

        }
    }
}