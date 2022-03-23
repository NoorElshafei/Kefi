package com.gifting.kefi.ui.activities.my_order;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gifting.kefi.data.model.OrderRequestModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyOrderRepo extends LiveData<ArrayList<OrderRequestModel>> {
    private static final String LOG_TAG = "FirebaseQueryLiveData";
    private Query query;
    private MyValueEventListener listener = new MyValueEventListener();
    private ArrayList<OrderRequestModel> orderRequestModels;
    private MutableLiveData<String> failText;


    public MyOrderRepo(Query query) {
        this.query = query;
        failText= new MutableLiveData<>();

    }

    public MyOrderRepo(DatabaseReference ref) {
        this.query = ref;

    }

    @Override
    protected void onActive() {
        super.onActive();
        query.addValueEventListener(listener);
        Log.d("nooooooooooooooor", "onActive");
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        query.removeEventListener(listener);
        Log.d("nooooooooooooooor", "onInactive");

    }

        private class MyValueEventListener implements ValueEventListener {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderRequestModels = new ArrayList<>();
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        OrderRequestModel orderRequestModel = snapshot1.getValue(OrderRequestModel.class);
                        orderRequestModels.add(orderRequestModel);
                    }
                    //Collections.reverse(orderRequestModels);
                    setValue(orderRequestModels);
                }else{
                    failText.postValue("No Order Found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("nooooooooooooooor", "Can't listen to query " + query, error.toException());

            }
        }

    public MutableLiveData<String> getFailText() {
        return failText;
    }
}
