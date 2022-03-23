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
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context context;
    private List<Products> productsList;
    private AppRoomDatabase db;



    public SearchAdapter(Context context, List<Products> productsList) {
        this.context = context;
        this.productsList = productsList;
        db = AppRoomDatabase.getDatabase(context);
    }

    // helper

    public void setData(ArrayList<Products> productsList){
        this.productsList = productsList;
        notifyDataSetChanged();
    }
    public List<Products> getData(){
        return productsList;
    }
    public void removeAllData(){
        productsList.clear();
    }

    //

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        View finishItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_product, parent, false);
        return new ViewHolder(finishItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        if (db.userDao().findFavoriteProductById(productsList.get(position).getId())) {
            holder.favoriteImage.setImageResource(R.drawable.ic_love_video_bink);
            holder.favoriteImage.setTag("love");
        } else {
            holder.favoriteImage.setImageResource(R.drawable.ic_unlove_video_bink);
            holder.favoriteImage.setTag("unLove");
        }
        holder.nameItem.setText(productsList.get(position).getName());
        holder.priceItem.setText(productsList.get(position).getPrice().toString() + context.getString(R.string.l_e));
        holder.rateItem.setText(productsList.get(position).getRate().toString());

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .priority(Priority.HIGH);

        new GlideImageLoader(holder.itemImage,
                holder.progressBar).load(productsList.get(position).getPicture(), options);

        holder.relativeLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context.getApplicationContext(), ProductDetailsActivity.class);
            intent.putExtra("product_details", productsList.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productsList != null ? productsList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameItem, priceItem, rateItem;
        private ImageView itemImage,favoriteImage;
        private RelativeLayout relativeLayout;
        private ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameItem = itemView.findViewById(R.id.name_item);
            priceItem = itemView.findViewById(R.id.item_price);
            rateItem = itemView.findViewById(R.id.rate_item);
            itemImage = itemView.findViewById(R.id.image_item);

            relativeLayout = itemView.findViewById(R.id.product_item);
            favoriteImage = itemView.findViewById(R.id.favorite_image);
            progressBar = itemView.findViewById(R.id.load_progress);

        }
    }
}
