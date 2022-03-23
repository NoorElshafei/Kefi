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
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class LikedProductAdapter extends RecyclerView.Adapter<LikedProductAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Products> products;


    public LikedProductAdapter(Context context, ArrayList<Products> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLikedProduct1Binding binding = ItemLikedProduct1Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.nameItem.setText(products.get(position).getName());
        holder.binding.itemPrice.setText(products.get(position).getPrice().toString()+ context.getString(R.string.l_e));
        Glide.with(context.getApplicationContext()).load(products.get(position).getPicture()).into(holder.binding.itemImage);

        holder.binding.productItem.setOnClickListener(v -> {
            Intent intent = new Intent(context.getApplicationContext(), ProductDetailsActivity.class);
            Products products1 = products.get(position);
            intent.putExtra("product_details", products1);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemLikedProduct1Binding binding;

        public ViewHolder(ItemLikedProduct1Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
