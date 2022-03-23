package com.gifting.kefi.ui.navigation_fragments.register;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.gifting.kefi.data.model.User;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class RegisterRepo {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference;
    private MutableLiveData<User> userLiveData;
    private MutableLiveData<String> failText;


    public RegisterRepo() {
        firebaseAuth = FirebaseAuth.getInstance();
        userLiveData = new MutableLiveData<>();
        failText = new MutableLiveData<>();

       /* if (firebaseAuth.getCurrentUser() != null) {
            userLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedOutLiveData.postValue(false);
        }*/
    }

    public void register(String name, String email, String password, long birthDate) {
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        firebaseAuth.getCurrentUser().linkWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                String userId = firebaseUser.getUid();

                User user = new User(userId, email, "", name, firebaseUser.getPhoneNumber(), birthDate, "default", name.toLowerCase(), "online", "notSelected", "notSelected", false);
                generateUniqueUserName(user, userId);

            } else {
                failText.postValue("something happen with register method:" + task.getException().getMessage());
            }
        });
    }

    private void generateUniqueUserName(User user, String userId) {
        String[] myName = user.getName().split(" ");
        String userName, firstName = "", lastName = "";
        if (myName.length > 1) {
            firstName = myName[0];
            lastName = myName[1];

            userName = firstName.charAt(0) + "_" + lastName;
        } else {
            firstName = myName[0];
            userName = firstName;
        }

        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("userName").startAt(userName).endAt(userName + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        count++;
                    }
                    String finalUserName = userName + count;
                    user.setUserName(finalUserName);

                } else {
                    user.setUserName(userName);
                }
                storeUserDataInDatabase(user, userId);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                failText.postValue(error.getMessage());
            }
        });
    }


    private void storeUserDataInDatabase(User user, String userId) {
        reference = FirebaseDatabase.getInstance().getReference("Users/" + userId + "/");

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
