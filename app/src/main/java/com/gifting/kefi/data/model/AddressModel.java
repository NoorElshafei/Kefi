package com.gifting.kefi.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressModel implements Parcelable {

    @SerializedName("city_name")
    @Expose
    private String city_name;
    @SerializedName("district_or_area_name")
    @Expose
    private String district_or_area_name;
    @SerializedName("house_and_apartment")
    @Expose
    private String house_and_apartment;
    @SerializedName("street_name")
    @Expose
    private String street_name;
    @SerializedName("Address_id")
    @Expose
    private String Address_id;

    public AddressModel() {
    }

    public AddressModel(String city_name, String district_or_area_name, String house_and_apartment, String street_name, String address_id) {
        this.city_name = city_name;
        this.district_or_area_name = district_or_area_name;
        this.house_and_apartment = house_and_apartment;
        this.street_name = street_name;
        Address_id = address_id;
    }

    protected AddressModel(Parcel in) {
        city_name = in.readString();
        district_or_area_name = in.readString();
        house_and_apartment = in.readString();
        street_name = in.readString();
        Address_id = in.readString();
    }

    public static final Creator<AddressModel> CREATOR = new Creator<AddressModel>() {
        @Override
        public AddressModel createFromParcel(Parcel in) {
            return new AddressModel(in);
        }

        @Override
        public AddressModel[] newArray(int size) {
            return new AddressModel[size];
        }
    };

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getDistrict_or_area_name() {
        return district_or_area_name;
    }

    public void setDistrict_or_area_name(String district_or_area_name) {
        this.district_or_area_name = district_or_area_name;
    }

    public String getHouse_and_apartment() {
        return house_and_apartment;
    }

    public void setHouse_and_apartment(String house_and_apartment) {
        this.house_and_apartment = house_and_apartment;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getAddress_id() {
        return Address_id;
    }

    public void setAddress_id(String address_id) {
        Address_id = address_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(city_name);
        dest.writeString(district_or_area_name);
        dest.writeString(house_and_apartment);
        dest.writeString(street_name);
        dest.writeString(Address_id);


    }
}