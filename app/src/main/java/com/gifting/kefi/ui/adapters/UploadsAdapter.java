package com.gifting.kefi.ui.adapters;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.local_database.AppRoomDatabase;
import com.gifting.kefi.data.model.UploadDetailsModel;
import com.gifting.kefi.databinding.ItemUploadBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UploadsAdapter extends RecyclerView.Adapter<UploadsAdapter.ViewHolder> {

    private Context context;
    private List<UploadDetailsModel> uploadDetailsModels;
    private static int p1;
    private int count = 0;

    public UploadsAdapter(Context context) {
        this.context = context;
        this.uploadDetailsModels = new ArrayList<>();
    }

    public void setUploadTasks(List<UploadDetailsModel> uploadTasks) {
        this.uploadDetailsModels = uploadTasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUploadBinding binding = ItemUploadBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.titleText.setText(uploadDetailsModels.get(position).getReelVideoModel().getTitle());

        if (holder.binding.pausePlayImage.getTag().equals("play")) {
            holder.binding.pausePlayImage.setImageResource(R.drawable.ic_play_upload);

        } else if (holder.binding.pausePlayImage.getTag().equals("pause")) {
            holder.binding.pausePlayImage.setImageResource(R.drawable.ic_puase_upload);
        }
        holder.binding.pausePlayImage.setOnClickListener(v -> {
            if (holder.binding.pausePlayImage.getTag().equals("pause")) {
                uploadDetailsModels.get(position).getUploadTask().resume();
                holder.binding.pausePlayImage.setImageResource(R.drawable.ic_puase_upload);
                holder.binding.pausePlayImage.setTag("pause");
            } else if (holder.binding.pausePlayImage.getTag().equals("play")) {
                uploadDetailsModels.get(position).getUploadTask().pause();
                holder.binding.pausePlayImage.setImageResource(R.drawable.ic_play_upload);
                holder.binding.pausePlayImage.setTag("play");
            }
        });

        holder.binding.cancelImage.setOnClickListener(v -> {
            uploadDetailsModels.get(position).getUploadTask().cancel();
            AppRoomDatabase.getDatabase(context).userDao().delete(uploadDetailsModels.get(position).getReelVideoModel());
            AppRoomDatabase.getDatabase(context).userDao().delete(uploadDetailsModels.get(position).getUploadsModel());
            uploadDetailsModels.remove(position);
            count++;
            notifyDataSetChanged();

        });

        uploadDetailsModels.get(position).getUploadTask().addOnSuccessListener(command -> {
            if (position - count < 0) {
                p1 = 0;
            } else {
                p1 = position - count;
            }
            uploadDetailsModels.get(p1).getStorageReference().getDownloadUrl().addOnSuccessListener(videoUrl -> {
                if (uploadDetailsModels.size() > 0) {
                    if (!uploadDetailsModels.get(p1).getUploadTask().isPaused() && !uploadDetailsModels.get(p1).getUploadTask().isCanceled()) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(uploadDetailsModels.get(p1).getUploadsModel().getVideoId());
                        uploadDetailsModels.get(p1).getReelVideoModel().setLink(videoUrl.toString());
                        uploadDetailsModels.get(p1).getReelVideoModel().setId(databaseReference.getKey());
                        databaseReference.setValue(uploadDetailsModels.get(p1).getReelVideoModel());
                        AppRoomDatabase.getDatabase(context).userDao().delete(uploadDetailsModels.get(p1).getReelVideoModel());
                        AppRoomDatabase.getDatabase(context).userDao().delete(uploadDetailsModels.get(p1).getUploadsModel());
                        uploadDetailsModels.remove(p1);
                        count++;
                        notifyDataSetChanged();

                    }
                }
            });

        }).addOnProgressListener(command -> {
            holder.binding.pausePlayImage.setImageResource(R.drawable.ic_puase_upload);
            long progress = 100 * command.getBytesTransferred() / command.getTotalByteCount();
            holder.binding.progressBar.setProgress((int) progress);
            holder.binding.progressText.setText(((int) progress) + "");

            //setProgressAnimate(holder.binding.progressBar, (int) progress);
            //setProgressAnimateText(holder.binding.progressText, (int) progress);
        }).addOnPausedListener(snapshot -> {
            holder.binding.pausePlayImage.setImageResource(R.drawable.ic_play_upload);
        }).addOnFailureListener(command -> {
            Toast.makeText(context, position + command.getMessage(), Toast.LENGTH_SHORT).show();
            AppRoomDatabase.getDatabase(context).userDao().delete(uploadDetailsModels.get(position).getReelVideoModel());
            AppRoomDatabase.getDatabase(context).userDao().delete(uploadDetailsModels.get(position).getUploadsModel());
        });
    }

    @Override
    public int getItemCount() {
        return (uploadDetailsModels != null) ? uploadDetailsModels.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemUploadBinding binding;

        public ViewHolder(ItemUploadBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    private void setProgressAnimate(ProgressBar pb, int progressTo) {
        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", pb.getProgress(), progressTo);
        if (progressTo - pb.getProgress() < 5) {
            animation.setDuration(5500);
        } else if (progressTo - pb.getProgress() < 10) {
            animation.setDuration(10500);
        } else if (progressTo - pb.getProgress() < 30) {
            animation.setDuration(15500);
        } else if (progressTo - pb.getProgress() < 40) {
            animation.setDuration(25500);
        } else if (progressTo - pb.getProgress() > 40) {
            animation.setDuration(40500);
        }

        animation.setAutoCancel(true);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (pb.getProgress() == 100) {
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    private void setProgressAnimateText(TextView textView, int textTo) {
        int from = Integer.parseInt(textView.getText().toString());
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(from, textTo);// here you set the range, from 0 to "count" value
        animator.addUpdateListener(animation -> textView.setText(String.valueOf(animation.getAnimatedValue())));
        if (textTo - from < 5) {
            animator.setDuration(5000);
        } else if (textTo - from < 10) {
            animator.setDuration(10000);
        } else if (textTo - from < 30) {
            animator.setDuration(15000);
        } else if (textTo - from < 40) {
            animator.setDuration(25000);
        } else if (textTo - from > 40) {
            animator.setDuration(40000);
        }
        animator.start();
    }
}
