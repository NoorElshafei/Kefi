package com.gifting.kefi.ui.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.CalendarModel;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ItemNoteBinding;
import com.gifting.kefi.ui.fragments.add_note.AddNoteFragment;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private Context context;
    private FragmentActivity activity;
    private List<CalendarModel> calendarList;
    private boolean checkEdit= false;
    private UserSharedPreference userSharedPreference;


    public NotesAdapter(FragmentActivity activity, Context context) {
        this.context = context;
        this.activity = activity;
        calendarList = new ArrayList<>();
        userSharedPreference=new UserSharedPreference(context);
    }

    public void setCalendarList(List<CalendarModel> calendarList) {
        this.calendarList = calendarList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemNoteBinding binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.textView15.setText(calendarList.get(position).getTitle());
        if (calendarList.get(position).getColor_select().equals("yellow")) {


            Glide.with(context).load(R.drawable.ic_circle_yellow).into(holder.binding.imageView7);
            //holder.binding.imageView7.setImageResource(R.drawable.ic_circle_yellow);
        }else if(calendarList.get(position).getColor_select().equals("green")){
            Glide.with(context).load(R.drawable.ic_circle_green).into(holder.binding.imageView7);
        }
        else if(calendarList.get(position).getColor_select().equals("bink")){
            Glide.with(context).load(R.drawable.ic_done_).into(holder.binding.imageView7);
        }



        String dateString = new SimpleDateFormat("hh:mm a", Locale.forLanguageTag(userSharedPreference.getLanguage())).format(calendarList.get(position).getData_from());
        String dateString2 = new SimpleDateFormat("hh:mm a",Locale.forLanguageTag(userSharedPreference.getLanguage())).format(calendarList.get(position).getData_to());
        holder.binding.textView16.setText(dateString);
        holder.binding.textView22.setText(dateString2);

        holder.binding.editButton.setOnClickListener(v -> {


            Bundle bundle= new Bundle();
            bundle.putParcelable("calendarModel",calendarList.get(position));
            AddNoteFragment fragment= new AddNoteFragment();
            fragment.setArguments(bundle);
            activity.getSupportFragmentManager()
                    .beginTransaction().addToBackStack(null)
                    .replace(R.id.container, fragment)
                    .commit();


        });






      /*  if (position == 2) {
            holder.binding.threeLines.setVisibility(View.GONE);
        }*/



    }



    @Override
    public int getItemCount() {
        if (calendarList != null)
            return calendarList.size();
        else
            return 0;

        //return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemNoteBinding binding;


        public ViewHolder(ItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
