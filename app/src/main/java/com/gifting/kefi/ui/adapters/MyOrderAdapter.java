package com.gifting.kefi.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.OrderRequestModel;
import com.gifting.kefi.data.model.RoomCarts;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.ui.activities.checkout.CheckOutActivity;
import com.gifting.kefi.ui.activities.track_order.TrackOrderActivity;
import com.gifting.kefi.ui.fragments.rate_order_bottom_sheet.RateOrderFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    private Context context;
    private FragmentManager fragmentManager;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private ArrayList<OrderRequestModel> orderRequestModels;
    private boolean isLoadingAdded = false;
    private UserSharedPreference userSharedPreference;


    public MyOrderAdapter(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.orderRequestModels = new ArrayList<>();
        this.userSharedPreference = new UserSharedPreference(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View finishItemLayoutView;
        if (viewType == ITEM) {
            finishItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_order, parent, false);
        } else {
            finishItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_progress, parent, false);
        }
        return new ViewHolder(finishItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (getItemViewType(position) == ITEM) {
            String longV = orderRequestModels.get(position).getOrderTime();
            long millisecond = Long.parseLong(longV);
            String dateString = new SimpleDateFormat("dd MMMM yyyy  HH:mm", Locale.forLanguageTag(userSharedPreference.getLanguage())).format(new Date(millisecond));
            holder.dateText.setText(dateString);
            holder.orderId.setText(context.getString(R.string.order_id_414668357) + longV);

            switch (orderRequestModels.get(position).getOrderStatus()) {
                case "inProgress":
                    holder.orderStatusImage.setImageResource(R.drawable.ic_inprogress_order);
                    holder.reorderLayout.setVisibility(View.GONE);
                    holder.rateLayout.setVisibility(View.GONE);
                    holder.trackOrder.setVisibility(View.VISIBLE);
                    break;
                case "canceled":
                    holder.orderStatusImage.setImageResource(R.drawable.ic_cancel_order);
                    break;
                case "finished":
                    holder.orderStatusImage.setImageResource(R.drawable.ic_finished_order);
                    break;
            }

            holder.rateLayout.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                RateOrderFragment rateOrderFragment = RateOrderFragment.newInstance();
                bundle.putString("ORDER_ID", orderRequestModels.get(position).getRequestId());
                bundle.putString("USER_NAME", orderRequestModels.get(position).getNameOfUser());
                rateOrderFragment.setArguments(bundle);
                rateOrderFragment.show(fragmentManager, "rateOrderFragment");
            });
            holder.trackOrder.setOnClickListener(v -> {
                Intent intent = new Intent(context, TrackOrderActivity.class);
                intent.putExtra("ORDER_ID", orderRequestModels.get(position).getOrderStatus());
                context.startActivity(intent);
            });

            holder.reorderLayout.setOnClickListener(v -> {
                Intent intent = new Intent(context, CheckOutActivity.class);
                ArrayList<RoomCarts> roomCarts = new ArrayList<>();
                roomCarts.addAll(orderRequestModels.get(position).getRoomCartsList());
                intent.putParcelableArrayListExtra("ORDER_REQUEST", roomCarts);
                context.startActivity(intent);

            });
        }


    }

    @Override
    public int getItemCount() {
        return orderRequestModels != null ? orderRequestModels.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == orderRequestModels.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dateText, orderId;
        private ImageView orderStatusImage;
        private ConstraintLayout reorderLayout, rateLayout, trackOrder;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.date_text);
            orderId = itemView.findViewById(R.id.order_id_text);
            orderStatusImage = itemView.findViewById(R.id.order_status);
            reorderLayout = itemView.findViewById(R.id.reorder_layout);
            rateLayout = itemView.findViewById(R.id.rate_order_layout);
            trackOrder = itemView.findViewById(R.id.track_order_layout);
        }
    }

       /*
   Helpers
   _________________________________________________________________________________________________
    */


    public void add(OrderRequestModel orderRequestModel) {
        orderRequestModels.add(orderRequestModel);
        notifyItemInserted(orderRequestModels.size() - 1);
    }

    private void displayRecentlyOrder(ArrayList<OrderRequestModel> orderRequestModels) {

        //Collections.reverse(orderRequestModels);
    }

    public void addAll(List<OrderRequestModel> mcList) {

        for (OrderRequestModel orderRequestModel : mcList) {
            add(orderRequestModel);
        }
        //Collections.reverse(mcList);

    }

    public void remove(OrderRequestModel orderRequestModel) {
        int position = orderRequestModels.indexOf(orderRequestModel);
        if (position > -1) {

            orderRequestModels.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
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
        add(new OrderRequestModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = orderRequestModels.size() - 1;
        OrderRequestModel item = getItem(position);

        if (item != null) {
            orderRequestModels.remove(position);
            notifyItemRemoved(position);
        }
    }

    public OrderRequestModel getItem(int position) {
        return orderRequestModels.get(position);
    }

}
