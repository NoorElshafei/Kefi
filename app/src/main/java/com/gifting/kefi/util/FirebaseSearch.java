package com.gifting.kefi.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.data.model.Products;
import com.gifting.kefi.ui.adapters.SearchAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseSearch {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static List<Products> DataCache = new ArrayList<>();

    public static String searchString = "";

    public static void show(Context c, String message) {
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }

    public static void openActivity(Context c, Class clazz) {
        Intent intent = new Intent(c, clazz);
        c.startActivity(intent);
    }

    /**
     * This method will allow us send a serialized scientist objec  to a specified
     * activity
     */
    public static void sendScientistToActivity(Context c, Products products,
                                               Class clazz) {
        Intent i = new Intent(c, clazz);
        i.putExtra("SCIENTIST_KEY", products);
        c.startActivity(i);
    }

    /**
     * This method will allow us receive a serialized scientist, deserialize it and return it,.
     */
    public static Products receiveScientist(Intent intent, Context c) {
        try {
            return (Products) intent.getSerializableExtra("SCIENTIST_KEY");
        } catch (Exception e) {
            e.printStackTrace();
            show(c, "RECEIVING-SCIENTIST ERROR: " + e.getMessage());
        }
        return null;
    }

    public static void showProgressBar(ProgressBar pb) {
        pb.setVisibility(View.VISIBLE);
    }

    public static void hideProgressBar(ProgressBar pb) {
        pb.setVisibility(View.GONE);
    }

    public static DatabaseReference getDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference("Products");
    }

    public static void search(final Context context, DatabaseReference db,
                              final ProgressBar pb,
                              SearchAdapter adapter, String searchTerm) {
        if (searchTerm != null && searchTerm.length() > 0) {
            char[] letters = searchTerm.toCharArray();
            String firstLetter = String.valueOf(letters[0]).toLowerCase();
            String remainingLetters = searchTerm.substring(1);
            searchTerm = firstLetter + remainingLetters;
        }

        FirebaseSearch.showProgressBar(pb);

        Query firebaseSearchQuery = db.orderByChild("search").startAt(searchTerm)
                .endAt(searchTerm + "\uf8ff");


        firebaseSearchQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataCache.clear();
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        //Now get Scientist Objects and populate our arraylist.
                        Products products = ds.getValue(Products.class);
                        //products.setKey(ds.getKey());
                        DataCache.add(products);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    DataCache.clear();
                    adapter.notifyDataSetChanged();
                    FirebaseSearch.show(context, "No item found");
                }
                FirebaseSearch.hideProgressBar(pb);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FIREBASE CRUD", error.getMessage());
                FirebaseSearch.hideProgressBar(pb);
                FirebaseSearch.show(context, error.getMessage());
            }
        });
    }


    public static void select(final AppCompatActivity a, DatabaseReference db,
                              final ProgressBar pb,
                              final RecyclerView rv, SearchAdapter adapter) {
        FirebaseSearch.showProgressBar(pb);

        db.child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataCache.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //Now get Scientist Objects and populate our arraylist.
                        Products products = ds.getValue(Products.class);
//                            scientist.setKey(ds.getKey());
                        DataCache.add(products);
                    }

                    adapter.notifyDataSetChanged();
                } else {
                    FirebaseSearch.show(a, "No more item found");
                }
                FirebaseSearch.hideProgressBar(pb);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FIREBASE CRUD", databaseError.getMessage());
                FirebaseSearch.hideProgressBar(pb);
                FirebaseSearch.show(a, databaseError.getMessage());
            }
        });
    }


}
