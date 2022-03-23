package com.gifting.kefi.ui.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.ReelVideoModel;
import com.gifting.kefi.databinding.ItemMyVideosBinding;
import com.gifting.kefi.ui.activities.video_details.VideoDetailsActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ReelVideoModel> reelVideoModels;
    private String isRecentlyOrMost;
    private RecentlyOrMost recentlyOrMostInterface;


    public ProfileAdapter(Context context, RecentlyOrMost recentlyOrMostInterface) {
        this.context = context;
        this.recentlyOrMostInterface = recentlyOrMostInterface;
        reelVideoModels = new ArrayList<>();
        isRecentlyOrMost = "";
    }

    public ProfileAdapter(Context context) {
        this.context = context;
        reelVideoModels = new ArrayList<>();
        isRecentlyOrMost = "";
    }

    public void setIsRecentlyOrMost(String isRecentlyOrMost) {
        this.isRecentlyOrMost = isRecentlyOrMost;
    }

    public void setReelVideoModels(ArrayList<ReelVideoModel> reelVideoModels) {
        this.reelVideoModels = reelVideoModels;
        notifyDataSetChanged();
    }

    public void removeAllData() {
        reelVideoModels.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMyVideosBinding binding = ItemMyVideosBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String[] tagsArray = reelVideoModels.get(position).getVideoTags().split("\\t|,|;|\\.|\\?|!|-|:|@|\\[|\\]|\\(|\\)|\\{|\\}|_|\\*|/");
        holder.binding.optionCardView.setVisibility(View.GONE);
        holder.binding.titleText.setText(reelVideoModels.get(position).getTitle());
        holder.binding.nameText.setText(reelVideoModels.get(position).getUserName());
        holder.binding.viewsText.setText(" "+reelVideoModels.get(position).getTotal_views() +" "+ context.getString(R.string.views));
        holder.binding.durationText.setText(reelVideoModels.get(position).getDuration());

        if (tagsArray.length < 3) {
            holder.binding.tag1.setText("#" + tagsArray[1]);

            ViewGroup.LayoutParams params = holder.binding.tag2Layout.getLayoutParams();
            params.height = 0;
            params.width = 0;
            holder.binding.tag2Layout.setLayoutParams(params);
        } else {
            holder.binding.tag1.setText("#" + tagsArray[1]);
            holder.binding.tag2.setText("#" + tagsArray[2]);
        }


        Glide.with(context.getApplicationContext()).load(reelVideoModels.get(position).getImage()).into(holder.binding.imageItem);
        Glide.with(context.getApplicationContext()).load(reelVideoModels.get(position).getUserImage()).placeholder(R.drawable.avatar).into(holder.binding.profileImageItem);

        holder.binding.rootItem.setOnClickListener(v -> {
            Intent intent = new Intent(context.getApplicationContext(), VideoDetailsActivity.class);
            intent.putExtra("REEL_VIDEO", reelVideoModels.get(position));
            context.startActivity(intent);
        });

        holder.binding.optionImage.setOnClickListener(v -> {
            if (holder.binding.optionCardView.getVisibility() == View.GONE)
                holder.binding.optionCardView.setVisibility(View.VISIBLE);
            else
                holder.binding.optionCardView.setVisibility(View.GONE);

        });

        holder.binding.deleteVideoText.setOnClickListener(v -> {
            holder.binding.optionCardView.setVisibility(View.GONE);
            new AlertDialog.Builder(context)
                    .setTitle(R.string.delete_video)
                    .setMessage(R.string.do_you_really_want_to_delete_video)
                    .setIcon(context.getApplicationContext().getDrawable(R.drawable.ic_trush))
                    .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ReelVideos");
                        reference.child(reelVideoModels.get(position).getUser_id()).child(reelVideoModels.get(position).getId()).removeValue().addOnCompleteListener(aVoid -> {
                            Toast.makeText(context.getApplicationContext(), context.getString(R.string.video_deleted_successfully), Toast.LENGTH_SHORT).show();
                            //reelVideoModels.remove(position);
                            //notifyDataSetChanged();
                           /* if (isRecentlyOrMost.equals("recently")) {
                               // recentlyOrMostInterface.isRecentlyOrMost("recently");
                            } else if (isRecentlyOrMost.equals("most")) {
                               // recentlyOrMostInterface.isRecentlyOrMost("most");
                            }*/
                        });

                    })
                    .setNegativeButton(android.R.string.no, null).show();

        });


    }

    @Override
    public int getItemCount() {
        return reelVideoModels != null ? reelVideoModels.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemMyVideosBinding binding;

        public ViewHolder(ItemMyVideosBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface RecentlyOrMost {
        void isRecentlyOrMost(String isRecentlyOrMostString);
    }
}


