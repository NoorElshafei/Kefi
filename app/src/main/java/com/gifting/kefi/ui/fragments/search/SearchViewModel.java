package com.gifting.kefi.ui.fragments.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SearchViewModel extends ViewModel {
    private DatabaseReference reference;
    private Query query;
    private SearchRepo liveData;
    private MutableLiveData<String> noItemText;

    public SearchViewModel() {
        reference = FirebaseDatabase.getInstance().getReference("Products");
    }

    public void setLoadSearchProducts(String searchTerm,String lang) {
        liveData = new SearchRepo();
        noItemText = liveData.getNoItemText();
        if (searchTerm != null && searchTerm.length() > 0) {
            char[] letters = searchTerm.toCharArray();
            String firstLetter = String.valueOf(letters[0]).toLowerCase();
            String remainingLetters = searchTerm.substring(1);
            searchTerm = firstLetter + remainingLetters;
        }

        //query = reference.orderByChild("search").startAt(searchTerm).endAt(searchTerm + "\uf8ff");
        liveData.setSearch(reference.child(lang),searchTerm.toLowerCase());
    }


    public SearchRepo getDataSnapshotLiveData() {
        return liveData;
    }

    public MutableLiveData<String> getNoItemText() {
        return noItemText;
    }
}