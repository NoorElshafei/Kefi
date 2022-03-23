package com.gifting.kefi.ui.activities.notifications;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gifting.kefi.data.model.NotificationModel;
import com.gifting.kefi.data.model.OrderRequestModel;
import com.gifting.kefi.ui.activities.my_order.MyOrderRepo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationsRepo extends LiveData<ArrayList<NotificationModel>> {
    private Query query;
    private MyValueEventListener listener = new MyValueEventListener();
    private ArrayList<NotificationModel> notificationModels;
    private MutableLiveData<String> failText;


    public NotificationsRepo(Query query) {
        this.query = query;
        failText = new MutableLiveData<>();
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            notificationModels = new ArrayList<>();
            if (snapshot.exists()) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    NotificationModel notificationModel = snapshot1.getValue(NotificationModel.class);
                    notificationModels.add(notificationModel);
                }
                setValue(notificationModels);
            }else{
                failText.postValue("no video");
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.e("nooooooooooooooor", "Can't listen to query " + query, error.toException());

        }
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

    public MutableLiveData<String> getFailText() {
        return failText;
    }
}
