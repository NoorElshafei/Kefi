package com.gifting.kefi.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.local_database.AppRoomDatabase;
import com.gifting.kefi.data.model.VideoModel;
import com.gifting.kefi.databinding.ItemVideoBinding;
import com.gifting.kefi.ui.activities.video_details_youtube.VideoDetailsYoutubeActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder> {

    private Context context;
    private ArrayList<VideoModel> videoModels;
    private AppRoomDatabase db;


    public VideosAdapter(Context context) {
        this.context = context;
        this.videoModels = new ArrayList<>();
        db = AppRoomDatabase.getDatabase(context.getApplicationContext());
    }

    public void setVideosData(ArrayList<VideoModel> videoModels) {
        this.videoModels = videoModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVideoBinding binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.durationText.setText(videoModels.get(position).getDuration());
        holder.binding.titleText.setText(videoModels.get(position).getTitle());
        holder.binding.smallTitleText.setText(videoModels.get(position).getSmallTitle());
        Glide.with(context.getApplicationContext()).load(videoModels.get(position).getImage()).apply(new RequestOptions().override(300, 450)).into(holder.binding.itemImage);


        holder.binding.rootItem.setOnClickListener(v -> {
            Intent intent = new Intent(context.getApplicationContext(), VideoDetailsYoutubeActivity.class);
            VideoModel videoModel = videoModels.get(position);
            intent.putExtra("VideoModel", videoModel);
            context.startActivity(intent);
        });

        if (db.userDao().findVideoById(videoModels.get(position).getId())) {
            holder.binding.favoriteImage.setImageResource(R.drawable.ic_love_video);
            holder.binding.favoriteImage.setTag("love");
        } else {
            holder.binding.favoriteImage.setImageResource(R.drawable.ic_unlove_video);
            holder.binding.favoriteImage.setTag("unLove");
        }

        holder.binding.favoriteImage.setOnClickListener(v -> {
            if (holder.binding.favoriteImage.getTag().equals("unLove")) {
                db.userDao().insertItem(videoModels.get(position));
                holder.binding.favoriteImage.setImageResource(R.drawable.ic_love_video);
                holder.binding.favoriteImage.setTag("love");
                Toast.makeText(context,context.getString( R.string.added_to_favourites), Toast.LENGTH_SHORT).show();
            } else {
                db.userDao().deleteById(videoModels.get(position).getId());
                holder.binding.favoriteImage.setImageResource(R.drawable.ic_unlove_video);
                holder.binding.favoriteImage.setTag("unLove");
                Toast.makeText(context,context.getString(R.string.removed_from_favourites), Toast.LENGTH_SHORT).show();
            }

        });
    }



    @Override
    public int getItemCount() {
        return (videoModels != null) ? videoModels.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemVideoBinding binding;

        public ViewHolder(ItemVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
