package com.gifting.kefi.data.local_database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.gifting.kefi.data.model.Article2Model;
import com.gifting.kefi.data.model.ArticlesModel;
import com.gifting.kefi.data.model.CalendarModel;
import com.gifting.kefi.data.model.Products;
import com.gifting.kefi.data.model.RecentViewedProduct;
import com.gifting.kefi.data.model.RecentlyModel;
import com.gifting.kefi.data.model.ReelVideoModel;
import com.gifting.kefi.data.model.RoomCarts;
import com.gifting.kefi.data.model.UploadsModel;
import com.gifting.kefi.data.model.VideoModel;


@Database(entities = {RoomCarts.class,
        RecentViewedProduct.class,
        RecentlyModel.class,
        VideoModel.class,
        ArticlesModel.class,
        Products.class,
        Article2Model.class,
        UploadsModel.class,
        ReelVideoModel.class,
        CalendarModel.class},
        version = 1)
public abstract class AppRoomDatabase extends RoomDatabase {
    private static AppRoomDatabase INSTANCE;

    public abstract CartDao userDao();


    public static AppRoomDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppRoomDatabase.class, "Blume_Database")
                    .allowMainThreadQueries()
                    //if you want create db only in memory, not in file
                    //Room.inMemoryDatabaseBuilder
                    //(context.getApplicationContext(), DataRoomDbase.class)
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
