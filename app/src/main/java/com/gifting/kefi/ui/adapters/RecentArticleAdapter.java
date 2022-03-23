package com.gifting.kefi.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.ArticlesModel;
import com.gifting.kefi.data.model.RecentlyModel;
import com.gifting.kefi.ui.activities.articles_details_.ArticleDetailsActivity;
import com.bumptech.glide.Glide;

import java.util.List;

public class RecentArticleAdapter extends RecyclerView.Adapter<RecentArticleAdapter.ViewHolder> {

    private Context context;
    List<RecentlyModel> recentlyModelList;


    public RecentArticleAdapter(Context context, List<RecentlyModel> recentlyModelList) {
        this.context = context;
        this.recentlyModelList = recentlyModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View finishItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_recenlty_article1, parent, false);
        return new ViewHolder(finishItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     /*   holder.cardView.setOnClickListener(v -> {
            context.startActivity(new Intent(context, ArticleDetailsActivity.class));
        });
      */
        holder.title.setText(recentlyModelList.get(position).getArticle());
        Glide.with(context.getApplicationContext()).load(recentlyModelList.get(position).getImageView()).into(holder.item_recently_image);
        holder.description_recently.setText(recentlyModelList.get(position).getDescription());


        holder.cardView.setOnClickListener(v -> {

            Intent intent = new Intent(context, ArticleDetailsActivity.class);
            ArticlesModel articlesModel = new ArticlesModel(recentlyModelList.get(position).getRecently_id(), recentlyModelList.get(position).getImageView(), recentlyModelList.get(position).getArticle(), recentlyModelList.get(position).getDescription(), recentlyModelList.get(position).getTips(), recentlyModelList.get(position).getDescription_tips(), recentlyModelList.get(position).getSecondArticle(),"");
            intent.putExtra("RecentlyArticleModel", articlesModel);
            context.startActivity(intent);


        });


    }

    @Override
    public int getItemCount() {
        if (recentlyModelList != null)
            return recentlyModelList.size();
        else
            return 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView description;
        TextView tips;
        TextView description_tips;
        TextView article;
        TextView second_article;
        ImageView item_features_image;
        TextView description_recently;
        TextView title;
        ImageView item_recently_image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView4);
            description_recently = itemView.findViewById(R.id.textView120);
            title = itemView.findViewById(R.id.textView119);
            description = itemView.findViewById(R.id.textView122);
            tips = itemView.findViewById(R.id.textView124);
            description_tips = itemView.findViewById(R.id.textView123);
            second_article = itemView.findViewById(R.id.textView125);
            article = itemView.findViewById(R.id.small_text);
            item_features_image = itemView.findViewById(R.id.item_features_image);
            item_recently_image = itemView.findViewById(R.id.recently_imageView);
        }
    }
}
