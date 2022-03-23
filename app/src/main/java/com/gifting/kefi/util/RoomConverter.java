package com.gifting.kefi.util;

import androidx.room.TypeConverter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class RoomConverter {
    static Gson gson = new Gson();

    @TypeConverter
    public static DatabaseReference stringToSomeObjectList(String data) {
        return data == null ? null :  FirebaseDatabase.getInstance().getReference();

       /* if (data == null) {
            return null;
        }
        DatabaseReference databaseReference = gson.fromJson(data, DatabaseReference.class);
        return databaseReference;*/
    }

    @TypeConverter
    public static String someObjectListToString(DatabaseReference databaseReference) {
        String jsonString = gson.toJson(databaseReference);
        return jsonString;
    }
}
