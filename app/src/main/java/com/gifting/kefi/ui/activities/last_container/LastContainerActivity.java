package com.gifting.kefi.ui.activities.last_container;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gifting.kefi.R;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.util.CustomLanguage;
import com.gifting.kefi.util.Language;

public class LastContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_container);

    }

    @Override
    protected void onStart() {
        super.onStart();
        CustomLanguage.checkLanguage(this);
    }
    /* private void setGraphDependOn(){
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.home_nav_fragment);  // Hostfragment
        NavInflater inflater = navHostFragment.getNavController().getNavInflater();
        NavGraph graph = inflater.inflate(R.navigation.nav_graph4);
        graph.setDefaultArguments(getIntent().getExtras());
        graph.setStartDestination(R.id.fragment1);

        navHostFragment.getNavController().setGraph(graph);
        navHostFragment.getNavController().getGraph().setDefaultArguments(getIntent().getExtras());


        NavigationView navigationView = findViewById(R.id.navigationView);
        NavigationUI.setupWithNavController(navigationView, navHostFragment.getNavController());
    }*/
}