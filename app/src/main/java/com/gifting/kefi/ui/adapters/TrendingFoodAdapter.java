package com.gifting.kefi.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.ui.activities.training_details.TrainingDetailsActivity;
import com.gifting.kefi.ui.activities.trending_food_details.FoodDetailsActivity;

public class TrendingFoodAdapter extends RecyclerView.Adapter<TrendingFoodAdapter.ViewHolder> {

    private Context context;


    public TrendingFoodAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View finishItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_food, parent, false);
        return new ViewHolder(finishItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cardView.setOnClickListener(v -> {
            context.startActivity(new Intent(context, FoodDetailsActivity.class));
        });

    }

    @Override
    public int getItemCount() {
      /*  if (answers != null)
            return answers.size();
        else
            return 0;*/
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.root_item);
        }
    }
}
