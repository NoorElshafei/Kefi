package com.gifting.kefi.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.data.model.Article2Model;
import com.gifting.kefi.databinding.ItemArticleBinding;
import com.gifting.kefi.ui.activities.articles_details_.ArticleDetails2Activity;
import com.gifting.kefi.util.Language;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class LikedNaturalArticleAdapter extends RecyclerView.Adapter<LikedNaturalArticleAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Article2Model> articlesModels;


    public LikedNaturalArticleAdapter(Context context, ArrayList<Article2Model> articlesModels) {
        this.context = context;
        this.articlesModels = articlesModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemArticleBinding binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Language.changeBackDependsLanguage(binding.imageVideo,context);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.titleText.setText(articlesModels.get(position).getTitle());
        Glide.with(context.getApplicationContext()).load(articlesModels.get(position).getImageView()).into(holder.binding.itemImage);

        holder.binding.rootItem.setOnClickListener(v -> {
            Intent intent = new Intent(context, ArticleDetails2Activity.class);
            Article2Model article2Model = articlesModels.get(position);
            intent.putExtra("Article", article2Model);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (articlesModels != null) ? articlesModels.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       private final ItemArticleBinding binding;

        public ViewHolder(ItemArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
