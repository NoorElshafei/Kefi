package com.gifting.kefi.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderRequestModel implements Parcelable {
    @SerializedName("requestId")
    @Expose
    private String requestId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("nameOfUser")
    @Expose
    private String nameOfUser;
    @SerializedName("paymentMethod")
    @Expose
    private String paymentMethod;
    @SerializedName("paymentStatus")
    @Expose
    private String paymentStatus;
    @SerializedName("userPhone")
    @Expose
    private String userPhone;
    @SerializedName("totalPrice")
    @Expose
    private String totalPrice;
    @SerializedName("orderTime")
    @Expose
    private String orderTime;
    @SerializedName("orderStatus")
    @Expose
    private String orderStatus;
    @SerializedName("roomCartsList")
    @Expose
    private List<RoomCarts> roomCartsList;
    @SerializedName("userAddress")
    @Expose
    private String userAddress;

    public OrderRequestModel() {
    }

    public OrderRequestModel(String requestId, String userId, String nameOfUser, String paymentMethod, String paymentStatus, String userPhone, String totalPrice, String orderTime, String orderStatus, List<RoomCarts> roomCartsList, String userAddress) {
        this.requestId = requestId;
        this.userId = userId;
        this.nameOfUser = nameOfUser;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.userPhone = userPhone;
        this.totalPrice = totalPrice;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.roomCartsList = roomCartsList;
        this.userAddress = userAddress;
    }

    protected OrderRequestModel(Parcel in) {
        requestId = in.readString();
        userId = in.readString();
        nameOfUser = in.readString();
        paymentMethod = in.readString();
        paymentStatus = in.readString();
        userPhone = in.readString();
        totalPrice = in.readString();
        orderTime = in.readString();
        orderStatus = in.readString();
        userAddress = in.readString();
    }

    public static final Creator<OrderRequestModel> CREATOR = new Creator<OrderRequestModel>() {
        @Override
        public OrderRequestModel createFromParcel(Parcel in) {
            return new OrderRequestModel(in);
        }

        @Override
        public OrderRequestModel[] newArray(int size) {
            return new OrderRequestModel[size];
        }
    };

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<RoomCarts> getRoomCartsList() {
        return roomCartsList;
    }

    public void setRoomCartsList(ArrayList<RoomCarts> roomCartsList) {
        this.roomCartsList = roomCartsList;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(requestId);
        dest.writeString(userId);
        dest.writeString(nameOfUser);
        dest.writeString(paymentMethod);
        dest.writeString(paymentStatus);
        dest.writeString(userPhone);
        dest.writeString(totalPrice);
        dest.writeString(orderTime);
        dest.writeString(orderStatus);
        dest.writeString(userAddress);
    }
}
