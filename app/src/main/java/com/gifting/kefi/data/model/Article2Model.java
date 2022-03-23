package com.gifting.kefi.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Article2Model implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    Long zid;
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "imageView")
    private int imageView;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "small_title")
    private String small_title;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "article_type")
    private String article_type;

    public Article2Model(String id, int imageView, String title, String small_title, String description, String article_type) {
        this.id = id;
        this.imageView = imageView;
        this.title = title;
        this.small_title = small_title;
        this.description = description;
        this.article_type = article_type;
    }


    protected Article2Model(Parcel in) {
        if (in.readByte() == 0) {
            zid = null;
        } else {
            zid = in.readLong();
        }
        id = in.readString();
        imageView = in.readInt();
        title = in.readString();
        small_title = in.readString();
        description = in.readString();
        article_type = in.readString();
    }

    public static final Creator<Article2Model> CREATOR = new Creator<Article2Model>() {
        @Override
        public Article2Model createFromParcel(Parcel in) {
            return new Article2Model(in);
        }

        @Override
        public Article2Model[] newArray(int size) {
            return new Article2Model[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSmall_title() {
        return small_title;
    }

    public void setSmall_title(String small_title) {
        this.small_title = small_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getZid() {
        return zid;
    }

    public void setZid(Long zid) {
        this.zid = zid;
    }

    public String getArticle_type() {
        return article_type;
    }

    public void setArticle_type(String article_type) {
        this.article_type = article_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (zid == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(zid);
        }
        dest.writeString(id);
        dest.writeInt(imageView);
        dest.writeString(title);
        dest.writeString(small_title);
        dest.writeString(description);
        dest.writeString(article_type);
    }
}
