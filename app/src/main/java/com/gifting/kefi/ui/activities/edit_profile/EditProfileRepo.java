package com.gifting.kefi.ui.activities.edit_profile;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class EditProfileRepo {
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<String> userLiveData;
    private MutableLiveData<String> failText;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private MutableLiveData<String> urlImageLiveData;
    private MutableLiveData<String> successDeleteText;


    public EditProfileRepo() {


        firebaseAuth = FirebaseAuth.getInstance();
        userLiveData = new MutableLiveData<>();
        failText = new MutableLiveData<>();
        successDeleteText = new MutableLiveData<>();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

    }


    public void editPassword(String email, String oldPassword, String newPassword) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user.updatePassword(newPassword).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                userLiveData.postValue("");

                            } else {
                                failText.postValue(task.getException().getMessage());
                            }
                        });
                    } else {
                        failText.postValue(task.getException().getMessage());
                    }
                });
    }

    public void uploadImage(Uri uri) {
        urlImageLiveData = new MutableLiveData<>();
        String imageName = UUID.randomUUID().toString();
        StorageReference imageFolder = storageReference.child("User_image/" + imageName);
        imageFolder.putFile(uri).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                downloadLink(imageFolder);
            } else {
                failText.postValue(task.getException().getMessage());
            }
        });


    }

    private void downloadLink(StorageReference imageFolder) {
        imageFolder.getDownloadUrl().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                databaseReference.child(firebaseAuth.getUid()).child("imageURL").setValue(task.getResult().toString());
                urlImageLiveData.postValue(task.getResult().toString());
            } else {
                failText.postValue(task.getException().getMessage());
            }
        });
    }

    public void deleteImage() {
        databaseReference.child(firebaseAuth.getUid()).child("imageURL").setValue("default").addOnSuccessListener(aVoid -> {
            successDeleteText.postValue("image delete successfully");
        });
    }

    public MutableLiveData<String> getUserLiveData() {
        return userLiveData;
    }

    public void setUserLiveData(MutableLiveData<String> userLiveData) {
        this.userLiveData = userLiveData;
    }

    public MutableLiveData<String> getFailText() {
        return failText;
    }

    public void setFailText(MutableLiveData<String> failText) {
        this.failText = failText;
    }

    public MutableLiveData<String> getUrlImageLiveData() {
        return urlImageLiveData;
    }

    public MutableLiveData<String> getSuccessDeleteText() {
        return successDeleteText;
    }
}
