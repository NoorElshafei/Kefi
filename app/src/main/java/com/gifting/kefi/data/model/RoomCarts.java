package com.gifting.kefi.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class RoomCarts implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    Long xid;
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "brand")
    private String brand;
    @ColumnInfo(name = "category")
    private String category;
    @ColumnInfo(name = "condition")
    private String condition;
    @ColumnInfo(name = "material")
    private String material;
    @ColumnInfo(name = "picture")
    private String picture;
    @ColumnInfo(name = "price")
    private Double price;
    @ColumnInfo(name = "rate")
    private Float rate;
    @ColumnInfo(name = "search")
    private String search;
    @ColumnInfo(name = "serial")
    private String serial;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "quantity")
    private Integer quantity;
    @ColumnInfo(name = "inventory_quantity")
    private Integer inventoryQuantity;

    @Ignore
    public RoomCarts() {
    }

    public RoomCarts(String id, String name, String brand, String category, String condition, String material, String picture, Double price, Float rate, String search, String description, String serial, Integer inventoryQuantity) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.condition = condition;
        this.material = material;
        this.picture = picture;
        this.price = price;
        this.rate = rate;
        this.search = search;
        this.description = description;
        this.serial = serial;
        this.inventoryQuantity = inventoryQuantity;
        this.quantity = 1;
    }

    protected RoomCarts(Parcel in) {
        if (in.readByte() == 0) {
            xid = null;
        } else {
            xid = in.readLong();
        }
        id = in.readString();
        name = in.readString();
        brand = in.readString();
        category = in.readString();
        condition = in.readString();
        material = in.readString();
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
        description = in.readString();
        if (in.readByte() == 0) {
            quantity = null;
        } else {
            quantity = in.readInt();
        }
        if (in.readByte() == 0) {
            inventoryQuantity = null;
        } else {
            inventoryQuantity = in.readInt();
        }
    }

    public static final Creator<RoomCarts> CREATOR = new Creator<RoomCarts>() {
        @Override
        public RoomCarts createFromParcel(Parcel in) {
            return new RoomCarts(in);
        }

        @Override
        public RoomCarts[] newArray(int size) {
            return new RoomCarts[size];
        }
    };

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Long getXid() {
        return xid;
    }

    public void setXid(Long xid) {
        this.xid = xid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(Integer inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
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
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(brand);
        dest.writeString(category);
        dest.writeString(condition);
        dest.writeString(material);
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
        dest.writeString(description);
        if (quantity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(quantity);
        }
        if (inventoryQuantity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(inventoryQuantity);
        }
    }
}
