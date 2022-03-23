package com.gifting.kefi.ui.fragments.edit_name_bottom_sheet;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.gifting.kefi.data.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditNameRepo {

    private MutableLiveData<User> successText;
    private MutableLiveData<String> failText;
    private DatabaseReference reference;

    public EditNameRepo() {
        failText = new MutableLiveData<>();
        successText = new MutableLiveData<>();
    }

    public void changeName(User user) {
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getId()).child("name");
        reference.setValue(user.getName()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                generateUniqueUserName(user);

            } else {
                failText.postValue(task.getException().getMessage());
            }
        });

    }

    private void generateUniqueUserName(User user) {
        String[] myName = user.getName().split(" ");
        String userName, firstName, lastName;
        if (myName.length > 1) {
            firstName = myName[0];
            lastName = myName[1];

            userName = firstName.charAt(0)+"_" + lastName;
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
                storeUserDataInDatabase(user);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                failText.postValue(error.getMessage());
            }
        });
    }


    private void storeUserDataInDatabase(User user) {
        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getId()).child("userName");

        reference.setValue(user.getUserName()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                successText.postValue(user);
            } else {
                failText.postValue(task.getException().getMessage());
            }
        });
    }


    public MutableLiveData<User> getSuccessText() {
        return successText;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }

}
