package com.gifting.kefi.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.data.model.NotificationModel;
import com.gifting.kefi.databinding.ItemNotificationBinding;
import com.gifting.kefi.ui.activities.product_details.ProductDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<NotificationModel> notificationModels;


    public NotificationsAdapter(Context context) {
        this.context = context;
    }

    public void setNotificationModels(ArrayList<NotificationModel> notificationModels) {
        this.notificationModels = notificationModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNotificationBinding binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.messageText.setText(notificationModels.get(position).getMessage());
        String dateString = new SimpleDateFormat("hh:mm aaa").format(new Date(notificationModels.get(position).getTime_created()));
        holder.binding.timeText.setText(dateString);

        holder.binding.constraintLayout3.setOnClickListener(v -> {
            if (notificationModels.get(position).getTable_name().equals("Products")) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("product_id",notificationModels.get(position).getId_table());
                context.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return (notificationModels != null) ? notificationModels.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemNotificationBinding binding;

        public ViewHolder(ItemNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
