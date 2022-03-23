package com.gifting.kefi.ui.fragments.add_note;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.TransitionInflater;

import com.gifting.kefi.R;
import com.gifting.kefi.data.local_database.AppRoomDatabase;
import com.gifting.kefi.data.model.CalendarModel;
import com.gifting.kefi.databinding.FragmentAddNoteBinding;
import com.gifting.kefi.util.DialogCustom;
import com.bumptech.glide.Glide;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddNoteFragment extends Fragment implements View.OnClickListener {

    private AddNoteViewModel mViewModel;
    private SingleDateAndTimePicker timePicker;
    private FragmentAddNoteBinding binding;
    private DialogCustom dialogCustom;
    private CalendarModel calendarModel;
    AppRoomDatabase db;
    private String id_date;
    private String selected_color = "";
    private boolean isUpdate = false;
    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";

    public static AddNoteFragment newInstance() {

        return new AddNoteFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_note, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);


        if (getArguments().getString("CalendarModel") != null) {

            // handle your code here.
            id_date = getArguments().getString("CalendarModel", "");

        }
        if (getArguments().getParcelable("calendarModel") != null) {
            isUpdate = true;
            calendarModel = getArguments().getParcelable("calendarModel");
            method();

        }


        binding.alarmSwitch.setOnClickListener(this);
        binding.newNoteButton.setOnClickListener(this);
        binding.yellowCircle.setOnClickListener(this);
        binding.greenCircle.setOnClickListener(this);
        binding.binkCircle.setOnClickListener(this);
        binding.newNoteButton.setOnClickListener(this);

        binding.alarmSwitch.setTag("fill");
        binding.yellowCircle.setTag("unFill");
        binding.greenCircle.setTag("unFill");
        binding.binkCircle.setTag("unFill");


        binding.timeTo.addOnDateChangedListener((displayed, date) -> {
            //Toast.makeText(getContext(), displayed, Toast.LENGTH_SHORT).show();
        });


        binding.timeFrom.setDisplayMinutes(true);
        binding.timeFrom.setDisplayDays(false);
        binding.timeFrom.setDisplayHours(true);
        binding.timeFrom.setDisplayMonths(false);
        binding.timeFrom.setDisplayYears(false);
        binding.timeFrom.setDisplayDaysOfMonth(false);
        binding.timeFrom.setDisplayMonthNumbers(false);

        binding.timeTo.setDisplayMinutes(true);
        binding.timeTo.setDisplayDays(false);
        binding.timeTo.setDisplayHours(true);
        binding.timeTo.setDisplayMonths(false);
        binding.timeTo.setDisplayYears(false);
        binding.timeTo.setDisplayDaysOfMonth(false);


    }

    @Override
    public void onClick(View v) {
        ///amany///
        dialogCustom = new DialogCustom(getContext());
        //when click save back to calendar




        if (v == binding.editTextTextPersonName) {
            int size = binding.editTextTextPersonName.getText().toString().length();
            if (size < 0 && size < 25) {
                dialogCustom.showDialog();

            }
        }
        if (v == binding.alarmSwitch) {
            if (binding.alarmSwitch.getTag() == "fill") {
                binding.alarmSwitch.setImageResource(R.drawable.ic_alarm_empty1);
                binding.alarmSwitch.setTag("unFill");


            } else {
                binding.alarmSwitch.setImageResource(R.drawable.ic_alarm_fill1);
                binding.alarmSwitch.setTag("fill");
            }
        } else if (v == binding.yellowCircle) {
            customizeSelectedCircle(binding.yellowCircle, binding.greenCircle, binding.binkCircle, R.drawable.ic_yellow_selected, R.drawable.ic_circle_yellow, R.drawable.ic_circle_green, R.drawable.ic_done_);
            selected_color = "yellow";

        } else if (v == binding.greenCircle) {
            customizeSelectedCircle(binding.greenCircle, binding.yellowCircle, binding.binkCircle, R.drawable.ic_green_selected, R.drawable.ic_circle_green, R.drawable.ic_circle_yellow, R.drawable.ic_done_);
            selected_color = "green";
        } else if (v == binding.binkCircle) {
            customizeSelectedCircle(binding.binkCircle, binding.greenCircle, binding.yellowCircle, R.drawable.ic_bink_selected, R.drawable.ic_done_, R.drawable.ic_circle_green, R.drawable.ic_circle_yellow);
            selected_color = "bink";
        } else if (v == binding.newNoteButton) {
            //AddNoteFragment back = fragmentManager.beginTransation();
          //  back.replace( R.layout.fragment_calendar, new AddNoteFragment() ).addToBackStack( "tag" ).commit();
          //  getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("my_fragment").commit();


            if ((!binding.editTextTextPersonName.getText().toString().equals("")) && (!binding.editTextTextPersonName2.getText().toString().equals(""))) {
                if (selected_color.equals("")) {
                    selected_color = "bink";
                }
                if (isUpdate) {
                    id_date=calendarModel.getId_date();

                    //selected_color=calendarModel.getColor_select();
                    update(binding.editTextTextPersonName.getText().toString(), binding.editTextTextPersonName2.getText().toString(), binding.timeTo.getDate().getTime(), binding.timeFrom.getDate().getTime(), id_date, selected_color);
                    Toast.makeText(getContext(), getString(R.string.note_updated_successfully), Toast.LENGTH_SHORT).show();


                } else {

                    insert(binding.editTextTextPersonName.getText().toString(), binding.editTextTextPersonName2.getText().toString(), binding.timeTo.getDate().getTime(), binding.timeFrom.getDate().getTime(), id_date, selected_color);
                    Toast.makeText(getContext(),getString( R.string.note_saved_successfully), Toast.LENGTH_SHORT).show();

                }


                Calendar date = Calendar.getInstance();
                date.setTimeInMillis(convertDate(id_date));
                Calendar startDate = Calendar.getInstance();
                startDate.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH) ,
                        date.get(Calendar.DAY_OF_MONTH), binding.timeFrom.getDate().getHours(), binding.timeFrom.getDate().getMinutes());

                Calendar endDate = Calendar.getInstance();
                endDate.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH) ,
                        date.get(Calendar.DAY_OF_MONTH), binding.timeTo.getDate().getHours(), binding.timeTo.getDate().getMinutes());

                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE, binding.editTextTextPersonName.getText().toString());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, binding.editTextTextPersonName2.getText().toString());
                intent.putExtra(CalendarContract.Events.DISPLAY_COLOR, getResources().getColor(R.color.bink));
                //intent.putExtra(CalendarContract.Calendars.CALENDAR_COLOR, getResources().getColor(R.color.bink));


                intent.putExtra(CalendarContract.Events.EXDATE, id_date);
                intent.putExtra(CalendarContract.Events.ALL_DAY, "false");
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startDate.getTimeInMillis());
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endDate.getTimeInMillis());
                getActivity().startActivity(intent);
                getActivity().onBackPressed();

               /* AddNoteFragment ldf = new AddNoteFragment ();
                Bundle back = new Bundle();
                back.putBoolean("BOOLEAN_VALUE",true);
                ldf.setArguments(back);

                getFragmentManager().beginTransaction().add(R.id.container, ldf).commit();*/

            } else {
                Toast.makeText(getContext(), getString(R.string.please_fill_all_information), Toast.LENGTH_SHORT).show();
            }


        }




        /*private void showFragment(){
            final AddNoteFragment fragment = new AddNoteFragment();
            final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R, fragment,"TAG_FRAGMENT");
            transaction.addToBackStack(null);
            transaction.commit();
        }

        @Override
        public void onBackPressed(){
            final AddNoteFragment fragment = (AddNoteFragment) getSupportFragmentManager().findFragmentByTag("TAG_FRAGMENT");

            if (fragment.allowBackPressed()) { // and then you define a method allowBackPressed with the logic to allow back pressed or not
                super.onBackPressed();
            }*/
        }







    private void customizeSelectedCircle(ImageView imageViewSelected, ImageView imageView2, ImageView imageView3, int firstColorSelected, int secondColor, int thirdColor, int fourthColorSelected) {
        if (imageViewSelected.getTag() == "unFill") {
            imageViewSelected.setImageResource(firstColorSelected);
            imageViewSelected.setTag("fill");
        } else {
            imageViewSelected.setImageResource(secondColor);
            imageViewSelected.setTag("unFill");
        }
        imageView2.setImageResource(thirdColor);
        imageView2.setTag("unFill");
        imageView3.setImageResource(fourthColorSelected);
        imageView3.setTag("unFill");

    }

    private void insert(String title, String note, Long data_to, Long data_from, String id_date, String color_select) {
        db = AppRoomDatabase.getDatabase(getContext());
        CalendarModel calendarModel = new CalendarModel();
        calendarModel.setTitle(title);
        calendarModel.setNote(note);
        calendarModel.setData_to(data_to);
        calendarModel.setData_from(data_from);
        calendarModel.setId_date(id_date);
        calendarModel.setColor_select(color_select);
        db.userDao().insertItem(calendarModel);


    }

    private void update(String title, String note, Long data_to, Long data_from, String id_date, String color_select) {
        db = AppRoomDatabase.getDatabase(getContext());
        CalendarModel calendarModelUpdated = new CalendarModel();
        calendarModelUpdated .setTitle(title);
        calendarModelUpdated .setNote(note);
        calendarModelUpdated .setData_to(data_to);
        calendarModelUpdated .setData_from(data_from);
        calendarModelUpdated .setId_date(id_date);
        calendarModelUpdated .setColor_select(color_select);
        db.userDao().updateDate(calendarModel.getCid(), calendarModelUpdated );


    }

    private void method() {

        Calendar dateFrom = Calendar.getInstance();
        dateFrom.setTimeInMillis((long) calendarModel.getData_from());

        Calendar dateTo = Calendar.getInstance();
        dateTo.setTimeInMillis((long) calendarModel.getData_to());


        binding.editTextTextPersonName.setText(calendarModel.getTitle() + "");
        binding.editTextTextPersonName2.setText(calendarModel.getNote() + "");

        binding.timeTo.selectDate(dateTo);
        binding.timeFrom.selectDate(dateFrom);
        selected_color=calendarModel.getColor_select();
        if (calendarModel.getColor_select().equals("yellow")) {


            Glide.with(getContext()).load(R.drawable.ic_yellow_selected).into(binding.yellowCircle);

        } else if (calendarModel.getColor_select().equals("green")) {
            Glide.with(getContext()).load(R.drawable.ic_green_selected).into(binding.greenCircle);
        } else if (calendarModel.getColor_select().equals("bink")) {
            Glide.with(getContext()).load(R.drawable.ic_bink_selected).into(binding.binkCircle);
        }



    }

    private long convertDate(String id_date) {

        String dateString = id_date;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long startDate = date.getTime();
        return startDate;

    }

}