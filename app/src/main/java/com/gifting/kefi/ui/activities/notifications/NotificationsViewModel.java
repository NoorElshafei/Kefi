package com.gifting.kefi.ui.activities.notifications;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gifting.kefi.ui.activities.my_order.MyOrderRepo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class NotificationsViewModel extends ViewModel {

    private DatabaseReference reference;
    private Query query;
    private NotificationsRepo notificationsRepo;
    private MutableLiveData<String> failText;


    public NotificationsViewModel() {
        reference = FirebaseDatabase.getInstance().getReference("Notifications");
    }


    public void getAllNotifications() {
        query = reference.child(FirebaseAuth.getInstance().getUid()).orderByValue();
        notificationsRepo = new NotificationsRepo(query);
        failText = notificationsRepo.getFailText();
    }

    public NotificationsRepo getNotificationsRepo() {
        return notificationsRepo;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }
}
