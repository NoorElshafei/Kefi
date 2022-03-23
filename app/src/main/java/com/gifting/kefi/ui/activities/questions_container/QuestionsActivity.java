package com.gifting.kefi.ui.activities.questions_container;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;

import com.gifting.kefi.R;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivityQuestionsBinding;
import com.gifting.kefi.ui.activities.first_container.FirstContainerActivity;
import com.gifting.kefi.ui.activities.main.MainActivity;
import com.gifting.kefi.util.AnimationCustomization;
import com.gifting.kefi.util.Language;

public class QuestionsActivity extends AppCompatActivity {
    private CardView indicator1, indicator2, indicator3;
    private ActivityQuestionsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_questions);

        Language.changeBackDependsLanguage(binding.backImage,getApplicationContext());



        indicator1 = findViewById(R.id.indicator1);
        indicator2 = findViewById(R.id.indicator2);
        indicator3 = findViewById(R.id.indicator3);


        Animation animation = AnimationCustomization.inFromRightAnimation();
        binding.relativeLayout.startAnimation(animation);


        binding.back.setOnClickListener(v -> {
            onBackPressed();
        });
    }


    @Override
    public void onBackPressed() {
        //int count = getSupportFragmentManager().getBackStackEntryCount();
        int count = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment2).getChildFragmentManager().getBackStackEntryCount();
        //int count = navHostFragment.getChildFragmentManager().getFragments().size();
        if (count < 1) {
            Intent intent = new Intent(getApplicationContext(), FirstContainerActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (count == 7) {

            indicator3.setCardBackgroundColor(getResources().getColor(R.color.bink_light));
            indicator2.setCardBackgroundColor(getResources().getColor(R.color.bink));

            super.onBackPressed();
        } else {


                AnimationCustomization.animationInFromLeft(indicator1, indicator2, indicator1, getApplicationContext());
                AnimationCustomization.animationInFromLeft(indicator2, indicator2, indicator1, getApplicationContext());
                AnimationCustomization.animationInFromLeft(indicator3, indicator2, indicator1, getApplicationContext());


            super.onBackPressed();
        }

    }

    public CardView getIndicator1() {
        return indicator1;
    }

    public CardView getIndicator2() {
        return indicator2;
    }

    public CardView getIndicator3() {
        return indicator3;
    }
}



    /*ObjectAnimator animX = ObjectAnimator.ofFloat(indicator, "x", 146f);
        arrayListObjectAnimators.add(animX);
                ObjectAnimator[] objectAnimators = arrayListObjectAnimators.toArray(new ObjectAnimator[arrayListObjectAnimators.size()]);
                AnimatorSet animSetXY = new AnimatorSet();
                animSetXY.playTogether(objectAnimators);
                animSetXY.setDuration(1000);//1sec
                animSetXY.start();*/
