package com.gifting.kefi.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RateOrderModel {
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("userName")
    @Expose
    private String userName;

    public RateOrderModel(String userId, String orderId, String comment, String rate, String userName) {
        this.userId = userId;
        this.orderId = orderId;
        this.comment = comment;
        this.rate = rate;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
