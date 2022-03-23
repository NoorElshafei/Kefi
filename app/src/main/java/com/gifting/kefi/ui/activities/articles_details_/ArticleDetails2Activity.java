package com.gifting.kefi.ui.activities.articles_details_;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.gifting.kefi.R;
import com.gifting.kefi.data.local_database.AppRoomDatabase;
import com.gifting.kefi.data.model.Article2Model;
import com.gifting.kefi.databinding.ActivityArticleDetails2Binding;
import com.bumptech.glide.Glide;

public class ArticleDetails2Activity extends AppCompatActivity {
    private ActivityArticleDetails2Binding binding;
    private Article2Model article2Model;
    private AppRoomDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_article_details2);


        db = AppRoomDatabase.getDatabase(getApplicationContext());

        if (getIntent().getParcelableExtra("Article") != null) {
            article2Model = getIntent().getParcelableExtra("Article");
            setUI(article2Model);
        }

        if (db.userDao().findFavoriteArticle2ById(article2Model.getId())) {
            binding.favouriteImage.setImageResource(R.drawable.ic_favourite_big);
            binding.favouriteImage.setTag("love");

        } else {
            binding.favouriteImage.setImageResource(R.drawable.ic_un_favourite_big);
            binding.favouriteImage.setTag("unLove");
        }

        binding.addFavouriteLayout.setOnClickListener(v -> {
            if (binding.favouriteImage.getTag().equals("unLove")) {
                db.userDao().insertItem(article2Model);
                binding.favouriteImage.setImageResource(R.drawable.ic_favourite_big);
                binding.favouriteImage.setTag("love");
                Toast.makeText(this,getString( R.string.added_to_favourites), Toast.LENGTH_SHORT).show();
            } else {
                db.userDao().deleteArticle2ById(article2Model.getId());
                binding.favouriteImage.setImageResource(R.drawable.ic_un_favourite_big);
                binding.favouriteImage.setTag("unLove");
                Toast.makeText(this, getString(R.string.removed_from_favourites), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setUI(Article2Model articleModel) {
        binding.textView124.setText(articleModel.getSmall_title());
        binding.textView121.setText(articleModel.getTitle());
        binding.textView123.setText(articleModel.getDescription());
        Glide.with(getApplicationContext()).load(articleModel.getImageView()).into(binding.image);
    }
}