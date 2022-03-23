package com.gifting.kefi.ui.navigation_fragments.questions;

import static com.gifting.kefi.util.AnimationCustomization.animationInFromLeft;
import static com.gifting.kefi.util.AnimationCustomization.animationInFromRight;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.gifting.kefi.R;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.ui.activities.questions_container.QuestionsActivity;


public class Q1Fragment extends Fragment implements View.OnClickListener {
    private LinearLayout q1Button, maleLayout, femaleLayout;
    private TextView preferToNot;
    private CardView indicator1, indicator2, indicator3;
    private boolean isAnswerSelected = false;
    private UserSharedPreference userSharedPreference;

    public Q1Fragment() {
        // Required empty public constructor
    }

    public static Q1Fragment newInstance() {
        Q1Fragment fragment = new Q1Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        q1Button.setOnClickListener(this);
        preferToNot.setOnClickListener(this);
        maleLayout.setOnClickListener(this);
        femaleLayout.setOnClickListener(this);

        userSharedPreference = new UserSharedPreference(getContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_q1, container, false);
        q1Button = view.findViewById(R.id.q1_button);
        preferToNot = view.findViewById(R.id.prefer_not_to_choose);
        maleLayout = view.findViewById(R.id.male_layout);
        femaleLayout = view.findViewById(R.id.female_layout);

        return view;
    }

    @Override
    public void onClick(View v) {

        if (v == q1Button) {
            if (isAnswerSelected) {
                indicator1 = ((QuestionsActivity) getActivity()).getIndicator1();
                indicator2 = ((QuestionsActivity) getActivity()).getIndicator2();
                indicator3 = ((QuestionsActivity) getActivity()).getIndicator3();


                    animationInFromRight(indicator1, indicator2, indicator1, getContext());
                    animationInFromRight(indicator2, indicator2, indicator1, getContext());
                    animationInFromRight(indicator3, indicator2, indicator1, getContext());


                Navigation.findNavController(v).navigate(R.id.action_q1Fragment_to_q2Fragment);
                isAnswerSelected = false;

            } else {
                Toast.makeText(getContext(), getString(R.string.please_select_one_answer), Toast.LENGTH_SHORT).show();
            }

        }
        if (v == preferToNot) {
            Navigation.findNavController(v).navigate(R.id.action_q1Fragment_to_q2Fragment);
            userSharedPreference.setGender("preferToNotChoose");

        }
        if (v == maleLayout) {
            maleLayout.setBackground(getResources().getDrawable(R.drawable.stroke_button_bink_18));
            femaleLayout.setBackgroundResource(0);
            isAnswerSelected = true;
            userSharedPreference.setGender("male");
        }
        if (v == femaleLayout) {
            femaleLayout.setBackground(getResources().getDrawable(R.drawable.stroke_button_bink_18));
            maleLayout.setBackgroundResource(0);
            isAnswerSelected = true;
            userSharedPreference.setGender("female");
        }

    }


}