package com.gifting.kefi.data.local_database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface CartDao {
    //Room Carts
    @Query("SELECT * FROM  RoomCarts")
    Flowable<List<RoomCarts>> getAll();

    @Query("SELECT * FROM  RoomCarts")
    List<RoomCarts> getAllSingle();

    @Query("SELECT * FROM RoomCarts WHERE id IN (:productIds)")
    Flowable<List<RoomCarts>> loadAllByIds(int[] productIds);

    @Query("SELECT EXISTS (SELECT 1 FROM RoomCarts WHERE id = :id)")
    boolean findById(String id);

    @Insert
    void insertItem(RoomCarts cartProduct);

    @Delete
    void delete(RoomCarts cartProduct);

    @Query("DELETE FROM RoomCarts")
    void deleteAll();

    @Update
    int updateProduct(RoomCarts roomCarts);

    default void updateCart(String id, Integer newQuantity) {
        RoomCarts cartModel = getCart(id);
        cartModel.setQuantity(newQuantity);
        updateProduct(cartModel);
    }

    @Query("SELECT * FROM RoomCarts WHERE id LIKE :id")
    RoomCarts getCart(String id);
    //

    // RecentViewedProduct
    @Query("SELECT * FROM  RecentViewedProduct")
    List<RecentViewedProduct> getAllRecentSingle();

    @Query("SELECT EXISTS (SELECT 1 FROM RecentViewedProduct WHERE id = :id)")
    boolean findRecentById(String id);

    @Insert
    void insertItem(RecentViewedProduct recentViewedProduct);

    @Delete
    void delete(RecentViewedProduct recentViewedProduct);

    @Query("DELETE FROM RecentViewedProduct")
    void deleteAllRecent();


    @Query("SELECT * FROM RecentViewedProduct WHERE id LIKE :id")
    RecentViewedProduct getRecentProduct(String id);

    @Query("DELETE FROM RecentViewedProduct")
    void deleteAllRecentViewedProduct();

    //

    // video Room
    @Insert
    void insertItem(VideoModel videoModel);

    @Query("SELECT EXISTS (SELECT 1 FROM VideoModel WHERE id = :id)")
    boolean findVideoById(String id);

    @Query("SELECT * FROM  VideoModel ")
    List<VideoModel> getAllFavouriteVideo();

    @Query("DELETE FROM VideoModel WHERE id = :id")
    void deleteById(String id);

    @Query("DELETE FROM VideoModel")
    void deleteAllVideoModel();
    //

    //  favorite article
    @Insert
    void insertItem(ArticlesModel articlesModel);

    @Query("SELECT EXISTS (SELECT 1 FROM ArticlesModel WHERE id = :id)")
    boolean findFavoriteArticleById(String id);

    @Query("SELECT * FROM  ArticlesModel ")
    List<ArticlesModel> getAllFavouriteArticle();

    @Query("DELETE FROM ArticlesModel WHERE id = :id")
    void deleteArticleById(String id);

    @Query("DELETE FROM ArticlesModel")
    void deleteAllArticlesModel();

    //

    //  favorite product
    @Insert
    void insertItem(Products products);

    @Query("SELECT EXISTS (SELECT 1 FROM Products WHERE id = :id)")
    boolean findFavoriteProductById(String id);

    @Query("SELECT * FROM  Products ")
    List<Products> getAllFavoriteProduct();

    @Query("DELETE FROM Products WHERE id = :id")
    void deleteProductById(String id);

    @Query("DELETE FROM Products")
    void deleteAllProducts();
    //

    //recentlyModel
    @Query("SELECT * FROM recently")
    List<RecentlyModel> getRecentlyModel();

    @Insert
    void insertRecentlyModel(RecentlyModel recentlyModel);

    @Delete
    void delete(RecentlyModel recentlyModel);

    @Query("DELETE FROM recently")
    void deleteAllRecentlyModel();
    //

    //search in recently table
    @Query("SELECT * FROM recently WHERE recently_id LIKE :id")
    RecentlyModel getRecentlyModel(String id);

    //compare id and return true or false
    @Query("SELECT EXISTS (SELECT 1 FROM recently WHERE recently_id = :id)")
    boolean findArticleById(String id);


    //  favorite article
    @Insert
    void insertItem(Article2Model articlesModel);

    @Query("SELECT EXISTS (SELECT 1 FROM Article2Model WHERE id = :id)")
    boolean findFavoriteArticle2ById(String id);

    @Query("SELECT * FROM  Article2Model ")
    List<Article2Model> getAllFavouriteArticle2();

    @Query("SELECT * FROM Article2Model WHERE article_type =:articleType")
    List<Article2Model> loadAllArticlesByType(String articleType);

    @Query("DELETE FROM Article2Model WHERE id = :id")
    void deleteArticle2ById(String id);


    @Query("DELETE FROM Article2Model")
    void deleteAllArticle2Model();
    //


    //  uploads
    @Insert
    void insertItem(UploadsModel uploadsModel);

    @Query("SELECT * FROM  UploadsModel ")
    List<UploadsModel> getAllUploads();

    @Delete
    void delete(UploadsModel uploadsModel);

    @Query("DELETE FROM UploadsModel")
    void deleteAllUploadsModel();

    //


    //  reel video
    @Insert
    void insertItem(ReelVideoModel reelVideoModel);

    @Query("SELECT * FROM  ReelVideoModel ")
    List<ReelVideoModel> getAllReel();

    @Delete
    void delete(ReelVideoModel reelVideoModel);

    @Query("DELETE FROM ReelVideoModel")
    void deleteAllReelVideoModel();
    //



//calendar
    @Query("SELECT * FROM  CalendarModel Where id_date= :date ")
    List<CalendarModel> getAllCalendarById(String date);

    @Query("SELECT * FROM  CalendarModel")
    List<CalendarModel> getAllCalendar();

    @Insert
    void insertItem(CalendarModel calendarModel);


    @Update
    int  update(CalendarModel calendarModel);


    default void updateDate(long cid, CalendarModel calendarModel) {
        CalendarModel calendar = getData(cid);
        calendar.setData_from(calendarModel.getData_from());
        calendar.setTitle(calendarModel.getTitle());
        calendar.setNote(calendarModel.getNote());
        calendar.setColor_select(calendarModel.getColor_select());
        calendar.setData_to(calendarModel.getData_to());

        update(calendar);
    }
    @Query("SELECT * FROM calendarmodel WHERE cid LIKE :cid")
    CalendarModel getData(long cid);




   /* @Update
    int updateProduct(RoomCarts roomCarts);

    default void updateCart(String id, Integer newQuantity) {
        RoomCarts cartModel = getCart(id);
        cartModel.setQuantity(newQuantity);
        updateProduct(cartModel);
    }

    @Query("SELECT * FROM RoomCarts WHERE id LIKE :id")
    RoomCarts getCart(String id);
*/


}
