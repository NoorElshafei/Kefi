package com.gifting.kefi.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewModel {

    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("rating")
    @Expose
    private Float rating;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("reviewCreatedAt")
    @Expose
    private long reviewCreatedAt;

    public ReviewModel() {
    }

    public ReviewModel(String comment, String productID, Float rating, String userID, String userName,long reviewCreatedAt) {
        this.comment = comment;
        this.productID = productID;
        this.rating = rating;
        this.userID = userID;
        this.userName = userName;
        this.reviewCreatedAt = reviewCreatedAt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getReviewCreatedAt() {
        return reviewCreatedAt;
    }

    public void setReviewCreatedAt(long reviewCreatedAt) {
        this.reviewCreatedAt = reviewCreatedAt;
    }
}
