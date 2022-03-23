package com.gifting.kefi.ui.activities.your_skin;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.gifting.kefi.data.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class YourSkinRepo {
    private DatabaseReference reference;
    private MutableLiveData<User> userLiveData;
    private MutableLiveData<String> failText;

    public YourSkinRepo() {
        userLiveData = new MutableLiveData<>();
        failText = new MutableLiveData<>();
    }

    public void storeUserDataInDatabase(User user) {
        reference = FirebaseDatabase.getInstance().getReference("Users/" + user.getId() + "/");

        reference.setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("nooooooooooor222", "onComplete2: ");
                userLiveData.postValue(user);
            } else {
                Log.d("nooooooooooor222", "faliar2: " + task.getException().getMessage());
                failText.postValue("there is no permission to store user data in database:" + task.getException().getMessage());
            }
        });
    }

    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }
}
