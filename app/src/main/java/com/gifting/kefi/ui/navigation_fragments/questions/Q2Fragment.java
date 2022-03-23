package com.gifting.kefi.ui.navigation_fragments.questions;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.User;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.ui.activities.questions_container.QuestionsActivity;
import com.gifting.kefi.ui.activities.your_skin.YourSkinActivity;
import com.gifting.kefi.ui.activities.your_skin.YourSkinViewModel;

import static com.gifting.kefi.data.shared_preference.UserSharedPreference.DRY;
import static com.gifting.kefi.data.shared_preference.UserSharedPreference.MIXED;
import static com.gifting.kefi.data.shared_preference.UserSharedPreference.NORMAL;
import static com.gifting.kefi.data.shared_preference.UserSharedPreference.OILY;
import static com.gifting.kefi.util.AnimationCustomization.animationInFromLeft;
import static com.gifting.kefi.util.AnimationCustomization.animationInFromRight;


public class Q2Fragment extends Fragment implements View.OnClickListener {
    private LinearLayout q2Button, dryLayout, oilyLayout, mixedLayout, normalLayout;
    private CardView indicator1, indicator2, indicator3;
    private UserSharedPreference userSharedPreference;
    private YourSkinViewModel mViewModel;
    private User user;
    private LiveData<User> userLiveData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public Q2Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Q2Fragment newInstance() {
        Q2Fragment fragment = new Q2Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(YourSkinViewModel.class);

        userSharedPreference = new UserSharedPreference(getContext());

        q2Button.setOnClickListener(this);
        dryLayout.setOnClickListener(this);
        oilyLayout.setOnClickListener(this);
        mixedLayout.setOnClickListener(this);
        normalLayout.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_q2, container, false);

        q2Button = view.findViewById(R.id.q2_button);
        dryLayout = view.findViewById(R.id.dry_layout);
        oilyLayout = view.findViewById(R.id.oily_layout);
        mixedLayout = view.findViewById(R.id.mixed_layout);
        normalLayout = view.findViewById(R.id.normal_layout);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == q2Button) {
            indicator1 = ((QuestionsActivity) getActivity()).getIndicator1();
            indicator2 = ((QuestionsActivity) getActivity()).getIndicator2();
            indicator3 = ((QuestionsActivity) getActivity()).getIndicator3();


                animationInFromRight(indicator1, indicator2, indicator1, getContext());
                animationInFromRight(indicator2, indicator2, indicator1, getContext());
                animationInFromRight(indicator3, indicator2, indicator1, getContext());

            Navigation.findNavController(v).navigate(R.id.action_q2Fragment_to_q3Fragment);


        } else if (v == dryLayout) {
            oilyLayout.setBackgroundResource(0);
            mixedLayout.setBackgroundResource(0);
            normalLayout.setBackgroundResource(0);
            userSharedPreference.saveUserSkin(DRY);
            userSharedPreference.setSkinType("DRY");
            user = userSharedPreference.getUserDetails();
            getActivity().startActivity(new Intent(getContext().getApplicationContext(),YourSkinActivity.class));



        } else if (v == oilyLayout) {
            dryLayout.setBackgroundResource(0);
            mixedLayout.setBackgroundResource(0);
            normalLayout.setBackgroundResource(0);
            userSharedPreference.saveUserSkin(OILY);
            userSharedPreference.setSkinType("OILY");
            user = userSharedPreference.getUserDetails();
            getActivity().startActivity(new Intent(getContext().getApplicationContext(),YourSkinActivity.class));



        } else if (v == mixedLayout) {
            oilyLayout.setBackgroundResource(0);
            dryLayout.setBackgroundResource(0);
            normalLayout.setBackgroundResource(0);
            userSharedPreference.saveUserSkin(MIXED);
            userSharedPreference.setSkinType("MIXED");
            user = userSharedPreference.getUserDetails();
            getActivity().startActivity(new Intent(getContext().getApplicationContext(),YourSkinActivity.class));


        } else if (v == normalLayout) {

            oilyLayout.setBackgroundResource(0);
            mixedLayout.setBackgroundResource(0);
            dryLayout.setBackgroundResource(0);
            userSharedPreference.saveUserSkin(NORMAL);
            userSharedPreference.setSkinType("NORMAL");
            user = userSharedPreference.getUserDetails();
            getActivity().startActivity(new Intent(getContext().getApplicationContext(),YourSkinActivity.class));

        }



    }

    @Override
    public void onStop() {
        super.onStop();
        mViewModel.getUserLiveData().removeObservers(this);
        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.exit_from_right, R.anim.enter_form_right);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}