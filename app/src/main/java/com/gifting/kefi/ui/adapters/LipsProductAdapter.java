package com.gifting.kefi.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;

public class LipsProductAdapter extends RecyclerView.Adapter<LipsProductAdapter.ViewHolder> {

    private Context context;


    public LipsProductAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View finishItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_liked_product1, parent, false);
        return new ViewHolder(finishItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


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
        }
    }
}
