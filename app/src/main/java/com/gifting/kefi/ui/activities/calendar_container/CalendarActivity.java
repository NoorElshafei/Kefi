package com.gifting.kefi.ui.activities.calendar_container;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.gifting.kefi.ui.fragments.calendar.CalendarFragment;
import com.gifting.kefi.R;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, CalendarFragment.newInstance(), "calendar").commit();
    }
}