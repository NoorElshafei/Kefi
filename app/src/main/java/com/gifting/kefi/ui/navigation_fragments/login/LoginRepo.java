package com.gifting.kefi.ui.navigation_fragments.login;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.gifting.kefi.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginRepo {
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<User> userLiveData;
    private MutableLiveData<String> failText;
    private DatabaseReference reference;


    public LoginRepo() {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.userLiveData = new MutableLiveData<>();
        this.failText = new MutableLiveData<>();

    }

    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                retrieveUserDataFromDatabase(firebaseAuth.getCurrentUser().getUid());
            } else {
                failText.postValue("Login Failure: " + task.getException().getMessage());
            }
        });
    }


    private void retrieveUserDataFromDatabase(String userId) {
        reference = FirebaseDatabase.getInstance().getReference("Users/" + userId + "/");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                userLiveData.postValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("nooooooooooor222", "faliar2: " + error.getMessage());
                failText.postValue(error.getMessage());
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
