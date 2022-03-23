package com.gifting.kefi.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.RoomCarts;
import com.gifting.kefi.util.TotalCartInterFace;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ReorderAdapter extends RecyclerView.Adapter<ReorderAdapter.ViewHolder> {

    private Context context;
    private ArrayList<RoomCarts> roomCartsList;
    private boolean isLoadingAdded = false;
    private double totalPrice = 0d;
    private TotalCartInterFace totalCarts;
    private boolean isFirstTime = true;


    public ReorderAdapter(Context context, TotalCartInterFace totalCarts) {
        this.context = context;
        this.roomCartsList = new ArrayList<>();
        this.totalCarts = totalCarts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View finishItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_cart, parent, false);
        return new ViewHolder(finishItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        double priceWithQuantity = getPriceWithQuantity(position);
        holder.nameCart.setText(roomCartsList.get(position).getName());
        holder.typeNameCart.setText(roomCartsList.get(position).getCondition());
        holder.quantityText.setText(roomCartsList.get(position).getQuantity().toString());
        holder.priceCart.setText(priceWithQuantity + context.getString(R.string.l_e));
        Glide.with(context).load(roomCartsList.get(position).getPicture()).into(holder.imageCart);

        if (isFirstTime)
            totalCarts.addToTatal(roomCartsList.get(position).getPrice().intValue() * roomCartsList.get(position).getQuantity());

        if (position == roomCartsList.size() - 1)
            isFirstTime = false;

        holder.addImage.setOnClickListener(v -> {
            if (roomCartsList.get(position).getQuantity() >= 1 && roomCartsList.get(position).getQuantity() < roomCartsList.get(position).getInventoryQuantity()) {
                updateProduct(1, position, holder);
                totalCarts.addToTatal(roomCartsList.get(position).getPrice().intValue());
            }
        });
        holder.minusImage.setOnClickListener(v -> {
            if (roomCartsList.get(position).getQuantity() > 1) {
                updateProduct(-1, position, holder);
                totalCarts.minusfromTatal(roomCartsList.get(position).getPrice().intValue());
            }
        });
        holder.deleteItem.setOnClickListener(v -> {
            if (roomCartsList.size() > 1) {
                remove(roomCartsList.get(position));
            } else {
                Toast.makeText(context, context.getString(R.string.you_can_not_delete_last_product_from_checkout), Toast.LENGTH_SHORT).show();

            }

        });

    }

    @Override
    public int getItemCount() {
        return roomCartsList != null ? roomCartsList.size() : 0;
    }

    /*
       custom mothods
       _________________________________________________________________________________________________
        */
    private double getPriceWithQuantity(int position) {
        return roomCartsList.get(position).getPrice() * roomCartsList.get(position).getQuantity().doubleValue();
    }

    private void updateProduct(int changeNumber, int position, ViewHolder holder) {
        roomCartsList.get(position).setQuantity(roomCartsList.get(position).getQuantity() + changeNumber);
        holder.quantityText.setText(roomCartsList.get(position).getQuantity().toString());
        holder.priceCart.setText(getPriceWithQuantity(position) + context.getString(R.string.l_e));

    }

    /*
    View Holder
    _________________________________________________________________________________________________
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameCart, typeNameCart, priceCart, quantityText;
        private ImageView imageCart, addImage, minusImage;
        private CardView deleteItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameCart = itemView.findViewById(R.id.cart_name);
            typeNameCart = itemView.findViewById(R.id.name_type_cart);
            priceCart = itemView.findViewById(R.id.price_item);
            quantityText = itemView.findViewById(R.id.quantity_text);
            imageCart = itemView.findViewById(R.id.cart_image1);
            addImage = itemView.findViewById(R.id.add_image);
            deleteItem = itemView.findViewById(R.id.delete_item);
            minusImage = itemView.findViewById(R.id.minus_image);
        }
    }

   /*
   Helpers
   _________________________________________________________________________________________________
    */

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void add(RoomCarts roomCarts) {
        roomCartsList.add(roomCarts);
        notifyItemInserted(roomCartsList.size() - 1);
    }

    public void addAll(List<RoomCarts> mcList) {
        for (RoomCarts roomCarts : mcList) {
            add(roomCarts);
        }
    }

    public void remove(RoomCarts roomCarts) {
        int position = roomCartsList.indexOf(roomCarts);
        if (position > -1) {
            totalCarts.minusfromTatal(roomCartsList.get(position).getPrice().intValue() * roomCartsList.get(position).getQuantity());
            roomCartsList.remove(position);
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
        add(new RoomCarts());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = roomCartsList.size() - 1;
        RoomCarts item = getItem(position);

        if (item != null) {
            roomCartsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public RoomCarts getItem(int position) {
        return roomCartsList.get(position);
    }


}
