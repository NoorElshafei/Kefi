package com.gifting.kefi.ui.activities.blumers;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.User;
import com.gifting.kefi.databinding.ActivityBlumeCreatorsBinding;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.adapters.UserSearchAdapter;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.ui.fragments.user_deatials_bottom_sheet.UserDetailsFragment;
import com.gifting.kefi.util.CheckInternet;
import com.gifting.kefi.util.Language;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BlumersActivity extends AppCompatActivity implements View.OnClickListener {
    private BlumersViewModel blumersViewModel;
    private ActivityBlumeCreatorsBinding binding;
    private ArrayList<User> userArrayList;
    private UserSearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_blume_creators);
        Glide.with(getApplicationContext()).load(R.drawable.loading).into(binding.loadProgress);

        Language.changeBackDependsLanguage(binding.backImage,getApplicationContext());

        checkInternet();

        binding.usersRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        adapter = new UserSearchAdapter(this,this);
        binding.usersRecyclerView.setAdapter(adapter);

        blumersViewModel = new ViewModelProvider(this).get(BlumersViewModel.class);
        userArrayList = new ArrayList<>();
        blumersViewModel.getUsers();
        blumersViewModel.getUserLiveData().observe(this, users -> {
            blumersViewModel.getUserLiveData().removeObservers(this);
            userArrayList = users;
            setUserAtView();
        });

        binding.back.setOnClickListener(this);
        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.searchAction.setOnClickListener(this);


        binding.searchEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                binding.noItemText.setVisibility(View.GONE);
                binding.usersRecyclerView.setVisibility(View.GONE);
                //binding.loadLayout.setVisibility(View.VISIBLE);
                binding.topBlumersLayout.setVisibility(View.GONE);

                String searchTerm = s.toString();

                if (searchTerm.trim().isEmpty()) {
                    adapter.removeAllData();
                    binding.loadLayout.setVisibility(View.GONE);
                    binding.noItemText.setVisibility(View.GONE);
                    binding.topBlumersLayout.setVisibility(View.VISIBLE);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        binding.searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String searchTerm = binding.searchEditText.getText().toString();
                if (searchTerm.trim().isEmpty()) {
                    adapter.removeAllData();
                } else {
                    performSearch(searchTerm);
                }
                return false;
            }
            return false;
        });

    }

    private void performSearch(String searchTerm) {
        binding.loadLayout.setVisibility(View.VISIBLE);
        binding.topBlumersLayout.setVisibility(View.GONE);
        binding.noItemText.setVisibility(View.GONE);
        blumersViewModel.setLoadSearchUsers(searchTerm);
        blumersViewModel.getUserLiveData1().observe(this, users -> {
            blumersViewModel.getUserLiveData1().removeObservers(this);

            if (users.size() == 0) {
                binding.noItemText.setVisibility(View.VISIBLE);
                binding.topBlumersLayout.setVisibility(View.GONE);
                binding.loadLayout.setVisibility(View.GONE);
                adapter.removeAllData();
                adapter.notifyDataSetChanged();
            } else {
                binding.topBlumersLayout.setVisibility(View.GONE);
                binding.usersRecyclerView.setVisibility(View.VISIBLE);
                binding.noItemText.setVisibility(View.GONE);
                binding.loadLayout.setVisibility(View.GONE);
                adapter.setUsers(users);
            }

            //adapter.notifyDataSetChanged();

        });
       /* blumersViewModel.getFailText().observe(this, text -> {
            blumersViewModel.getFailText().removeObservers(this);


            // Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

        });*/
    }

    @Override
    public void onClick(View v) {
        if (v == binding.user1TopImage) {
            openFragment(0);
        } else if (v == binding.user2TopImage) {
            openFragment(1);
        } else if (v == binding.user3TopImage) {
            openFragment(2);
        } else if (v == binding.user4TopImage) {
            openFragment(3);
        } else if (v == binding.user5TopImage) {
            openFragment(4);
        } else if (v == binding.user6TopImage) {
            openFragment(5);
        } else if (v == binding.user7TopImage) {
            openFragment(6);
        } else if (v == binding.user8TopImage) {
            openFragment(7);
        } else if (v == binding.user9TopImage) {
            openFragment(8);
        } else if (v == binding.user10TopImage) {
            openFragment(9);
        } else if (v == binding.user11TopImage) {
            openFragment(10);
        } else if (v == binding.user12TopImage) {
            openFragment(11);
        } else if (v == binding.user13TopImage) {
            openFragment(12);
        } else if (v == binding.user14TopImage) {
            openFragment(13);
        } else if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.notificationLayout) {
            startActivity(new Intent(this, NotificationsActivity.class));
        } else if (v == binding.searchAction) {

            String searchTerm = binding.searchEditText.getText().toString();
            if (searchTerm.trim().isEmpty()) {
                adapter.removeAllData();
            } else {
                performSearch(searchTerm);
            }
        } else if (v == binding.back) {
            onBackPressed();
        }

    }

    private void setUserAtView() {
        binding.loadLayout.setVisibility(View.GONE);
        binding.topBlumersLayout.setVisibility(View.VISIBLE);
        Glide.with(getApplicationContext()).load(userArrayList.get(0).getImageURL()).placeholder(R.drawable.avatar).into(binding.user1TopImage);
        Glide.with(getApplicationContext()).load(userArrayList.get(1).getImageURL()).placeholder(R.drawable.avatar).into(binding.user2TopImage);
        Glide.with(getApplicationContext()).load(userArrayList.get(2).getImageURL()).placeholder(R.drawable.avatar).into(binding.user3TopImage);
        Glide.with(getApplicationContext()).load(userArrayList.get(3).getImageURL()).placeholder(R.drawable.avatar).into(binding.user4TopImage);
        Glide.with(getApplicationContext()).load(userArrayList.get(4).getImageURL()).placeholder(R.drawable.avatar).into(binding.user5TopImage);
        Glide.with(getApplicationContext()).load(userArrayList.get(5).getImageURL()).placeholder(R.drawable.avatar).into(binding.user6TopImage);
        Glide.with(getApplicationContext()).load(userArrayList.get(6).getImageURL()).placeholder(R.drawable.avatar).into(binding.user7TopImage);
        Glide.with(getApplicationContext()).load(userArrayList.get(7).getImageURL()).placeholder(R.drawable.avatar).into(binding.user8TopImage);
        Glide.with(getApplicationContext()).load(userArrayList.get(8).getImageURL()).placeholder(R.drawable.avatar).into(binding.user9TopImage);
        Glide.with(getApplicationContext()).load(userArrayList.get(9).getImageURL()).placeholder(R.drawable.avatar).into(binding.user10TopImage);
        Glide.with(getApplicationContext()).load(userArrayList.get(10).getImageURL()).placeholder(R.drawable.avatar).into(binding.user11TopImage);
        Glide.with(getApplicationContext()).load(userArrayList.get(11).getImageURL()).placeholder(R.drawable.avatar).into(binding.user12TopImage);
        Glide.with(getApplicationContext()).load(userArrayList.get(12).getImageURL()).placeholder(R.drawable.avatar).into(binding.user13TopImage);
        Glide.with(getApplicationContext()).load(userArrayList.get(13).getImageURL()).placeholder(R.drawable.avatar).into(binding.user14TopImage);

        binding.user1TopText.setText(userArrayList.get(0).getName());
        binding.user2TopText.setText(userArrayList.get(1).getName());
        binding.user3TopText.setText(userArrayList.get(2).getName());
        binding.user4TopText.setText(userArrayList.get(3).getName());
        binding.user5TopText.setText(userArrayList.get(4).getName());
        binding.user6TopText.setText(userArrayList.get(5).getName());
        binding.user7TopText.setText(userArrayList.get(6).getName());
        binding.user8TopText.setText(userArrayList.get(7).getName());
        binding.user9TopText.setText(userArrayList.get(8).getName());
        binding.user10TopText.setText(userArrayList.get(9).getName());
        binding.user11TopText.setText(userArrayList.get(10).getName());
        binding.user12TopText.setText(userArrayList.get(11).getName());
        binding.user13TopText.setText(userArrayList.get(12).getName());
        binding.user14TopText.setText(userArrayList.get(13).getName());

        binding.user1TopImage.setOnClickListener(this);
        binding.user2TopImage.setOnClickListener(this);
        binding.user3TopImage.setOnClickListener(this);
        binding.user4TopImage.setOnClickListener(this);
        binding.user5TopImage.setOnClickListener(this);
        binding.user6TopImage.setOnClickListener(this);
        binding.user7TopImage.setOnClickListener(this);
        binding.user8TopImage.setOnClickListener(this);
        binding.user9TopImage.setOnClickListener(this);
        binding.user10TopImage.setOnClickListener(this);
        binding.user11TopImage.setOnClickListener(this);
        binding.user12TopImage.setOnClickListener(this);
        binding.user13TopImage.setOnClickListener(this);
        binding.user14TopImage.setOnClickListener(this);


    }

    private void openFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("USER", userArrayList.get(position));
        UserDetailsFragment userDetailsFragment = UserDetailsFragment.newInstance();
        userDetailsFragment.setArguments(bundle);
        userDetailsFragment.show(getSupportFragmentManager(), "dssd1");
    }

    private void checkInternet() {
        CheckInternet.hasInternetConnection().subscribe((hasInternet) -> {
            if (!hasInternet && this!=null) {
                Toast.makeText(getApplicationContext(), getString(R.string.internet_may_be_unstable_or_not_connected), Toast.LENGTH_LONG).show();

            }
        });
    }
}