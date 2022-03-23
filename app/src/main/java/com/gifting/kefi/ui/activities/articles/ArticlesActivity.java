package com.gifting.kefi.ui.activities.articles;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.local_database.AppRoomDatabase;
import com.gifting.kefi.data.model.ArticlesModel;
import com.gifting.kefi.data.model.RecentlyModel;
import com.gifting.kefi.databinding.ActivityArticlesBinding;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.adapters.FeaturedArticleAdapter;
import com.gifting.kefi.ui.adapters.RecentArticleAdapter;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.Language;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArticlesActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityArticlesBinding binding;
    FeaturedArticleAdapter adapter1;
    FeaturedArticleAdapter adapter3;
    FeaturedArticleAdapter adapter4;
    FeaturedArticleAdapter adapter5;
    FeaturedArticleAdapter adapter6;
    RecentArticleAdapter adapter2;
    ArrayList<ArticlesModel> kids;
    ArrayList<ArticlesModel> listOfArticles;
    List<RecentlyModel>recentlyModelList;
    ArrayList<ArticlesModel> hair;
    ArrayList<ArticlesModel> body;
    ArrayList<ArticlesModel> face;
    private TabLayout tabLayout;
    AppRoomDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_articles);

        db=AppRoomDatabase.getDatabase(getApplicationContext());

        Language.changeBackDependsLanguage(binding.backImage,getApplicationContext());


      /*  View headerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.custom_tab1, null, false);*/
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        View headerView = LayoutInflater.from(this).inflate(R.layout.custom_tab1, null);
        //View v = LayoutInflater.from(this).inflate(R.layout.custom_tab1, null);
        LinearLayout linearLayoutOne = (LinearLayout) headerView.findViewById(R.id.ll);
        LinearLayout linearLayout2 = (LinearLayout) headerView.findViewById(R.id.ll2);
        LinearLayout linearLayout3 = (LinearLayout) headerView.findViewById(R.id.ll3);
        LinearLayout linearLayout4 = (LinearLayout) headerView.findViewById(R.id.l4);
        LinearLayout linearLayout5 = (LinearLayout) headerView.findViewById(R.id.l5);

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.getTabAt(0).setCustomView(linearLayoutOne);
        tabLayout.getTabAt(1).setCustomView(linearLayout2);
        tabLayout.getTabAt(2).setCustomView(linearLayout3);
        tabLayout.getTabAt(3).setCustomView(linearLayout4);
        tabLayout.getTabAt(4).setCustomView(linearLayout5);
        tabLayout.setTabIndicatorFullWidth(false);



        AllArticles();


        binding.articleFeaturedRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        adapter1 = new FeaturedArticleAdapter(this, listOfArticles);
        binding.articleFeaturedRecyclerView.setAdapter(adapter1);

        AppRoomDatabase db=AppRoomDatabase.getDatabase(this);

         recentlyModelList =db.userDao().getRecentlyModel();

        binding.articleRecentlyRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        adapter2 = new RecentArticleAdapter(this,recentlyModelList);
        binding.articleRecentlyRecyclerView.setAdapter(adapter2);



        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);


        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0)
                {
                    adapter1 = new FeaturedArticleAdapter(ArticlesActivity.this, listOfArticles);
                    binding.articleFeaturedRecyclerView.setAdapter(adapter1);
                }
                else if (tab.getPosition()==1){

                    adapter4 = new FeaturedArticleAdapter(ArticlesActivity.this, face);
                    binding.articleFeaturedRecyclerView.setAdapter(adapter4);

                }
                else if (tab.getPosition()==2){
                    adapter3 = new FeaturedArticleAdapter(ArticlesActivity.this, hair);
                    binding.articleFeaturedRecyclerView.setAdapter(adapter3);
                }
                else if (tab.getPosition()==3){
                    adapter6 = new FeaturedArticleAdapter(ArticlesActivity.this, body);
                    binding.articleFeaturedRecyclerView.setAdapter(adapter6);
                }
                else if(tab.getPosition()==4){
                    adapter5 = new FeaturedArticleAdapter(ArticlesActivity.this, kids);
                    binding.articleFeaturedRecyclerView.setAdapter(adapter5);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
    @Override
    protected void onStart(){
        super.onStart();
        recentlyModelList =db.userDao().getRecentlyModel();
        Collections.reverse( recentlyModelList);
        binding.articleRecentlyRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        adapter2 = new RecentArticleAdapter(this,recentlyModelList);
        binding.articleRecentlyRecyclerView.setAdapter(adapter2);

    }


    @Override
    public void onClick(View v) {
        if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.notificationLayout) {
            startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
        } else if (v == binding.back) {
            onBackPressed();
        }
    }

    public void AllArticles(){
        listOfArticles = new ArrayList<ArticlesModel>();
        listOfArticles.add(new ArticlesModel("2", R.drawable.diaper, getString(R.string.diaper_area), getString(R.string.detailsDiaperArea), getString(R.string.tips_for_diaper_area), getString(R.string.tipsDiaperArea),getString(R.string.description) ,getString(R.string.September_5_2021)));
        listOfArticles.add(new ArticlesModel("3", R.drawable.dark_circle2, getString(R.string.dark_circles),getString(R.string.detailsDark),  getString(R.string.tips_to_overcome_it), getString(R.string.tipsDarkCircle),getString(R.string.description),getString(R.string.September_8_2021)));
        listOfArticles.add(new ArticlesModel("4", R.drawable.dandruff,  getString(R.string.dandruff),getString(R.string.detailsDandruff), getString(R.string.tips_for_dandruff), getString(R.string.tipsDandruff),getString(R.string.description),getString(R.string.september_12_2021)));
        listOfArticles.add(new ArticlesModel("5", R.drawable.dry_skin, getString(R.string.dry_skin), getString(R.string.detailsDrySkin), getString(R.string.tips_for_dry_skin), getString(R.string.tipsDrySkin), getString(R.string.description),getString(R.string.september_17_2021)));
        listOfArticles.add(new ArticlesModel("6", R.drawable.chapped_lips3, getString(R.string.chapped_lips), getString(R.string.detailsChappedLips),  getString(R.string.tips_to_overcome_it), getString(R.string.tipsChappedLips),getString(R.string.description),getString(R.string.September_20_2021) ));
        listOfArticles.add(new ArticlesModel("7", R.drawable.cracked_corner_lips, getString(R.string.cracked_corner_lips), getString(R.string.detailsCrackedCornerLips),  getString(R.string.tips_to_overcome_it), getString(R.string.tipsCrackedCornerLips), getString(R.string.description),getString(R.string.September_25_2021)));
        listOfArticles.add(new ArticlesModel("1", R.drawable.bikini62, getString(R.string.bikini_area), getString(R.string.detailsBikiniArea), getString(R.string.tips_for_bikini), getString(R.string.tipsBikiniArea),getString(R.string.problem),getString(R.string.September_30_2021) ));
        listOfArticles.add(new ArticlesModel("8", R.drawable.fine_line2, getString(R.string.fine_lines), getString(R.string.detailsFineLines),  getString(R.string.tips_to_overcome_it), getString(R.string.tipsFineLine),getString(R.string.description),getString(R.string.October_2_2021)));
        listOfArticles.add(new ArticlesModel("9", R.drawable.foot_hand, getString(R.string.foot_hand), getString(R.string.detailsFootHand), getString(R.string.tips_for_foot_hand), getString(R.string.tipsFootHand),getString(R.string.problem) ,getString(R.string.October_6_2021)));
        listOfArticles.add(new ArticlesModel("10", R.drawable.hair555, getString(R.string.hair_care), getString(R.string.detailsHairCare), getString(R.string.tips_for_hair_care), getString(R.string.tipsHairCare), getString(R.string.description),getString(R.string.October_10_2021)));
        listOfArticles.add(new ArticlesModel("11", R.drawable.hair_damage, getString(R.string.hair_damage), getString(R.string.detailsHairDamage), getString(R.string.tips_to_avoid_damage), getString(R.string.tipsHairDamage),getString(R.string.description),getString(R.string.October_13_2021) ));
        listOfArticles.add(new ArticlesModel("12", R.drawable.hair_loss, getString(R.string.hair_loss), getString(R.string.detailsHairLoss), getString(R.string.tips_to_avoid_hair_loss), getString(R.string.tipsHairLoss),getString(R.string.description) ,getString(R.string.October_16_2021)));
        listOfArticles.add(new ArticlesModel("13", R.drawable.knee666, getString(R.string.knee_elbow), getString(R.string.detailsKneeElbow), getString(R.string.tips_for_knee_elbow), getString(R.string.tipsKneeElbow),getString(R.string.problem),getString(R.string.October_20_2021) ));
        listOfArticles.add(new ArticlesModel("14", R.drawable.nails1, getString(R.string.nails), getString(R.string.detailsNails), getString(R.string.tips_for_nails), getString(R.string.tipsNails), getString(R.string.problem),getString(R.string.October_25_2021)));
        listOfArticles.add(new ArticlesModel("15", R.drawable.normal_skin2, getString(R.string.normal_skin), getString(R.string.detailsNormalSkin), getString(R.string.tips_for_normal_skin), getString(R.string.tipsNormalSkin),getString(R.string.description) ,getString(R.string.October_3_2021)));
        listOfArticles.add(new ArticlesModel("16", R.drawable.oily_skin1,  getString(R.string.oily_skin), getString(R.string.detailsOilySkin), getString(R.string.tips_for_oily_skin), getString(R.string.tipsOilySkin), getString(R.string.description),getString(R.string.August_5_2021)));
        listOfArticles.add(new ArticlesModel("17", R.drawable.puffy_eye1,  getString(R.string.puffy_eyes), getString(R.string.detailsPuffyEyes),  getString(R.string.tips_to_overcome_it), getString(R.string.tipsPuffyEyes), getString(R.string.description),getString(R.string.August_8_2021)));
        listOfArticles.add(new ArticlesModel("18", R.drawable.skin5555,  getString(R.string.skin_care), getString(R.string.detailsSkinCare), getString(R.string.tips_for_skin_care), getString(R.string.tipsSkinCare), getString(R.string.description),getString(R.string.August_9_2021)));
        listOfArticles.add(new ArticlesModel("19", R.drawable.sudden_swollen_lips,  getString(R.string.sudden_swollen), getString(R.string.detailsSuddenSwollenLips),  getString(R.string.tips_to_overcome_it), getString(R.string.tipsSuddenSwollenLips), getString(R.string.description),getString(R.string.August_13_2021)));
        listOfArticles.add(new ArticlesModel("20", R.drawable.sunburned_lips,  getString(R.string.sunburned_lips), getString(R.string.detailsSunburnedLips),  getString(R.string.tips_to_overcome_it), getString(R.string.tipsSunburnedLips),getString(R.string.description),getString(R.string.August_19_2021) ));
        listOfArticles.add(new ArticlesModel("21", R.drawable.tzone,  getString(R.string.mixed_skin), getString(R.string.detailsMixedSkin), getString(R.string.tips_for_mixed_skin), getString(R.string.tipsMixedSkin),getString(R.string.description),getString(R.string.August_24_2021)));
        listOfArticles.add(new ArticlesModel("22", R.drawable.underarm, getString(R.string.underarm_area), getString(R.string.detailsUnderarmArea), getString(R.string.tips_for_underarm), getString(R.string.tipsUnderarmArea),getString(R.string.problem) ,getString(R.string.August_29_2021)));


        face = new ArrayList<ArticlesModel>();

        face.add(new ArticlesModel("3", R.drawable.dark_circle2,  getString(R.string.dark_circles),getString(R.string.detailsDark), getString(R.string.tips_to_overcome_it), getString(R.string.tipsDarkCircle),getString(R.string.description),getString(R.string.September_8_2021)));
        face.add(new ArticlesModel("8", R.drawable.fine_line2,  getString(R.string.fine_lines), getString(R.string.detailsFineLines), getString(R.string.tips_to_overcome_it), getString(R.string.tipsFineLine),getString(R.string.description),getString(R.string.October_2_2021)));
        face.add(new ArticlesModel("17", R.drawable.puffy_eye1,  getString(R.string.puffy_eyes), getString(R.string.detailsPuffyEyes), getString(R.string.tips_to_overcome_it), getString(R.string.tipsPuffyEyes), getString(R.string.description),getString(R.string.August_8_2021)));
        face.add(new ArticlesModel("6", R.drawable.chapped_lips3, getString(R.string.chapped_lips), getString(R.string.detailsChappedLips), getString(R.string.tips_to_overcome_it), getString(R.string.tipsChappedLips),getString(R.string.description),getString(R.string.September_20_2021) ));
        face.add(new ArticlesModel("7", R.drawable.cracked_corner_lips, getString(R.string.cracked_corner_lips), getString(R.string.detailsCrackedCornerLips), getString(R.string.tips_to_overcome_it), getString(R.string.tipsCrackedCornerLips), getString(R.string.description),getString(R.string.September_25_2021)));
        face.add(new ArticlesModel("19", R.drawable.sudden_swollen_lips,  getString(R.string.sudden_swollen), getString(R.string.detailsSuddenSwollenLips), getString(R.string.tips_to_overcome_it), getString(R.string.tipsSuddenSwollenLips), getString(R.string.description),getString(R.string.August_13_2021)));
        face.add(new ArticlesModel("20", R.drawable.sunburned_lips,  getString(R.string.sunburned_lips), getString(R.string.detailsSunburnedLips), getString(R.string.tips_to_overcome_it), getString(R.string.tipsSunburnedLips),getString(R.string.description),getString(R.string.August_19_2021)  ));
        face.add(new ArticlesModel("15", R.drawable.normal_skin2,  getString(R.string.normal_skin), getString(R.string.detailsNormalSkin), getString(R.string.tips_for_normal_skin), getString(R.string.tipsNormalSkin),getString(R.string.description),getString(R.string.October_3_2021) ));
        face.add(new ArticlesModel("16", R.drawable.oily_skin1, getString(R.string.oily_skin), getString(R.string.detailsOilySkin), getString(R.string.tips_for_oily_skin), getString(R.string.tipsOilySkin), getString(R.string.description),getString(R.string.August_5_2021)));
        face.add(new ArticlesModel("21", R.drawable.tzone, getString(R.string.mixed_skin), getString(R.string.detailsMixedSkin), getString(R.string.tips_for_mixed_skin), getString(R.string.tipsMixedSkin),getString(R.string.description),getString(R.string.August_24_2021) ));
        face.add(new ArticlesModel("5", R.drawable.dry_skin, getString(R.string.dry_skin), getString(R.string.detailsDrySkin), getString(R.string.tips_for_dry_skin), getString(R.string.tipsDrySkin), getString(R.string.description),getString(R.string.september_17_2021)));


           kids = new ArrayList<ArticlesModel>();
        kids.add(new ArticlesModel("18", R.drawable.skin_care, getString(R.string.skin_care), getString(R.string.detailsSkinCare), getString(R.string.tips_for_skin_care), getString(R.string.tipsSkinCare), getString(R.string.description),getString(R.string.August_9_2021)));
        kids.add(new ArticlesModel("10", R.drawable.hair_care, getString(R.string.hair_care), getString(R.string.detailsHairCare), getString(R.string.tips_for_hair_care), getString(R.string.tipsHairCare), getString(R.string.description),getString(R.string.October_10_2021)));
        kids.add(new ArticlesModel("2", R.drawable.diaper, getString(R.string.diaper_area), getString(R.string.detailsDiaperArea), getString(R.string.tips_for_diaper_area), getString(R.string.tipsDiaperArea),getString(R.string.description) ,getString(R.string.September_5_2021)));


          body = new ArrayList<ArticlesModel>();
        body.add(new ArticlesModel("9", R.drawable.foot_hand, getString(R.string.foot_hand), getString(R.string.detailsFootHand), getString(R.string.tips_for_healthy_foot_hand), getString(R.string.tipsFootHand),getString(R.string.problem),getString(R.string.October_6_2021)));
        body.add(new ArticlesModel("13", R.drawable.knee_elbow, getString(R.string.knee_elbow), getString(R.string.detailsKneeElbow), getString(R.string.tips_for_healthy_knee_elbow), getString(R.string.tipsKneeElbow),getString(R.string.problem),getString(R.string.October_20_2021) ));
        body.add(new ArticlesModel("14", R.drawable.nails1, getString(R.string.nails), getString(R.string.detailsNails), getString(R.string.tips_for_healthy_nails), getString(R.string.tipsNails), getString(R.string.problem),getString(R.string.October_25_2021)));
        body.add(new ArticlesModel("1", R.drawable.bikini62, getString(R.string.bikini_area), getString(R.string.detailsBikiniArea), getString(R.string.tips_for_bikini_area), getString(R.string.tipsBikiniArea),getString(R.string.problem),getString(R.string.September_30_2021) ));
        body.add(new ArticlesModel("22", R.drawable.underarm, getString(R.string.underarm_area), getString(R.string.detailsUnderarmArea), getString(R.string.tips_for_underarm_area), getString(R.string.tipsUnderarmArea),getString(R.string.problem),getString(R.string.August_29_2021)));

          hair  = new ArrayList<ArticlesModel>();

        hair.add(new ArticlesModel("4", R.drawable.dandruff, getString(R.string.dandruff),getString(R.string.detailsDandruff), getString(R.string.tips_to_avoid_dandruff), getString(R.string.tipsDandruff),getString(R.string.description) ,getString(R.string.september_12_2021)));
        hair.add(new ArticlesModel("11", R.drawable.hair_damage, getString(R.string.hair_damage), getString(R.string.detailsHairDamage), getString(R.string.tips_to_avoid_hair_damage), getString(R.string.tipsHairDamage),getString(R.string.description),getString(R.string.October_13_2021)));
        hair.add(new ArticlesModel("12", R.drawable.hair_loss, getString(R.string.hair_loss), getString(R.string.detailsHairLoss), getString(R.string.tips_to_avoid_hair_loss), getString(R.string.tipsHairLoss),getString(R.string.description),getString(R.string.October_13_2021)));


    }



}
