package com.gifting.kefi.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Products implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    Long xid;
    @ColumnInfo(name = "brand")
    @SerializedName("brand")
    @Expose
    private String brand;
    @ColumnInfo(name = "category")
    @SerializedName("category")
    @Expose
    private String category;
    @ColumnInfo(name = "condition")
    @SerializedName("condition")
    @Expose
    private String condition;
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private String id;
    @ColumnInfo(name = "material")
    @SerializedName("material")
    @Expose
    private String material;
    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    private String name;
    @ColumnInfo(name = "picture")
    @SerializedName("picture")
    @Expose
    private String picture;
    @ColumnInfo(name = "price")
    @SerializedName("price")
    @Expose
    private Double price;
    @ColumnInfo(name = "rate")
    @SerializedName("rate")
    @Expose
    private Float rate;
    @ColumnInfo(name = "search")
    @SerializedName("search")
    @Expose
    private String search;
    @ColumnInfo(name = "serial")
    @SerializedName("serial")
    @Expose
    private String serial;
    @ColumnInfo(name = "inventory_number")
    @SerializedName("inventory_number")
    @Expose
    private Integer inventoryNumber;
    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("product_type")
    @Expose
    private String product_type;

    @Ignore
    public Products() {
    }

    public Products(String brand, String category, String condition, String id, String material, String name, String picture, Double price, Float rate, String search, String serial, Integer inventoryNumber, String description, String product_type) {
        this.brand = brand;
        this.category = category;
        this.condition = condition;
        this.id = id;
        this.material = material;
        this.name = name;
        this.picture = picture;
        this.price = price;
        this.rate = rate;
        this.search = search;
        this.serial = serial;
        this.inventoryNumber = inventoryNumber;
        this.description = description;
        this.product_type = product_type;
    }


    protected Products(Parcel in) {
        if (in.readByte() == 0) {
            xid = null;
        } else {
            xid = in.readLong();
        }
        brand = in.readString();
        category = in.readString();
        condition = in.readString();
        id = in.readString();
        material = in.readString();
        name = in.readString();
        picture = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        if (in.readByte() == 0) {
            rate = null;
        } else {
            rate = in.readFloat();
        }
        search = in.readString();
        serial = in.readString();
        if (in.readByte() == 0) {
            inventoryNumber = null;
        } else {
            inventoryNumber = in.readInt();
        }
        description = in.readString();
        product_type = in.readString();
    }

    public static final Creator<Products> CREATOR = new Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel in) {
            return new Products(in);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Integer getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(Integer inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getXid() {
        return xid;
    }

    public void setXid(Long xid) {
        this.xid = xid;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (xid == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(xid);
        }
        dest.writeString(brand);
        dest.writeString(category);
        dest.writeString(condition);
        dest.writeString(id);
        dest.writeString(material);
        dest.writeString(name);
        dest.writeString(picture);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(price);
        }
        if (rate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(rate);
        }
        dest.writeString(search);
        dest.writeString(serial);
        if (inventoryNumber == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(inventoryNumber);
        }
        dest.writeString(description);
        dest.writeString(product_type);
    }
}
