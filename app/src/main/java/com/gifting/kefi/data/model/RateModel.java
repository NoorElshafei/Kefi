package com.gifting.kefi.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RateModel {
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("commentRate")
    @Expose
    private String commentRate;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userPhone")
    @Expose
    private String userPhone;
    @SerializedName("userId")
    @Expose
    private String userId;

    public RateModel(String rate, String commentRate,String userName,String userPhone, String userId) {
        this.rate = rate;
        this.commentRate = commentRate;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userId = userId;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCommentRate() {
        return commentRate;
    }

    public void setCommentRate(String commentRate) {
        this.commentRate = commentRate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
