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
import com.gifting.kefi.databinding.FragmentQ3Binding;
import com.gifting.kefi.ui.activities.questions_container.QuestionsActivity;
import com.gifting.kefi.ui.adapters.QuestionsAdapter;

import java.util.ArrayList;
import java.util.Arrays;


public class Q3Fragment extends Fragment implements View.OnClickListener {
    private LinearLayout q3Button;
    private FragmentQ3Binding binding;
    private QuestionsAdapter adapter;
    private CardView indicator1, indicator2, indicator3;
    private boolean isAnswerSelected = false;


    public Q3Fragment() {
        // Required empty public constructor
    }


    public static Q3Fragment newInstance() {
        Q3Fragment fragment = new Q3Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        q3Button.setOnClickListener(this);

        binding.q3RecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        ArrayList<String> answersList = new ArrayList<>(Arrays.asList(getString(R.string.under_20), getString(R.string.between_20_40), getString(R.string.upper_40)));
        ArrayList<Integer> checkIfSelectedList = new ArrayList<>(Arrays.asList(0, 0, 0));

        adapter = new QuestionsAdapter(getContext(), answersList, checkIfSelectedList, "q3", false);
        binding.q3RecyclerView.setAdapter(adapter);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_q3, container, false);
        View view = binding.getRoot();

        q3Button = view.findViewById(R.id.q3_button);


        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == q3Button) {
            if (adapter.isAnswerSelected()) {
                indicator1 = ((QuestionsActivity) getActivity()).getIndicator1();
                indicator2 = ((QuestionsActivity) getActivity()).getIndicator2();
                indicator3 = ((QuestionsActivity) getActivity()).getIndicator3();


                    animationInFromRight(indicator1, indicator2, indicator1, getContext());
                    animationInFromRight(indicator2, indicator2, indicator1, getContext());
                    animationInFromRight(indicator3, indicator2, indicator1, getContext());


                Navigation.findNavController(v).navigate(R.id.action_q3Fragment_to_q4Fragment);
            } else {
                Toast.makeText(getContext(), getString(R.string.please_select_one_answer), Toast.LENGTH_SHORT).show();
            }
        }


    }
}