package com.gifting.kefi.ui.activities.notification_note_settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.gifting.kefi.R;
import com.gifting.kefi.databinding.ActivityNotificationNoteSettingsBinding;
import com.gifting.kefi.ui.activities.calendar_container.CalendarActivity;

public class NotificationNoteSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityNotificationNoteSettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification_note_settings);

        binding.changeButton.setOnClickListener(this);
        binding.doneButton.setOnClickListener(this);

        binding.hourEditText.setFocusableInTouchMode(true);


    }

    @Override
    public void onClick(View v) {

        if (v == binding.changeButton) {
            startActivity(new Intent(NotificationNoteSettingsActivity.this, CalendarActivity.class));
        } else if (v == binding.doneButton) {
            Toast.makeText(this, getString(R.string.settings_changed_successfully), Toast.LENGTH_LONG).show();
            onBackPressed();
        }

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int[] sourceCoordinates = new int[2];
            v.getLocationOnScreen(sourceCoordinates);
            float x = ev.getRawX() + v.getLeft() - sourceCoordinates[0];
            float y = ev.getRawY() + v.getTop() - sourceCoordinates[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom()) {
                hideKeyboard(this);
            }

        }
        return super.dispatchTouchEvent(ev);
    }

    private void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null) {
            activity.getWindow().getDecorView();
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            binding.hourEditText.clearFocus();
            if (imm != null) {
                imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
            }
        }
    }
}