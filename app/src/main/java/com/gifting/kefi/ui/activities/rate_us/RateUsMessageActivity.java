package com.gifting.kefi.ui.activities.rate_us;

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
import androidx.lifecycle.ViewModelProvider;

import com.gifting.kefi.R;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivityRateUsMessageBinding;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.DialogCustom;
import com.gifting.kefi.util.Language;
import com.google.firebase.auth.FirebaseAuth;

public class RateUsMessageActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityRateUsMessageBinding binding;
    private RateUsViewModel rateUsViewModel;
    private float rating;
    private DialogCustom dialogCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rate_us_message);
        rateUsViewModel = new ViewModelProvider(this).get(RateUsViewModel.class);

        Language.changeBackDependsLanguage(binding.backImage, this);

        rating = getIntent().getFloatExtra("rateValue", 0);

        //binding.simpleRatingBar.setRating(rating);

        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);
        binding.sendRateButton.setOnClickListener(this);


        binding.messageEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)

                binding.messageEditText.setHint("");
            else
                binding.messageEditText.setHint(getString(R.string.write_a_clarification_that_expresses_your_satisfaction));
        });
        binding.messageEditText.setFocusableInTouchMode(true);




       /* binding.rateUsRoot.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (binding.messageEditText.isFocused()) {
                    Rect outRect = new Rect();
                    binding.messageEditText.getGlobalVisibleRect(outRect);
                    if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                        binding.messageEditText.clearFocus();
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
            return false;
        });*/

    }

    @Override
    public void onClick(View v) {
        dialogCustom = new DialogCustom(this);
        if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.notificationLayout) {
            startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
        } else if (v == binding.back) {
            onBackPressed();
        } else if (v == binding.sendRateButton) {


            if (binding.messageEditText.getText().toString().equals("")) {
                Toast.makeText(this, getString(R.string.please_write_your_rate), Toast.LENGTH_SHORT).show();
            } else {
                dialogCustom.showDialog();
                saveRate();
            }


        }

    }

    private void saveRate() {

        UserSharedPreference userSharedPreference = new UserSharedPreference(this);
        rateUsViewModel.setRateApp(FirebaseAuth.getInstance().getUid(), userSharedPreference.getUserDetails().getName(), userSharedPreference.getUserDetails().getPhoneNumber(), binding.messageEditText.getText().toString(), rating + "");
        rateUsViewModel.getSuccessText().observe(this, s -> {
            dialogCustom.dismissDialog();
            Toast.makeText(this, getString(R.string.thank_you_your_message_sent_successfully), Toast.LENGTH_SHORT).show();
            onBackPressed();
        });

        rateUsViewModel.getFailText().observe(this, s -> {
            dialogCustom.dismissDialog();
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        });
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
            binding.messageEditText.clearFocus();
            if (imm != null) {
                imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
            }
        }
    }

}