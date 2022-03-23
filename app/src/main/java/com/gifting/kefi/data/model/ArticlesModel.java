package com.gifting.kefi.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ArticlesModel implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    Long xid;
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "article")
    private String article;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "tips")
    private String tips;
    @ColumnInfo(name = "description_tips")
    private String description_tips;
    @ColumnInfo(name = "imageView")
    private int imageView;
    @ColumnInfo(name = "secondArticle")
    private String secondArticle;
    @ColumnInfo(name = "date_of_article")
    private String date_of_article;



    public ArticlesModel(String id, int imageView, String article, String description, String tips, String description_tips, String secondArticle,String date_of_article) {
        this.id = id;
        this.imageView = imageView;
        this.article = article;
        this.description = description;
        this.tips = tips;
        this.description_tips = description_tips;
        this.secondArticle = secondArticle;
        this.date_of_article = date_of_article;
    }


    protected ArticlesModel(Parcel in) {
        if (in.readByte() == 0) {
            xid = null;
        } else {
            xid = in.readLong();
        }
        id = in.readString();
        article = in.readString();
        description = in.readString();
        tips = in.readString();
        description_tips = in.readString();
        imageView = in.readInt();
        secondArticle = in.readString();
        date_of_article=in.readString();
    }

    public static final Creator<ArticlesModel> CREATOR = new Creator<ArticlesModel>() {
        @Override
        public ArticlesModel createFromParcel(Parcel in) {
            return new ArticlesModel(in);
        }

        @Override
        public ArticlesModel[] newArray(int size) {
            return new ArticlesModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String tips) {
        this.article = article;
    }

    public String getSecondArticle() {
        return secondArticle;
    }

    public void setSecondArticle(String secondArticle) {
        this.secondArticle = secondArticle;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getDescription_tips() {
        return description_tips;
    }

    public void setDescription_tips(String description_tips) {
        this.description_tips = description_tips;
    }

    public String getDate_of_article() {
        return date_of_article;
    }

    public void setDate_of_article(String date_of_article) {
        this.date_of_article = date_of_article;
    }

    public Long getXid() {
        return xid;
    }

    public void setXid(Long xid) {
        this.xid = xid;
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
        dest.writeString(article);
        dest.writeString(description);
        dest.writeString(tips);
        dest.writeString(description_tips);
        dest.writeInt(imageView);
        dest.writeString(secondArticle);
        dest.writeString(date_of_article);
    }
}
