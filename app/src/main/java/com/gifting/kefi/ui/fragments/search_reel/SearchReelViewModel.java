package com.gifting.kefi.ui.fragments.search_reel;

import androidx.lifecycle.ViewModel;

import com.gifting.kefi.ui.fragments.search.SearchRepo;

public class SearchReelViewModel extends ViewModel {
   /* private DatabaseReference reference;
    private Query query;
    private SearchRepo liveData;
    private MutableLiveData<String> noItemText;

    public SearchReelViewModel() {
        reference = FirebaseDatabase.getInstance().getReference("Reel");
    }


    public void setLoadSearchProducts(String searchTerm) {
        liveData = new SearchRepo();
        noItemText = liveData.getNoItemText();
        if (searchTerm != null && searchTerm.length() > 0) {
            char[] letters = searchTerm.toCharArray();
            String firstLetter = String.valueOf(letters[0]).toLowerCase();
            String remainingLetters = searchTerm.substring(1);
            searchTerm = firstLetter + remainingLetters;
        }

        query = reference.orderByChild("search").startAt(searchTerm).endAt(searchTerm + "\uf8ff");
        liveData.setSearch(query);
    }


    public SearchRepo getDataSnapshotLiveData() {
        return liveData;
    }

    public MutableLiveData<String> getNoItemText() {
        return noItemText;
    }*/
}