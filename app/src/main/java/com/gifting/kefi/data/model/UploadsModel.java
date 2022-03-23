package com.gifting.kefi.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UploadsModel {
    @PrimaryKey(autoGenerate = true)
    Long xid;
    @ColumnInfo(name = "videoFolder")
    private String videoFolder;
    @ColumnInfo(name = "videoId")
    private String videoId;
    @ColumnInfo(name = "status")
    private String status;

    public UploadsModel(String videoFolder, String videoId,String status) {
        this.videoFolder = videoFolder;
        this.videoId = videoId;
        this.status = status;
    }

    public Long getXid() {
        return xid;
    }

    public void setXid(Long xid) {
        this.xid = xid;
    }

    public String getVideoFolder() {
        return videoFolder;
    }

    public void setVideoFolder(String videoFolder) {
        this.videoFolder = videoFolder;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
