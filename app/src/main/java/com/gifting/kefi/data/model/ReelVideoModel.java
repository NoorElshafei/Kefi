package com.gifting.kefi.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class ReelVideoModel implements Parcelable {
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
    @ColumnInfo(name = "videoTags")
    @SerializedName("videoTags")
    @Expose
    private String videoTags;
    @ColumnInfo(name = "userName")
    @SerializedName("userName")
    @Expose
    private String userName;
    @ColumnInfo(name = "userImage")
    @SerializedName("userImage")
    @Expose
    private String userImage;
    @ColumnInfo(name = "time_created")
    @SerializedName("time_created")
    @Expose
    private long time_created;
    @ColumnInfo(name = "user_id")
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @ColumnInfo(name = "total_views")
    @SerializedName("total_views")
    @Expose
    private int total_views;
    @ColumnInfo(name = "status")
    @SerializedName("status")
    @Expose
    private String status;

    public ReelVideoModel() {
    }

    public ReelVideoModel(String description, String duration, String id, String image, String link, String title, String videoTags, String userName, String userImage, long time_created, String user_id, String status) {
        this.description = description;
        this.duration = duration;
        this.id = id;
        this.image = image;
        this.link = link;
        this.title = title;
        this.videoTags = videoTags;
        this.userName = userName;
        this.userImage = userImage;
        this.time_created = time_created;
        this.user_id = user_id;
        this.total_views = 0;
        this.status = status;
    }


    protected ReelVideoModel(Parcel in) {
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
        videoTags = in.readString();
        userName = in.readString();
        userImage = in.readString();
        time_created = in.readLong();
        user_id = in.readString();
        total_views = in.readInt();
        status = in.readString();
    }

    public static final Creator<ReelVideoModel> CREATOR = new Creator<ReelVideoModel>() {
        @Override
        public ReelVideoModel createFromParcel(Parcel in) {
            return new ReelVideoModel(in);
        }

        @Override
        public ReelVideoModel[] newArray(int size) {
            return new ReelVideoModel[size];
        }
    };

    public Long getXid() {
        return xid;
    }

    public void setXid(Long xid) {
        this.xid = xid;
    }

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


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public long getTime_created() {
        return time_created;
    }

    public void setTime_created(long time_created) {
        this.time_created = time_created;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getTotal_views() {
        return total_views;
    }

    public void setTotal_views(int total_views) {
        this.total_views = total_views;
    }

    public String getVideoTags() {
        return videoTags;
    }

    public void setVideoTags(String videoTags) {
        this.videoTags = videoTags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        dest.writeString(videoTags);
        dest.writeString(userName);
        dest.writeString(userImage);
        dest.writeLong(time_created);
        dest.writeString(user_id);
        dest.writeInt(total_views);
        dest.writeString(status);
    }
}
