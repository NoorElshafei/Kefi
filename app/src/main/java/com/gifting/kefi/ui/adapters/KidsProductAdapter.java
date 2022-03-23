package com.gifting.kefi.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.Products;
import com.gifting.kefi.databinding.ItemLikedProduct1Binding;
import com.gifting.kefi.ui.activities.product_details.ProductDetailsActivity;
import com.gifting.kefi.util.GlideImageLoader;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class KidsProductAdapter extends RecyclerView.Adapter<KidsProductAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Products> products;


    public KidsProductAdapter(Context context) {
        this.context = context;
        products = new ArrayList<>();
    }

    public void setProductModels(ArrayList<Products> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLikedProduct1Binding binding = ItemLikedProduct1Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .priority(Priority.HIGH);

        new GlideImageLoader(holder.binding.itemImage,
                holder.binding.loadProgress).load(products.get(position).getPicture(), options);
       // Glide.with(context).load(products.get(position).getPicture()).into(holder.binding.itemImage);
        holder.binding.nameItem.setText(products.get(position).getName());
        holder.binding.itemPrice.setText(products.get(position).getPrice() + context.getString(R.string.l_e));
        holder.binding.productItem.setOnClickListener(v -> {
            Intent intent = new Intent(context.getApplicationContext(), ProductDetailsActivity.class);
            intent.putExtra("product_details", products.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (products != null) ? products.size() : 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemLikedProduct1Binding binding;

        public ViewHolder(ItemLikedProduct1Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
