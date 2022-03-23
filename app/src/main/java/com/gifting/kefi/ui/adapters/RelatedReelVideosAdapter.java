package com.gifting.kefi.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.data.model.ReelVideoModel;
import com.gifting.kefi.databinding.ItemRelatedVideoBinding;
import com.gifting.kefi.ui.activities.video_details.VideoDetailsActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RelatedReelVideosAdapter extends RecyclerView.Adapter<RelatedReelVideosAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ReelVideoModel> reelVideoModels;


    public RelatedReelVideosAdapter(Context context) {
        this.context = context;
        reelVideoModels = new ArrayList<>();
    }

    public void setRelatedVideo(ArrayList<ReelVideoModel> reelVideoModels) {
        this.reelVideoModels = reelVideoModels;
        notifyDataSetChanged();
    }

    public void removeAllData() {
        reelVideoModels.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRelatedVideoBinding binding = ItemRelatedVideoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.durationText.setText(reelVideoModels.get(position).getDuration());
        holder.binding.titleText.setText(reelVideoModels.get(position).getTitle());
        holder.binding.favoriteImage.setVisibility(View.INVISIBLE);
        Glide.with(context).load(reelVideoModels.get(position).getImage()).into(holder.binding.itemImage);
        holder.binding.rootItem.setOnClickListener(v -> {
            Intent intent = new Intent(context.getApplicationContext(), VideoDetailsActivity.class);
            intent.putExtra("REEL_VIDEO", reelVideoModels.get(position));
            context.startActivity(intent);

        });

        /*holder.binding.favoriteImage.setOnClickListener(v -> {
            if (holder.binding.favoriteImage.getTag().equals("unLove")) {
                holder.binding.favoriteImage.setImageResource(R.drawable.ic_love_video_small);
                holder.binding.favoriteImage.setTag("love");
            }
            else {
                holder.binding.favoriteImage.setImageResource(R.drawable.ic_unlove_video_small);
                holder.binding.favoriteImage.setTag("unLove");

            }

        });*/

    }

    @Override
    public int getItemCount() {
        return (reelVideoModels != null) ? reelVideoModels.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemRelatedVideoBinding binding;

        public ViewHolder(ItemRelatedVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
