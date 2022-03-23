package com.gifting.kefi.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.local_database.AppRoomDatabase;
import com.gifting.kefi.data.model.Products;
import com.gifting.kefi.ui.activities.product_details.ProductDetailsActivity;
import com.gifting.kefi.util.GlideImageLoader;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class StoreProductAdapter extends RecyclerView.Adapter<StoreProductAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Products> productsArrayList;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private AppRoomDatabase db;

    public StoreProductAdapter(Context context) {
        this.context = context;
        this.productsArrayList = new ArrayList<>();
        db = AppRoomDatabase.getDatabase(context);

    }

    public ArrayList<Products> getProducts() {
        return productsArrayList;
    }

    public void setProducts(ArrayList<Products> movies) {
        this.productsArrayList = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View finishItemLayoutView;
        if (viewType == ITEM) {
            finishItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_product, parent, false);
        } else {
            finishItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_progress, parent, false);
        }
        return new ViewHolder(finishItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM) {
            holder.nameItem.setText(productsArrayList.get(position).getName());
            holder.priceItem.setText(productsArrayList.get(position).getPrice().toString() + context.getString(R.string.l_e));
            holder.rateItem.setText(productsArrayList.get(position).getRate().toString());


            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .priority(Priority.HIGH);

            new GlideImageLoader(holder.itemImage,
                    holder.progressBar).load(productsArrayList.get(position).getPicture(), options);
            //Glide.with(context).load(productsArrayList.get(position).getPicture()).into(holder.itemImage);


            holder.relativeLayout.setOnClickListener(v -> {
                Intent intent = new Intent(context.getApplicationContext(), ProductDetailsActivity.class);
                intent.putExtra("product_details", productsArrayList.get(position));
                context.startActivity(intent);
            });


            if (db.userDao().findFavoriteProductById(productsArrayList.get(position).getId())) {
                holder.favoriteImage.setImageResource(R.drawable.ic_love_video_bink);
                holder.favoriteImage.setTag("love");
            } else {
                holder.favoriteImage.setImageResource(R.drawable.ic_unlove_video_bink);
                holder.favoriteImage.setTag("unLove");
            }

            holder.favoriteImage.setOnClickListener(v -> {
                if (holder.favoriteImage.getTag().equals("unLove")) {
                    db.userDao().insertItem(productsArrayList.get(position));
                    holder.favoriteImage.setImageResource(R.drawable.ic_love_video_bink);
                    holder.favoriteImage.setTag("love");
                    Toast.makeText(context,context.getString( R.string.added_to_favourites), Toast.LENGTH_SHORT).show();
                } else {
                    db.userDao().deleteProductById(productsArrayList.get(position).getId());
                    holder.favoriteImage.setImageResource(R.drawable.ic_unlove_video_bink);
                    holder.favoriteImage.setTag("unLove");
                    Toast.makeText(context,context.getString(R.string.removed_from_favourites), Toast.LENGTH_SHORT).show();
                }

            });
        }

    }

    @Override
    public int getItemCount() {
        return productsArrayList != null ? productsArrayList.size() : 0;
    }


    @Override
    public int getItemViewType(int position) {
        return (position == productsArrayList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


     /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(Products product) {
        productsArrayList.add(product);
        notifyItemInserted(productsArrayList.size() - 1);
    }

    public void addAll(ArrayList<Products> mcList) {
        for (Products products : mcList) {
            add(products);
        }
    }

    public void remove(Products product) {
        int position = productsArrayList.indexOf(product);
        if (position > -1) {
            productsArrayList.remove(position);
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
        add(new Products());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = productsArrayList.size() - 1;
        Products item = getItem(position);

        if (item != null) {
            productsArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Products getItem(int position) {
        return productsArrayList.get(position);
    }

 /*
   View Holder
   _________________________________________________________________________________________________
    */

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameItem, priceItem, rateItem;
        private ImageView itemImage, favoriteImage;
        private RelativeLayout relativeLayout;
        private ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameItem = itemView.findViewById(R.id.name_item);
            priceItem = itemView.findViewById(R.id.item_price);
            rateItem = itemView.findViewById(R.id.rate_item);
            itemImage = itemView.findViewById(R.id.image_item);
            progressBar = itemView.findViewById(R.id.load_progress);

            relativeLayout = itemView.findViewById(R.id.product_item);
            favoriteImage = itemView.findViewById(R.id.favorite_image);
        }
    }
}
