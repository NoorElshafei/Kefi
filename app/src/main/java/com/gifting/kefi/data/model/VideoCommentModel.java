package com.gifting.kefi.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoCommentModel {

    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("videoID")
    @Expose
    private String videoID;
    @SerializedName("commentID")
    @Expose
    private String commentID;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("commentDate")
    @Expose
    private long commentDate;

    public VideoCommentModel() {
    }

    public VideoCommentModel(String comment, String videoID, String userID, String userName,long commentDate) {
        this.comment = comment;
        this.videoID = videoID;
        this.userID = userID;
        this.userName = userName;
        this.commentDate = commentDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(long commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }
}
