package com.gifting.kefi.ui.activities.my_order;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MyOrderViewModel extends ViewModel {
    private DatabaseReference reference;
    private Query query;
    private MyOrderRepo myOrderRepoLiveData1;
    private MyOrderRepo myOrderRepoLiveData2;
    private MutableLiveData<String> failText;

    public MyOrderViewModel() {
        reference = FirebaseDatabase.getInstance().getReference("Request").child(FirebaseAuth.getInstance().getUid());
    }

    public void setLoadFirstReviews() {
        query = reference.orderByKey().limitToLast(10);
        myOrderRepoLiveData1 = new MyOrderRepo(query);
        failText= myOrderRepoLiveData1.getFailText();
    }

    public void setLoadNextReviews(String requestId) {
        query = reference.orderByKey().endBefore(requestId).limitToLast(10);
        myOrderRepoLiveData2 = new MyOrderRepo(query);

    }


    public MyOrderRepo getFirstReviewsLiveData() {
        return myOrderRepoLiveData1;
    }

    public MyOrderRepo getNextReviewsLiveData1() {
        return myOrderRepoLiveData2;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }
}
