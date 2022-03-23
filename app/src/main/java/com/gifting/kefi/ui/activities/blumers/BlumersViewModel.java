package com.gifting.kefi.ui.activities.blumers;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gifting.kefi.data.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class BlumersViewModel extends ViewModel {
    private MutableLiveData<ArrayList<User>> userLiveData;
    private MutableLiveData<ArrayList<User>> userLiveData1;
    private MutableLiveData<String> failText;
    private BlumersRepo blumersRepo;
    private DatabaseReference reference;
    private Query query;

    public BlumersViewModel() {
    }

    public void getUsers() {
        blumersRepo = new BlumersRepo();
        userLiveData = blumersRepo.getUserLiveData();
        failText = blumersRepo.getFailText();
        blumersRepo.getUsers();
    }

    public void setLoadSearchUsers(String searchTerm) {
        blumersRepo = new BlumersRepo();
        userLiveData1 = blumersRepo.getUserLiveData1();
        failText = blumersRepo.getFailText();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        //query = reference.orderByChild("search").startAt(searchTerm).endAt(searchTerm + "\uf8ff");
        blumersRepo.setSearch(reference, searchTerm.toLowerCase());
    }

    public MutableLiveData<ArrayList<User>> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }

    public MutableLiveData<ArrayList<User>> getUserLiveData1() {
        return userLiveData1;
    }
}
