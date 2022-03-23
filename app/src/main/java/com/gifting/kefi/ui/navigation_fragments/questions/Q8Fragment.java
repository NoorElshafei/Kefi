package com.gifting.kefi.ui.navigation_fragments.questions;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.databinding.FragmentQ8Binding;
import com.gifting.kefi.ui.activities.your_skin.YourSkinActivity;
import com.gifting.kefi.ui.adapters.QuestionsAdapter;

import java.util.ArrayList;
import java.util.Arrays;


public class Q8Fragment extends Fragment implements View.OnClickListener {
    private FragmentQ8Binding binding;
    private QuestionsAdapter adapter;
    private CardView indicator1, indicator2, indicator3;


    public Q8Fragment() {
        // Required empty public constructor
    }


    public static Q8Fragment newInstance() {
        Q8Fragment fragment = new Q8Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        binding.q8Button.setOnClickListener(this);

        binding.q8RecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        ArrayList<String> answersList = new ArrayList<>(Arrays.asList(getString(R.string.often), getString(R.string.rarely)));
        ArrayList<Integer> checkIfSelectedList = new ArrayList<>(Arrays.asList(0, 0));

        adapter = new QuestionsAdapter(getContext(), answersList, checkIfSelectedList, "q8", false);
        binding.q8RecyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_q8, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onClick(View v) {

        if (v == binding.q8Button) {
            if (adapter.isAnswerSelected()) {
                Intent intent = new Intent(getContext(), YourSkinActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter_form_right, R.anim.exit_from_right);
            } else {
                Toast.makeText(getContext(), getString(R.string.please_select_one_answer), Toast.LENGTH_SHORT).show();
            }
        }
    }
}