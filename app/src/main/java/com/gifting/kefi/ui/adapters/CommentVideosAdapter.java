package com.gifting.kefi.ui.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.VideoCommentModel;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ItemCommentVideoBinding;
import com.gifting.kefi.databinding.ItemProgressBinding;
import com.gifting.kefi.ui.fragments.add_comment_reel_video.AddCommentReelVideoFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CommentVideosAdapter extends RecyclerView.Adapter<CommentVideosAdapter.ViewHolder> {

    private Context context;
    private ArrayList<VideoCommentModel> videoCommentModelArrayList;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private UserSharedPreference userSharedPreference;


    public CommentVideosAdapter(Context context) {
        this.context = context;
        this.videoCommentModelArrayList = new ArrayList<>();
        userSharedPreference = new UserSharedPreference(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            ItemCommentVideoBinding binding = ItemCommentVideoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);
        } else {
            ItemProgressBinding binding = ItemProgressBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM) {
            holder.binding1.optionCardView.setVisibility(View.GONE);
            holder.binding1.nameItem.setText(videoCommentModelArrayList.get(position).getUserName());
            holder.binding1.commentText.setText(videoCommentModelArrayList.get(position).getComment());
            String firstChar = videoCommentModelArrayList.get(position).getUserName().charAt(0) + "";
            holder.binding1.nameShortCut.setText(firstChar.toUpperCase());
            long millisecond = videoCommentModelArrayList.get(position).getCommentDate();
            String dateString = new SimpleDateFormat("dd MMM yyyy", Locale.forLanguageTag(userSharedPreference.getLanguage())).format(new Date(millisecond));
            holder.binding1.dateText.setText(dateString);


            if (videoCommentModelArrayList.get(position).getUserID().equals(userSharedPreference.getUserDetails().getId())) {
                holder.binding1.commentOption.setVisibility(View.VISIBLE);
            } else {
                holder.binding1.commentOption.setVisibility(View.GONE);
            }

            holder.binding1.commentOption.setOnClickListener(v -> {
                if (holder.binding1.optionCardView.getVisibility() == View.GONE)
                    holder.binding1.optionCardView.setVisibility(View.VISIBLE);
                else
                    holder.binding1.optionCardView.setVisibility(View.GONE);
            });

            holder.binding1.editCommentText.setOnClickListener(v -> {
                holder.binding1.optionCardView.setVisibility(View.GONE);

                Bundle bundle = new Bundle();
                AddCommentReelVideoFragment addCommentVideoFragment = AddCommentReelVideoFragment.newInstance();
                bundle.putString("VIDEO_ID", videoCommentModelArrayList.get(position).getVideoID());
                bundle.putString("COMMENT", videoCommentModelArrayList.get(position).getComment());
                bundle.putString("COMMENT_ID", videoCommentModelArrayList.get(position).getCommentID());
                bundle.putLong("VideoDate", videoCommentModelArrayList.get(position).getCommentDate());
                bundle.putString("EditVideo", "true");
                addCommentVideoFragment.setArguments(bundle);
                addCommentVideoFragment.show(((FragmentActivity) (context)).getSupportFragmentManager(), "addCommentFragment");
            });

            holder.binding1.deleteCommentText.setOnClickListener(v -> {
                holder.binding1.optionCardView.setVisibility(View.GONE);
                new AlertDialog.Builder(context)
                        .setTitle(context.getString(R.string.delete_comment))
                        .setMessage(context.getString(R.string.do_you_really_want_to_delete_Comment))
                        .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("VideoStructure").child("Comments");
                            reference.child(videoCommentModelArrayList.get(position).getVideoID()).child(videoCommentModelArrayList.get(position).getCommentID()).removeValue();
                            Toast.makeText(context, context.getString(R.string.comment_deleted_successfully), Toast.LENGTH_SHORT).show();
                            videoCommentModelArrayList.remove(position);
                            notifyDataSetChanged();

                        })
                        .setNegativeButton(android.R.string.no, null).show();
            });

        }

    }

    @Override
    public int getItemCount() {
        return videoCommentModelArrayList != null ? videoCommentModelArrayList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == videoCommentModelArrayList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemCommentVideoBinding binding1;
        private final ItemProgressBinding binding2;

        public ViewHolder(ItemCommentVideoBinding binding) {
            super(binding.getRoot());
            this.binding1 = binding;
            binding2 = null;
        }

        public ViewHolder(ItemProgressBinding binding) {
            super(binding.getRoot());
            this.binding2 = binding;
            binding1 = null;
        }
    }


    /*
 Helpers
 _________________________________________________________________________________________________
  */
    private String getShortCutOfName(String name) {
        StringBuilder shortCutName = new StringBuilder();
        String[] myName = name.split(" ");
        for (int i = 0; i < myName.length && i < 2; i++) {
            String s = myName[i].toUpperCase();
            shortCutName.append(s.charAt(0));
        }
        return shortCutName.toString();
    }

    public void add(VideoCommentModel videoCommentModel) {
        videoCommentModelArrayList.add(videoCommentModel);
        notifyItemInserted(videoCommentModelArrayList.size() - 1);
    }

    public void addAll(ArrayList<VideoCommentModel> mcList) {
        for (VideoCommentModel videoCommentModel : mcList) {
            add(videoCommentModel);
        }
    }

    public void remove(VideoCommentModel videoCommentModel) {
        int position = videoCommentModelArrayList.indexOf(videoCommentModel);
        if (position > -1) {
            videoCommentModelArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }


    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new VideoCommentModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = videoCommentModelArrayList.size() - 1;
        VideoCommentModel item = getItem(position);

        if (item != null) {
            videoCommentModelArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public VideoCommentModel getItem(int position) {
        return videoCommentModelArrayList.get(position);
    }

}
