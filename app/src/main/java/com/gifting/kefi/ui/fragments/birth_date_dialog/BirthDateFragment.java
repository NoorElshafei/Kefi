package com.gifting.kefi.ui.fragments.birth_date_dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.gifting.kefi.R;
import com.gifting.kefi.databinding.BirthDateFragmentBinding;

import java.util.Calendar;

public class BirthDateFragment extends DialogFragment implements View.OnClickListener {

    private BirthDateViewModel mViewModel;
    private BirthDateFragmentBinding binding;
    private Calendar calendar;
    private DateInterface dateInterface;
    private int cYear = 0, cMonth = 0, cDay = 0;

    public BirthDateFragment(DateInterface dateInterface) {
        this.dateInterface = dateInterface;
    }

    public static BirthDateFragment newInstance(DateInterface dateInterface) {
        return new BirthDateFragment(dateInterface);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.MyTheme);
        calendar = Calendar.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.birth_date_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.datePicker.setMaxDate(calendar.getTimeInMillis());
        binding.buttonDone.setOnClickListener(this);
        binding.calendarView.setOnClickListener(this);
        binding.cardViewCalendar.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BirthDateViewModel.class);
        // TODO: Use the ViewModel

        viewCalendar();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.buttonDone) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            if (cDay == 0) {
                cDay = calendar.get(Calendar.DAY_OF_MONTH);
                cMonth = calendar.get(Calendar.MONTH);
                cYear = calendar.get(Calendar.YEAR);
            }
            dateInterface.setDate(cDay, cMonth, cYear);
            dismiss();
        } else if (v == binding.cardViewCalendar) {
            binding.calendarView.setVisibility(View.VISIBLE);

        } else if (v == binding.calendarView) {
            dismiss();
        }
    }

    private void viewCalendar() {
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        binding.datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), (view, year, monthOfYear, dayOfMonth) -> {
            cYear = year;
            cMonth = monthOfYear;
            cDay = dayOfMonth;
        });
    }


}

