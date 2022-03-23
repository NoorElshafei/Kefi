package com.gifting.kefi.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.local_database.AppRoomDatabase;
import com.gifting.kefi.data.model.ArticlesModel;
import com.gifting.kefi.ui.activities.articles_details_.ArticleDetailsActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FeaturedArticleAdapter extends RecyclerView.Adapter<FeaturedArticleAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ArticlesModel> listOfArticles;
    private AppRoomDatabase db;


    public FeaturedArticleAdapter(Context context, ArrayList<ArticlesModel> listOfArticles) {
        this.context = context;
        this.listOfArticles = listOfArticles;
        db = AppRoomDatabase.getDatabase(context.getApplicationContext());


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View finishItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_features_article1, parent, false);
        return new ViewHolder(finishItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.smallText.setText(listOfArticles.get(position).getArticle());
        holder.date_article.setText(listOfArticles.get(position).getDate_of_article());
        Glide.with(context.getApplicationContext()).load(listOfArticles.get(position).getImageView()).into(holder.item_features_image);

        holder.constraintLayout.setOnClickListener(v -> {

            Intent intent = new Intent(context.getApplicationContext(), ArticleDetailsActivity.class);
            intent.putExtra("FeaturedArticlesModel", listOfArticles.get(position));
            context.startActivity(intent);

        });


        if (db.userDao().findFavoriteArticleById(listOfArticles.get(position).getId())) {
            holder.favoriteImage.setImageResource(R.drawable.ic_love_video);
            holder.favoriteImage.setTag("love");
        } else {
            holder.favoriteImage.setImageResource(R.drawable.ic_unlove_video);
            holder.favoriteImage.setTag("unLove");
        }

        holder.favoriteImage.setOnClickListener(v -> {
            if (holder.favoriteImage.getTag().equals("unLove")) {
                db.userDao().insertItem(listOfArticles.get(position));
                holder.favoriteImage.setImageResource(R.drawable.ic_love_video);
                holder.favoriteImage.setTag("love");
                Toast.makeText(context,context.getString(R.string.added_to_favourites), Toast.LENGTH_SHORT).show();
            } else {
                db.userDao().deleteArticleById(listOfArticles.get(position).getId());
                holder.favoriteImage.setImageResource(R.drawable.ic_unlove_video);
                holder.favoriteImage.setTag("unLove");
                Toast.makeText(context,context.getString(R.string.removed_from_favourites), Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public int getItemCount() {
        return (listOfArticles != null) ? listOfArticles.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout constraintLayout;
        TextView description, tips, description_tips, smallText, second_article,date_article;
        ImageView item_features_image, favoriteImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            description = itemView.findViewById(R.id.textView122);
            tips = itemView.findViewById(R.id.textView124);
            description_tips = itemView.findViewById(R.id.textView123);
            second_article = itemView.findViewById(R.id.textView125);
            smallText = itemView.findViewById(R.id.small_text);
            item_features_image = itemView.findViewById(R.id.item_features_image);
            favoriteImage = itemView.findViewById(R.id.favorite_image);
            date_article=itemView.findViewById(R.id.data_text);

        }
    }
}
