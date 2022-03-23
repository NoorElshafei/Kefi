package com.gifting.kefi.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.data.model.VideoModel;
import com.gifting.kefi.databinding.ItemVideosSmallBinding;
import com.gifting.kefi.ui.activities.video_details_youtube.VideoDetailsYoutubeActivity;
import com.gifting.kefi.util.Language;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HairVideosAdapter extends RecyclerView.Adapter<HairVideosAdapter.ViewHolder> {
    private Context context;
    private ArrayList<VideoModel> videoModels;


    public HairVideosAdapter(Context context) {
        this.context = context;
        videoModels = new ArrayList<>();
    }

    public void setVideoModels(ArrayList<VideoModel> videoModels) {
        this.videoModels = videoModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVideosSmallBinding binding = ItemVideosSmallBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.durationText.setText(videoModels.get(position).getDuration());
        holder.binding.titleText.setText(videoModels.get(position).getTitle());
        Glide.with(context.getApplicationContext()).load(videoModels.get(position).getImage()).into(holder.binding.itemImage);
        Language.changeBackDependsLanguage(holder.binding.imageVideo,context);
        holder.binding.rootItem.setOnClickListener(v -> {
            Intent intent = new Intent(context, VideoDetailsYoutubeActivity.class);
            intent.putExtra("VideoModel", videoModels.get(position));
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return (videoModels != null) ? videoModels.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemVideosSmallBinding binding;

        public ViewHolder(ItemVideosSmallBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
