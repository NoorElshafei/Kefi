package com.gifting.kefi.ui.adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;

import java.util.ArrayList;

import static com.gifting.kefi.data.shared_preference.UserSharedPreference.DRY;
import static com.gifting.kefi.data.shared_preference.UserSharedPreference.MIXED;
import static com.gifting.kefi.data.shared_preference.UserSharedPreference.NORMAL;
import static com.gifting.kefi.data.shared_preference.UserSharedPreference.OILY;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private ArrayList<String> answers;
    private ArrayList<Integer> checkIfSelectedList;
    private int selectedItem = -1;
    private String question = "";
    private Context context;
    private boolean sizeChanged = true;
    private int previousItem;
    private boolean isAnswerSelected;

    public QuestionsAdapter(Context context, ArrayList<String> answers, ArrayList<Integer> checkIfSelectedList, String q1, boolean isAnswerSelected) {
        this.answers = new ArrayList<>();
        this.checkIfSelectedList = new ArrayList<>();
        this.answers = answers;
        this.checkIfSelectedList = checkIfSelectedList;
        this.question = q1;
        this.context = context;
        this.isAnswerSelected = isAnswerSelected;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View finishItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_answer, parent, false);
        return new ViewHolder(finishItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(answers.get(position));
        holder.itemView.setOnClickListener(v -> {
            isAnswerSelected = true;
            if (question == "q4") {
                UserSharedPreference userSharedPreference = new UserSharedPreference(context);
                if (position == 0) {
                    userSharedPreference.setSkinType("OILY");
                    userSharedPreference.saveUserSkin(OILY);

                } else if (position == 1) {
                    userSharedPreference.setSkinType("NORMAL");
                    userSharedPreference.saveUserSkin(NORMAL);

                } else if (position == 2) {
                    userSharedPreference.setSkinType("DRY");
                    userSharedPreference.saveUserSkin(DRY);

                } else if (position == 3) {
                    userSharedPreference.setSkinType("MIXED");
                    userSharedPreference.saveUserSkin(MIXED);
                }

            }
            previousItem = selectedItem;

            if (checkIfSelectedList.get(position) != 1) {
                checkIfSelectedList.set(position, 1);

            } else {
                checkIfSelectedList.set(position, 0);
            }
            selectedItem = position;
            notifyDataSetChanged();


        });

        if (selectedItem == position) {
            holder.imageView.setImageResource(R.drawable.ic_check_yes);

        } else {
            holder.imageView.setImageResource(R.drawable.ic_check_no);
        }


        if (question == "q4") {

            if (position == 2) {
                float v = context.getResources().getDimensionPixelSize(R.dimen._10ssp);
                holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, v);

            }
            if (position == 3) {
                float v = context.getResources().getDimensionPixelSize(R.dimen._9ssp);
                holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, v);
                sizeChanged = false;
            }
        }

        if (question == "q5" && position == 3) {

            float v = context.getResources().getDimensionPixelSize(R.dimen._10ssp);
            holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, v);
            sizeChanged = false;
        }
        if (question == "q6" && position == 1) {
            float v = context.getResources().getDimensionPixelSize(R.dimen._11ssp);
            holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, v);
            sizeChanged = false;
        }
    }

    @Override
    public int getItemCount() {
        if (answers != null)
            return answers.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.answer_text);
            imageView = itemView.findViewById(R.id.image);
        }
    }

    public boolean isAnswerSelected() {
        return isAnswerSelected;
    }
}
