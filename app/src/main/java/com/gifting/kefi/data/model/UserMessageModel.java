package com.gifting.kefi.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserMessageModel {
    @SerializedName("createdAt")
    @Expose
    private long createdAt;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userPhone")
    @Expose
    private String userPhone;
    @SerializedName("userId")
    @Expose
    private String userId;

    public UserMessageModel(long createdAt , String message,String userName,String userPhone,String userId) {
        this.createdAt = createdAt;
        this.userName = userName;
        this.message = message;
        this.userPhone = userPhone;
        this.userId = userId;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
