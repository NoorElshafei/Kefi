package com.gifting.kefi.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.local_database.AppRoomDatabase;
import com.gifting.kefi.data.model.VideoModel;
import com.gifting.kefi.databinding.ItemRelatedVideoBinding;
import com.gifting.kefi.ui.activities.video_details_youtube.VideoDetailsYoutubeActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RelatedVideosAdapter extends RecyclerView.Adapter<RelatedVideosAdapter.ViewHolder> {

    private Context context;
    private ArrayList<VideoModel> videoModels;
    private AppRoomDatabase db;


    public RelatedVideosAdapter(Context context) {
        this.context = context;
        videoModels = new ArrayList<>();
        db = AppRoomDatabase.getDatabase(context.getApplicationContext());
    }

    public void setRelatedVideo(ArrayList<VideoModel> videoModels) {
        this.videoModels = videoModels;
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
        holder.binding.durationText.setText(videoModels.get(position).getDuration());
        holder.binding.titleText.setText(videoModels.get(position).getTitle());
        Glide.with(context).load(videoModels.get(position).getImage()).into(holder.binding.itemImage);
        holder.binding.rootItem.setOnClickListener(v -> {
            Intent intent = new Intent(context, VideoDetailsYoutubeActivity.class);
            intent.putExtra("VideoModel", videoModels.get(position));
            context.startActivity(intent);
        });

        if (db.userDao().findVideoById(videoModels.get(position).getId())) {
            holder.binding.favoriteImage.setImageResource(R.drawable.ic_love_video_small);
            holder.binding.favoriteImage.setTag("love");
        } else {
            holder.binding.favoriteImage.setImageResource(R.drawable.ic_unlove_video_small);
            holder.binding.favoriteImage.setTag("unLove");
        }
        holder.binding.favoriteImage.setOnClickListener(v -> {
            if (holder.binding.favoriteImage.getTag().equals("unLove")) {
                db.userDao().insertItem(videoModels.get(position));
                holder.binding.favoriteImage.setImageResource(R.drawable.ic_love_video_small);
                holder.binding.favoriteImage.setTag("love");
            }
            else {
                db.userDao().deleteById(videoModels.get(position).getId());

                holder.binding.favoriteImage.setImageResource(R.drawable.ic_unlove_video_small);
                holder.binding.favoriteImage.setTag("unLove");

            }

        });





    }

    @Override
    public int getItemCount() {
        return (videoModels != null) ? videoModels.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemRelatedVideoBinding binding;

        public ViewHolder(ItemRelatedVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
