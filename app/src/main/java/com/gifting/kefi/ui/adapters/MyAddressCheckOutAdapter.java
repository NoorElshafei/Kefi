package com.gifting.kefi.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.AddressModel;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.util.DialogCustom;
import com.gifting.kefi.util.Language;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyAddressCheckOutAdapter extends RecyclerView.Adapter<MyAddressCheckOutAdapter.ViewHolder> {

    private Activity activity;
    private Context context;
    private ArrayList<AddressModel> addressFirebaseModelArrayList;
    private double totalPrice = 0d;
    private UserSharedPreference userSharedPreference;
    private DatabaseReference reference;
    private DialogCustom dialogCustom;


    public MyAddressCheckOutAdapter(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        this.addressFirebaseModelArrayList = new ArrayList<>();
        userSharedPreference = new UserSharedPreference(context);
        reference = FirebaseDatabase.getInstance().getReference("Address").child(FirebaseAuth.getInstance().getUid());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View finishItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_address, parent, false);
        return new ViewHolder(finishItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.numberOfHouse.setText(addressFirebaseModelArrayList.get(position).getHouse_and_apartment());
        holder.nameOfStreet.setText(addressFirebaseModelArrayList.get(position).getStreet_name());
        holder.nameOfDistrict.setText(addressFirebaseModelArrayList.get(position).getDistrict_or_area_name());
        holder.nameOfCity.setText(addressFirebaseModelArrayList.get(position).getCity_name());
        Language.changeBackDependsLanguage(holder.arrow, context);

        holder.layout.setOnClickListener(v -> {

            userSharedPreference.setSelectedAddress(addressFirebaseModelArrayList.get(position).getHouse_and_apartment() + ", " +
                    addressFirebaseModelArrayList.get(position).getStreet_name() + ", " +
                    addressFirebaseModelArrayList.get(position).getDistrict_or_area_name() + ", " +
                    addressFirebaseModelArrayList.get(position).getCity_name());
            activity.onBackPressed();

        });

        holder.deleteImage.setOnClickListener(v -> {
            dialogCustom = new DialogCustom(context);
            dialogCustom.showDialog();
            reference.child(addressFirebaseModelArrayList.get(position).getAddress_id()).removeValue().addOnSuccessListener(command -> {
                dialogCustom.dismissDialog();
                addressFirebaseModelArrayList.remove(position);
                notifyDataSetChanged();
            });
        });

    }

    @Override
    public int getItemCount() {
        return addressFirebaseModelArrayList != null ? addressFirebaseModelArrayList.size() : 0;
    }


    /*
    View Holder
    _________________________________________________________________________________________________
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView numberOfHouse, nameOfStreet, nameOfDistrict, nameOfCity;
        private ConstraintLayout layout;
        private ImageView deleteImage, arrow;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            numberOfHouse = itemView.findViewById(R.id.shipping_value);
            nameOfStreet = itemView.findViewById(R.id.shipping_value1);
            nameOfDistrict = itemView.findViewById(R.id.shipping_value2);
            nameOfCity = itemView.findViewById(R.id.shipping_value3);
            layout = itemView.findViewById(R.id.address_layout_item);
            deleteImage = itemView.findViewById(R.id.delete_address_image);
            arrow = itemView.findViewById(R.id.image_address);


        }
    }

   /*
   Helpers
   _________________________________________________________________________________________________
    */

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void add(AddressModel addressFirebaseModel) {
        addressFirebaseModelArrayList.add(addressFirebaseModel);
        notifyItemInserted(addressFirebaseModelArrayList.size() - 1);
    }

    public void addAll(List<AddressModel> mcList) {
        for (AddressModel addressFirebaseModel : mcList) {
            add(addressFirebaseModel);
        }
    }

    public void remove(AddressModel addressFirebaseModel) {
        int position = addressFirebaseModelArrayList.indexOf(addressFirebaseModel);
        if (position > -1) {
            addressFirebaseModelArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }


    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        add(new AddressModel());
    }

    public void removeLoadingFooter() {
        int position = addressFirebaseModelArrayList.size() - 1;
        AddressModel item = getItem(position);

        if (item != null) {
            addressFirebaseModelArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public AddressModel getItem(int position) {
        return addressFirebaseModelArrayList.get(position);
    }


}
