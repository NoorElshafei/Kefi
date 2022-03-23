package com.gifting.kefi.ui.fragments.calendar;

import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.local_database.AppRoomDatabase;
import com.gifting.kefi.data.model.CalendarModel;
import com.gifting.kefi.databinding.FragmentCalendarBinding;
import com.gifting.kefi.ui.activities.notification_note_settings.NotificationNoteSettingsActivity;
import com.gifting.kefi.ui.adapters.NotesAdapter;
import com.gifting.kefi.ui.fragments.add_note.AddNoteFragment;
import com.gifting.kefi.util.MySelectorDecorator;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CalendarFragment extends Fragment implements View.OnClickListener {

    private CalendarViewModel mViewModel;
    private FragmentCalendarBinding binding;
    private NotesAdapter adapter;
    private BottomSheetBehavior bottomSheetBehavior;
    private List<CalendarModel> calendarList;
    private AppRoomDatabase db;
    private Calendar calendar1;


    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false);

        View view = binding.getRoot();
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);
      //  customizeCalendar();



      /*  bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetBehaviorId.layoutBottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View view, int i) {
                // do something when state changes
            }

            @Override
            public void onSlide(View view, float v) {
                // animating the view on top of Bottom Sheet
                binding.bottomSheetBehaviorId.constraintLayout2.animate().y(v <= 0 ?
                        view.getY() + bottomSheetBehavior.getPeekHeight() - binding.bottomSheetBehaviorId.constraintLayout2.getHeight() :
                        view.getHeight() - binding.bottomSheetBehaviorId.constraintLayout2.getHeight()).setDuration(0).start();

            }
        });
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);*/



        binding.bottomSheetBehaviorId.notesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter = new NotesAdapter(getActivity(), getContext());
        binding.bottomSheetBehaviorId.notesRecyclerView.setAdapter(adapter);


        binding.settingsImage.setOnClickListener(v -> startActivity(new Intent(getContext(), NotificationNoteSettingsActivity.class)));
        binding.bottomSheetBehaviorId.newButton.setOnClickListener(this);


    }

 /*   private void customizeCalendar() {
        binding.calendarView.addDecorator(new MySelectorDecorator(getContext()));
        binding.calendarView.setTopbarVisible(false);

        customizeMonth(Calendar.getInstance().get(Calendar.MONTH)+1);
        binding.calendarTextYear.setText("" + Calendar.getInstance().get(Calendar.YEAR));
        binding.calendarView.setOnMonthChangedListener((widget, date) -> {
            customizeMonth(date.getMonth());
            binding.calendarTextYear.setText("" + date.getYear());

        });

        binding.calendarView.setOnDateChangedListener((widget, date, selected) -> {

            customizeMonth(date.getMonth());
            binding.calendarTextYear.setText("" + date.getYear());
//            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            calendar1 = Calendar.getInstance();
            calendar1.set(date.getYear(), date.getMonth()-1, date.getDay());
            db = AppRoomDatabase.getDatabase(getContext());
            String dateString = new SimpleDateFormat("dd-MM-yyyy").format(calendar1.getTimeInMillis());

            calendarList = db.userDao().getAllCalendarById(dateString);
            adapter.setCalendarList(calendarList);
            // Toast.makeText(getContext(),calendar1.getTime().getTime()+"", Toast.LENGTH_SHORT).show();
            binding.bottomSheetBehaviorId.rootLayout.setVisibility(View.VISIBLE);

        });

    }*/

    private void customizeMonth(int month) {

        switch (month) {
            case 1:
                binding.calendarTextMonth.setText(getString(R.string.January));
                break;
            case 2:
                binding.calendarTextMonth.setText(getString(R.string.February));
                break;
            case 3:
                binding.calendarTextMonth.setText(getString(R.string.march));
                break;
            case 4:
                binding.calendarTextMonth.setText(getString(R.string.April));
                break;
            case 5:
                binding.calendarTextMonth.setText(getString(R.string.May));
                break;
            case 6:
                binding.calendarTextMonth.setText(getString(R.string.June));
                break;
            case 7:
                binding.calendarTextMonth.setText(getString(R.string.July));
                break;
            case 8:
                binding.calendarTextMonth.setText(getString(R.string.August));
                break;
            case 9:
                binding.calendarTextMonth.setText(getString(R.string.September));
                break;
            case 10:
                binding.calendarTextMonth.setText(getString(R.string.October));
                break;
            case 11:
                binding.calendarTextMonth.setText(getString(R.string.November));
                break;
            case 12:
                binding.calendarTextMonth.setText(getString(R.string.December));
                break;
            default:


        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.bottomSheetBehaviorId.newButton) {
            /*getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null)
                    .replace(R.id.container, AddNoteFragment.newInstance(), "add note").commit();*/
            String dateString = new SimpleDateFormat("dd-MM-yyyy").format(calendar1.getTimeInMillis());

            Bundle bundle = new Bundle();
            bundle.putString("CalendarModel", dateString);
            AddNoteFragment fragment2 = new AddNoteFragment();
            fragment2.setArguments(bundle);
            getActivity().getSupportFragmentManager()
                    .beginTransaction().addToBackStack(null)
                    .replace(R.id.container, fragment2)
                    .commit();
        }
        else {
            ///amany
            Toast.makeText(getContext(), getString(R.string.please_select_day), Toast.LENGTH_SHORT).show();
        }




    }
}