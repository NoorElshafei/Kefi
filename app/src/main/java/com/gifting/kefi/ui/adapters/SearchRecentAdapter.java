package com.gifting.kefi.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.Products;
import com.gifting.kefi.data.model.RecentViewedProduct;
import com.gifting.kefi.ui.activities.product_details.ProductDetailsActivity;
import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

public class SearchRecentAdapter extends RecyclerView.Adapter<SearchRecentAdapter.ViewHolder> {

    private Context context;
    private List<RecentViewedProduct> recentViewedProducts;


    public SearchRecentAdapter(Context context, List<RecentViewedProduct> recentViewedProducts) {
        this.context = context;
        this.recentViewedProducts = recentViewedProducts;
    }

    public void setData(List<RecentViewedProduct> recentViewedProducts) {
        this.recentViewedProducts = recentViewedProducts;
        Collections.reverse(this.recentViewedProducts);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View finishItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_recent_search, parent, false);
        return new ViewHolder(finishItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(recentViewedProducts.get(position).getPicture()).into(holder.imageView);
        holder.nameItem.setText(recentViewedProducts.get(position).getName());
        holder.priceItem.setText(recentViewedProducts.get(position).getPrice() + context.getString(R.string.l_e));

        holder.parentItem.setOnClickListener(v -> {
            Intent intent = new Intent(context.getApplicationContext(), ProductDetailsActivity.class);
            Products products = new Products(recentViewedProducts.get(position).getBrand(),recentViewedProducts.get(position).getCategory(),recentViewedProducts.get(position).getCondition(),recentViewedProducts.get(position).getId(),recentViewedProducts.get(position).getMaterial(),recentViewedProducts.get(position).getName(),recentViewedProducts.get(position).getPicture(),recentViewedProducts.get(position).getPrice(),recentViewedProducts.get(position).getRate(),recentViewedProducts.get(position).getSearch(),recentViewedProducts.get(position).getSerial(),recentViewedProducts.get(position).getInventoryQuantity(),recentViewedProducts.get(position).getDescription(),"");
            intent.putExtra("product_details", products);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return recentViewedProducts != null ? recentViewedProducts.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameItem, priceItem;
        private ImageView imageView;
        private CardView parentItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameItem = itemView.findViewById(R.id.name_item);
            priceItem = itemView.findViewById(R.id.price_item);
            imageView = itemView.findViewById(R.id.image_item);
            parentItem = itemView.findViewById(R.id.parent_item);
        }
    }
}
