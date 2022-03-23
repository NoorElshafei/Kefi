package com.gifting.kefi.ui.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.ReviewModel;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.ui.fragments.add_comment_product.AddCommentFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProductReviewAdapter extends RecyclerView.Adapter<ProductReviewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ReviewModel> reviewModelArrayList;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private UserSharedPreference userSharedPreference;

    public ProductReviewAdapter(Context context) {
        this.context = context;
        this.reviewModelArrayList = new ArrayList<>();
        userSharedPreference = new UserSharedPreference(context);

    }

    public ArrayList<ReviewModel> getProducts() {
        return reviewModelArrayList;
    }

    public void setProducts(ArrayList<ReviewModel> movies) {
        this.reviewModelArrayList = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View finishItemLayoutView;
        if (viewType == ITEM) {
            finishItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_review, parent, false);
        } else {
            finishItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_progress, parent, false);
        }
        return new ViewHolder(finishItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (getItemViewType(position) == ITEM) {
            holder.optionCardView.setVisibility(View.GONE);

            holder.nameItem.setText(reviewModelArrayList.get(position).getUserName());
            holder.commentText.setText(reviewModelArrayList.get(position).getComment());
            //holder.rateItem.setRating(reviewModelArrayList.get(position).getRating().floatValue());
            holder.nameShortCut.setText(getShortCutOfName(reviewModelArrayList.get(position).getUserName()));


            if (reviewModelArrayList.get(position).getUserID().equals(userSharedPreference.getUserDetails().getId())) {
                holder.optionComment.setVisibility(View.VISIBLE);
            } else {
                holder.optionComment.setVisibility(View.GONE);
            }
            holder.optionComment.setOnClickListener(v -> {
                if (holder.optionCardView.getVisibility() == View.GONE)
                    holder.optionCardView.setVisibility(View.VISIBLE);
                else
                    holder.optionCardView.setVisibility(View.GONE);
            });

            holder.editComment.setOnClickListener(v -> {
                holder.optionCardView.setVisibility(View.GONE);

                Bundle bundle = new Bundle();
                AddCommentFragment addCommentFragment = AddCommentFragment.newInstance();
                bundle.putString("PRODUCT_ID", reviewModelArrayList.get(position).getProductID());
                bundle.putString("COMMENT", reviewModelArrayList.get(position).getComment());
                bundle.putFloat("RATE", reviewModelArrayList.get(position).getRating());
                addCommentFragment.setArguments(bundle);
                addCommentFragment.show(((FragmentActivity) (context)).getSupportFragmentManager(), "addCommentFragment");
            });

            holder.deleteComment.setOnClickListener(v -> {
                holder.optionCardView.setVisibility(View.GONE);
                new AlertDialog.Builder(context)
                        .setTitle(context.getString(R.string.delete_comment))
                        .setMessage(context.getString(R.string.do_you_really_want_to_delete_Review))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Rating");
                            reference.child(reviewModelArrayList.get(position).getProductID()).child(reviewModelArrayList.get(position).getUserID()).removeValue();
                            Toast.makeText(context, context.getString(R.string.review_deleted_successfully), Toast.LENGTH_SHORT).show();
                            reviewModelArrayList.remove(position);
                            notifyDataSetChanged();

                        })
                        .setNegativeButton(android.R.string.no, null).show();
            });


        }

    }

    @Override
    public int getItemCount() {
        return reviewModelArrayList != null ? reviewModelArrayList.size() : 0;
    }


    @Override
    public int getItemViewType(int position) {
        return (position == reviewModelArrayList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


     /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(ReviewModel reviewModel) {
        reviewModelArrayList.add(reviewModel);
        notifyItemInserted(reviewModelArrayList.size() - 1);
    }

    public void addAll(ArrayList<ReviewModel> mcList) {
        for (ReviewModel reviewModel : mcList) {
            add(reviewModel);
        }
    }

    public void remove(ReviewModel reviewModel) {
        int position = reviewModelArrayList.indexOf(reviewModel);
        if (position > -1) {
            reviewModelArrayList.remove(position);
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
        add(new ReviewModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = reviewModelArrayList.size() - 1;
        ReviewModel item = getItem(position);

        if (item != null) {
            reviewModelArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public ReviewModel getItem(int position) {
        return reviewModelArrayList.get(position);
    }

 /*
   View Holder
   _________________________________________________________________________________________________
    */

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameItem, commentText, nameShortCut, editComment, deleteComment;
        //private ScaleRatingBar rateItem;
        private ImageView optionComment;
        private CardView optionCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameItem = itemView.findViewById(R.id.name_reviewer);
            commentText = itemView.findViewById(R.id.comment_text);
            //rateItem = itemView.findViewById(R.id.rate_item);
            nameShortCut = itemView.findViewById(R.id.name_short_cut);
            optionComment = itemView.findViewById(R.id.comment_option);
            optionCardView = itemView.findViewById(R.id.option_cardView);
            deleteComment = itemView.findViewById(R.id.deleteComment_text);
            editComment = itemView.findViewById(R.id.editComment_text);

        }
    }

    private String getShortCutOfName(String name) {
        StringBuilder shortCutName = new StringBuilder();
        String[] myName = name.split(" ");
        for (int i = 0; i < myName.length && i < 2; i++) {
            String s = myName[i].toUpperCase();
            shortCutName.append(s.charAt(0));
        }
        return shortCutName.toString();
    }
}
