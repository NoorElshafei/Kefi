package com.gifting.kefi.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.data.local_database.AppRoomDatabase;
import com.gifting.kefi.data.model.ReelVideoModel;
import com.gifting.kefi.data.model.VideoModel;
import com.gifting.kefi.databinding.ItemPlayListBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ReelVideoModel> reelVideoModels;
    private AppRoomDatabase db;


    public PlayListAdapter(Context context) {
        this.context = context;
        this.reelVideoModels = new ArrayList<>();
    }

    public void setVideosData(ArrayList<ReelVideoModel> videoModels) {
        this.reelVideoModels = videoModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPlayListBinding binding = ItemPlayListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.binding.smallTitleText.setText(reelVideoModels.get(position).getSmallTitle());
        Glide.with(context).load(reelVideoModels.get(position).getImage()).into(holder.binding.itemImage);
    }

    @Override
    public int getItemCount() {
        return (reelVideoModels != null) ? reelVideoModels.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemPlayListBinding binding;

        public ViewHolder(ItemPlayListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
