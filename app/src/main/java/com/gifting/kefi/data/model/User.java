package com.gifting.kefi.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    @SerializedName("birth_date")
    @Expose
    private long birthDate;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("imageURL")
    @Expose
    private String imageURL;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("search")
    @Expose
    private String search;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("skinType")
    @Expose
    private String skinType;
    @SerializedName("isQuestionAnswered")
    @Expose
    private boolean isQuestionAnswered;
    @SerializedName("headLine")
    @Expose
    private String headLine;


    public User() {
    }

    public User(String id, String email, String userName, String name, String phoneNumber, long birthDate, String imageURL, String search, String status, String gender, String skinType, boolean isQuestionAnswered) {
        this.birthDate = birthDate;
        this.email = email;
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.phoneNumber = phoneNumber;
        this.search = search;
        this.status = status;
        this.userName = userName;
        this.gender = gender;
        this.skinType = skinType;
        this.isQuestionAnswered = isQuestionAnswered;
        this.headLine = "";
    }

    protected User(Parcel in) {
        birthDate = in.readLong();
        email = in.readString();
        id = in.readString();
        name = in.readString();
        imageURL = in.readString();
        phoneNumber = in.readString();
        search = in.readString();
        status = in.readString();
        userName = in.readString();
        gender = in.readString();
        skinType = in.readString();
        isQuestionAnswered = in.readByte() != 0;
        headLine = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public boolean isQuestionAnswered() {
        return isQuestionAnswered;
    }

    public void setQuestionAnswered(boolean questionAnswered) {
        isQuestionAnswered = questionAnswered;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setSkinType(String skinType) {
        this.skinType = skinType;
    }

    public String getSkinType() {
        return skinType;
    }

    public String getGender() {
        return gender;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(birthDate);
        dest.writeString(email);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(imageURL);
        dest.writeString(phoneNumber);
        dest.writeString(search);
        dest.writeString(status);
        dest.writeString(userName);
        dest.writeString(gender);
        dest.writeString(skinType);
        dest.writeByte((byte) (isQuestionAnswered ? 1 : 0));
        dest.writeString(headLine);
    }
}
