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
import com.gifting.kefi.databinding.FragmentQ5Binding;
import com.gifting.kefi.ui.activities.questions_container.QuestionsActivity;
import com.gifting.kefi.ui.adapters.QuestionsAdapter;

import java.util.ArrayList;
import java.util.Arrays;


public class Q5Fragment extends Fragment implements View.OnClickListener {

    private LinearLayout q5Button;
    private FragmentQ5Binding binding;
    private QuestionsAdapter adapter;
    private CardView indicator1, indicator2, indicator3;


    public Q5Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Q5Fragment newInstance() {
        Q5Fragment fragment = new Q5Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.q5RecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        ArrayList<String> answersList = new ArrayList<>(Arrays.asList(getString(R.string.greasy), getString(R.string.rough_texture), getString(R.string.smooth_texture), getString(R.string.fine_lines_and_wrinkles)));
        ArrayList<Integer> checkIfSelectedList = new ArrayList<>(Arrays.asList(0, 0, 0, 0));

        adapter = new QuestionsAdapter(getContext(), answersList, checkIfSelectedList, "q5", false);
        binding.q5RecyclerView.setAdapter(adapter);

        q5Button.setOnClickListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_q5, container, false);
        View view = binding.getRoot();


        q5Button = view.findViewById(R.id.q5_button);

        return view;

    }

    @Override
    public void onClick(View v) {
        if (v == q5Button) {
            if (adapter.isAnswerSelected()) {
                indicator1 = ((QuestionsActivity) getActivity()).getIndicator1();
                indicator2 = ((QuestionsActivity) getActivity()).getIndicator2();
                indicator3 = ((QuestionsActivity) getActivity()).getIndicator3();

                UserSharedPreference userSharedPreference = new UserSharedPreference(getContext());


                    animationInFromRight(indicator1, indicator2, indicator1, getContext());
                    animationInFromRight(indicator2, indicator2, indicator1, getContext());
                    animationInFromRight(indicator3, indicator2, indicator1, getContext());


                Navigation.findNavController(v).navigate(R.id.action_q5Fragment_to_q6Fragment);
            } else {
                Toast.makeText(getContext(), getString(R.string.please_select_one_answer), Toast.LENGTH_SHORT).show();
            }
        }
    }
}