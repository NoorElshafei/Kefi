package com.gifting.kefi.ui.navigation_fragments.questions;

import static com.gifting.kefi.util.AnimationCustomization.animationInFromLeft;
import static com.gifting.kefi.util.AnimationCustomization.animationInFromRight;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.FragmentQ6Binding;
import com.gifting.kefi.ui.activities.questions_container.QuestionsActivity;
import com.gifting.kefi.ui.adapters.QuestionsAdapter;

import java.util.ArrayList;
import java.util.Arrays;


public class Q6Fragment extends Fragment implements View.OnClickListener {
    private LinearLayout q6Button;
    private FragmentQ6Binding binding;
    private QuestionsAdapter adapter;
    private CardView indicator1, indicator2, indicator3;


    public Q6Fragment() {
        // Required empty public constructor
    }


    public static Q6Fragment newInstance() {
        Q6Fragment fragment = new Q6Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.q6RecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        ArrayList<String> answersList = new ArrayList<>(Arrays.asList(getString(R.string.all_over_the_face), getString(R.string.at_the_checks_mouth), getString(R.string.never)));
        ArrayList<Integer> checkIfSelectedList = new ArrayList<>(Arrays.asList(0, 0, 0));

        adapter = new QuestionsAdapter(getContext(), answersList, checkIfSelectedList, "q6",false);
        binding.q6RecyclerView.setAdapter(adapter);

        q6Button.setOnClickListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_q6, container, false);
        View view = binding.getRoot();
        q6Button = view.findViewById(R.id.q6_button);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == q6Button) {
            if (adapter.isAnswerSelected()) {

                indicator1 = ((QuestionsActivity) getActivity()).getIndicator1();
                indicator2 = ((QuestionsActivity) getActivity()).getIndicator2();
                indicator3 = ((QuestionsActivity) getActivity()).getIndicator3();

                UserSharedPreference userSharedPreference = new UserSharedPreference(getContext());


                    animationInFromRight(indicator1, indicator2, indicator1, getContext());
                    animationInFromRight(indicator2, indicator2, indicator1, getContext());
                    animationInFromRight(indicator3, indicator2, indicator1, getContext());


                Navigation.findNavController(v).navigate(R.id.action_q6Fragment_to_q7Fragment);
            } else {
                Toast.makeText(getContext(),getString(R.string.please_select_one_answer), Toast.LENGTH_SHORT).show();
            }
        }
    }
}