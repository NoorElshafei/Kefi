package com.gifting.kefi.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recently")
public class RecentlyModel  implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int Aid;
    @ColumnInfo(name = "recently_id")
    private String recently_id;
    @ColumnInfo(name = "article")
    private String article;
    @ColumnInfo(name = "description")
    private String  description;
    @ColumnInfo(name = "tips")
    private String tips;
    @ColumnInfo(name = "description_tips")
    private String description_tips;
    @ColumnInfo(name = "imageView")
    private int imageView;
    @ColumnInfo(name = " second_article")
    private String secondArticle;
   public RecentlyModel( ) {

    }


    protected RecentlyModel(Parcel in) {
        Aid = in.readInt();
        recently_id= in.readString();
        article = in.readString();
        description = in.readString();
        tips = in.readString();
        description_tips = in.readString();
        imageView = in.readInt();
        secondArticle = in.readString();
    }

    public static final Creator<RecentlyModel> CREATOR = new Creator<RecentlyModel>() {
        @Override
        public RecentlyModel createFromParcel(Parcel in) {
            return new RecentlyModel(in);
        }

        @Override
        public RecentlyModel[] newArray(int size) {
            return new RecentlyModel[size];
        }
    };

    public String getRecently_id() {
        return recently_id;
    }

    public void setRecently_id(String recently_id) {
        this.recently_id = recently_id;
    }
    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
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


    public int getAid() {
        return Aid;
    }

    public void setAid(int aid) {
        Aid = aid;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(Aid);
        dest.writeString(recently_id);
        dest.writeString(article);
        dest.writeString(description);
        dest.writeString(tips);
        dest.writeString(description_tips);
        dest.writeInt(imageView);
        dest.writeString(secondArticle);
    }
}

