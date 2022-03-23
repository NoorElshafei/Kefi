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
public class VideoModel implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    Long xid;
    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    private String description;
    @ColumnInfo(name = "duration")
    @SerializedName("duration")
    @Expose
    private String duration;
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private String id;
    @ColumnInfo(name = "image")
    @SerializedName("image")
    @Expose
    private String image;
    @ColumnInfo(name = "link")
    @SerializedName("link")
    @Expose
    private String link;
    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    private String title;
    @ColumnInfo(name = "videoType")
    @SerializedName("videoType")
    @Expose
    private String videoType;
    @ColumnInfo(name = "smallTitle")
    @SerializedName("smallTitle")
    @Expose
    private String smallTitle;

    public VideoModel(String id) {
        this.id = id;
    }

    @Ignore
    public VideoModel() {
    }


    protected VideoModel(Parcel in) {
        if (in.readByte() == 0) {
            xid = null;
        } else {
            xid = in.readLong();
        }
        description = in.readString();
        duration = in.readString();
        id = in.readString();
        image = in.readString();
        link = in.readString();
        title = in.readString();
        videoType = in.readString();
        smallTitle = in.readString();
    }

    public static final Creator<VideoModel> CREATOR = new Creator<VideoModel>() {
        @Override
        public VideoModel createFromParcel(Parcel in) {
            return new VideoModel(in);
        }

        @Override
        public VideoModel[] newArray(int size) {
            return new VideoModel[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getSmallTitle() {
        return smallTitle;
    }

    public void setSmallTitle(String smallTitle) {
        this.smallTitle = smallTitle;
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
        dest.writeString(description);
        dest.writeString(duration);
        dest.writeString(id);
        dest.writeString(image);
        dest.writeString(link);
        dest.writeString(title);
        dest.writeString(videoType);
        dest.writeString(smallTitle);
    }
}