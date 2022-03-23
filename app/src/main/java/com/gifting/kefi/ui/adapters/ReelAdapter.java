package com.gifting.kefi.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.ReelVideoModel;
import com.gifting.kefi.databinding.ItemReelBinding;
import com.gifting.kefi.ui.activities.video_details.VideoDetailsActivity;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReelAdapter extends RecyclerView.Adapter<ReelAdapter.ViewHolder> {

    private Context context;
    private Fragment fragment;
    private ArrayList<ReelVideoModel> reelVideoModels;


    public ReelAdapter(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        reelVideoModels = new ArrayList<>();
    }

    public void setReelAdapters(ArrayList<ReelVideoModel> reelAdapters) {
        this.reelVideoModels = reelAdapters;
        notifyDataSetChanged();
    }

    public void removeAllData() {
        reelVideoModels.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReelBinding binding = ItemReelBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.titleText.setText(reelVideoModels.get(position).getTitle());
        holder.binding.nameText.setText(reelVideoModels.get(position).getUserName());
        holder.binding.durationText.setText(reelVideoModels.get(position).getDuration());
        Glide.with(context.getApplicationContext()).load(reelVideoModels.get(position).getImage()).into(holder.binding.thumbImage);
        Glide.with(context.getApplicationContext()).load(reelVideoModels.get(position).getUserImage()).placeholder(R.drawable.avatar).into(holder.binding.imageProfile);
        holder.binding.timeText.setText(formatDate(reelVideoModels.get(position).getTime_created()));
        holder.binding.viewsText.setText(reelVideoModels.get(position).getTotal_views() + context.getString(R.string.views));

        holder.binding.itemReel.setOnClickListener(v -> {
            Intent intent = new Intent(context.getApplicationContext(), VideoDetailsActivity.class);
            intent.putExtra("REEL_VIDEO", reelVideoModels.get(position));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return (reelVideoModels != null) ? reelVideoModels.size() : 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemReelBinding binding;

        public ViewHolder(ItemReelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private String formatDate(long dateMillisecond) {
        long timeNow = Calendar.getInstance().getTimeInMillis() - dateMillisecond;
        if (timeNow < 60000) {
            return "Just Now";
        }// less than hour
        else if (timeNow < 3600000) {
            return (timeNow / 60000) + " min";
        }// less than day
        else if (timeNow < 86400000) {
            return (timeNow / 3600000) + "hr";
        }// between 1 to 4 days
        else if (timeNow >= 86400000 && timeNow < 345600000) {
            return (timeNow / (1000 * 60 * 60 * 24)) + "d";
        } else {
            String dateString = new SimpleDateFormat("d MMM").format(new Date(dateMillisecond));
            return dateString;
        }
    }
}
