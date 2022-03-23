package com.gifting.kefi.ui.activities.articles_details_;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.gifting.kefi.R;
import com.gifting.kefi.data.local_database.AppRoomDatabase;
import com.gifting.kefi.data.model.ArticlesModel;
import com.gifting.kefi.data.model.RecentlyModel;
import com.gifting.kefi.databinding.ActivityArticleDetailsBinding;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;
import com.gifting.kefi.util.Language;

import java.util.List;

public class ArticleDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityArticleDetailsBinding binding;
    private ArticlesModel articlesModel;
    List<RecentlyModel> recentlyModelList;
    AppRoomDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_article_details);


        Language.changeBackDependsLanguage(binding.backImage, this);

        db = AppRoomDatabase.getDatabase(this);
        if (getIntent().getExtras().getParcelable("FeaturedArticlesModel") != null) {
            articlesModel = getIntent().getExtras().getParcelable("FeaturedArticlesModel");
            recentlyModelList = db.userDao().getRecentlyModel();


            if (!db.userDao().findArticleById(articlesModel.getId())) {
                if (recentlyModelList.size() >= 5) {
                    db.userDao().delete(recentlyModelList.get(0));
                }

            } else {
                RecentlyModel recentlyModel = db.userDao().getRecentlyModel(articlesModel.getId());
                db.userDao().delete(recentlyModel);
                recentlyModel.setRecently_id(null);

            }
            insert(articlesModel.getId(), articlesModel.getImageView(), articlesModel.getArticle(), articlesModel.getDescription(), articlesModel.getTips(), articlesModel.getDescription_tips(), articlesModel.getSecondArticle());
        } else {
            articlesModel = getIntent().getExtras().getParcelable("RecentlyArticleModel");
        }


        binding.textView122.setText(articlesModel.getDescription());
        binding.textView124.setText(articlesModel.getTips());
        binding.textView123.setText(articlesModel.getDescription_tips());
        binding.image.setImageResource(articlesModel.getImageView());
        binding.textView121.setText(articlesModel.getArticle());
        binding.textView125.setText(articlesModel.getSecondArticle());


        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);
        binding.back.setOnClickListener(this);
        binding.textView123.setVisibility(View.GONE);
        binding.linearLayout12.setVisibility(View.GONE);


        binding.readMoreLinear.setOnClickListener(v -> {
            // check in the text in button  if equal =read more do this else do else statement
            if (binding.readMoreText.getText().toString().equals(getString(R.string.read_more))) {
                // for show text
                binding.textView125.setVisibility(View.VISIBLE);
                binding.textView122.setVisibility(View.VISIBLE);
                binding.textView123.setVisibility(View.VISIBLE);
                binding.linearLayout12.setVisibility(View.VISIBLE);


                // for show button
                binding.readMoreLinear.setVisibility(View.VISIBLE);
                // for show max of text when press in button
                binding.textView122.setMaxLines(Integer.MAX_VALUE);
                // when click in read more change the text to read less
                binding.readMoreText.setText(R.string.read_less);


            } else {
                //  i want still show button
                binding.readMoreLinear.setVisibility(View.VISIBLE);
                binding.textView123.setVisibility(View.GONE);
                binding.linearLayout12.setVisibility(View.GONE);
                // when click in read less change the text to read more
                binding.readMoreText.setText(getString(R.string.read_more));
                // less the text to the max line to 3 line

                binding.textView122.setMaxLines(3);

            }

        });


    }


    @Override
    public void onClick(View v) {
        if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.notificationLayout) {
            startActivity(new Intent(this, NotificationsActivity.class));
        } else if (v == binding.back) {
            onBackPressed();
        }
    }

    private void insert(String recently_id, int imageView, String article, String description, String tips, String description_tips, String secondArticle) {
        db = AppRoomDatabase.getDatabase(this);
        RecentlyModel recentlyModel = new RecentlyModel();
        recentlyModel.setRecently_id(recently_id);
        recentlyModel.setImageView(imageView);
        recentlyModel.setArticle(article);
        recentlyModel.setDescription(description);
        recentlyModel.setTips(tips);
        recentlyModel.setDescription_tips(description_tips);
        recentlyModel.setSecondArticle(secondArticle);

        db.userDao().insertRecentlyModel(recentlyModel);

    }


}


