package com.gifting.kefi.ui.navigation_fragments.questions;

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
import com.gifting.kefi.databinding.FragmentQ7Binding;
import com.gifting.kefi.ui.activities.questions_container.QuestionsActivity;
import com.gifting.kefi.ui.adapters.QuestionsAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class Q7Fragment extends Fragment implements View.OnClickListener {
    private LinearLayout q7Button;
    private FragmentQ7Binding binding;
    private QuestionsAdapter adapter;
    private CardView indicator1, indicator2, indicator3;


    public Q7Fragment() {
        // Required empty public constructor
    }


    public static Q7Fragment newInstance() {
        Q7Fragment fragment = new Q7Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.q7RecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        ArrayList<String> answersList = new ArrayList<>(Arrays.asList(getString(R.string.very_often), getString(R.string.sometimes),getString(R.string.never2)));
        ArrayList<Integer> checkIfSelectedList = new ArrayList<>(Arrays.asList(0, 0, 0));

        adapter = new QuestionsAdapter(getContext(), answersList, checkIfSelectedList, "q7", false);
        binding.q7RecyclerView.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_q7, container, false);
        View view = binding.getRoot();
        q7Button = view.findViewById(R.id.q7_button);

        q7Button.setOnClickListener(this);
        return view;

    }


    @Override
    public void onClick(View v) {
        if (v == q7Button) {
            if (adapter.isAnswerSelected()) {

                indicator1 = ((QuestionsActivity) getActivity()).getIndicator1();
                indicator2 = ((QuestionsActivity) getActivity()).getIndicator2();
                indicator3 = ((QuestionsActivity) getActivity()).getIndicator3();

                indicator3.setCardBackgroundColor(getResources().getColor(R.color.bink));
                indicator2.setCardBackgroundColor(getResources().getColor(R.color.bink_light));

                Navigation.findNavController(v).navigate(R.id.action_q7Fragment_to_q8Fragment);
            } else {
                Toast.makeText(getContext(),  getString(R.string.please_select_one_answer), Toast.LENGTH_SHORT).show();
            }

        }
    }
}