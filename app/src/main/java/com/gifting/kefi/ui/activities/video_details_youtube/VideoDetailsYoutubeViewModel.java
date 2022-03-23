package com.gifting.kefi.ui.activities.video_details_youtube;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gifting.kefi.data.model.ReelVideoModel;
import com.gifting.kefi.data.model.VideoCommentModel;
import com.gifting.kefi.data.model.VideoModel;

import java.util.ArrayList;

public class VideoDetailsYoutubeViewModel extends ViewModel {
    private MutableLiveData<String> checkLikeLiveData;
    private MutableLiveData<String> checkDisLikeLiveData;
    private MutableLiveData<ArrayList<VideoModel>> videoArrayListMutableLiveData;
    private MutableLiveData<String> failText;
    private VideoDetailsYoutubeRepo<String> videoDetailsYoutubeRepo;
    private VideoDetailsYoutubeRepo<String> videoDetailsYoutubeRepo1;
    private VideoDetailsYoutubeRepo<String> videoDetailsYoutubeRepo4;
    private VideoDetailsYoutubeRepo<ArrayList<VideoCommentModel>> videoDetailsYoutubeRepo2;
    private VideoDetailsYoutubeRepo<ArrayList<VideoCommentModel>> videoDetailsYoutubeRepo3;

    public VideoDetailsYoutubeViewModel() {
        videoDetailsYoutubeRepo = new VideoDetailsYoutubeRepo();
        videoDetailsYoutubeRepo1 = new VideoDetailsYoutubeRepo();


        videoDetailsYoutubeRepo4 = new VideoDetailsYoutubeRepo();
        checkLikeLiveData = videoDetailsYoutubeRepo.getCheckLikeLiveData();
        videoArrayListMutableLiveData = videoDetailsYoutubeRepo.getVideoArrayListMutableLiveData();
        checkDisLikeLiveData = videoDetailsYoutubeRepo1.getCheckDisLikeLiveData();
        failText = videoDetailsYoutubeRepo.getFailText();
    }

    public void setViewToVideo(String userId, String videoId) {
        videoDetailsYoutubeRepo.setViewToVideo(userId, videoId);
    }

    public void getTotalViews(String videoId) {
        videoDetailsYoutubeRepo4.getTotalViews(videoId);
    }

    public void getTotalLikes(String videoId) {
        videoDetailsYoutubeRepo.getTotalLikes(videoId);
    }

    public void setTotalViewInReelVideo(ReelVideoModel reelVideo, String s) {
        videoDetailsYoutubeRepo.setTotalViewInReelVideo(reelVideo, s);
    }

    public void getRelatedVideos(String videoType, String videoId, String language) {
        videoDetailsYoutubeRepo.getRelatedVideo(videoType, videoId,language);
    }

    public void getTotalDisLikes(String videoId) {
        videoDetailsYoutubeRepo1.getTotalDisLikes(videoId);
    }

    public void setLikeToVideo(String videoId) {
        videoDetailsYoutubeRepo.setLikeToVideo(videoId);
    }

    public void setUnLikeToVideo(String videoId) {
        videoDetailsYoutubeRepo.setUnLikeToVideo(videoId);
    }

    public void setDisLikeToVideo(String videoId) {
        videoDetailsYoutubeRepo.setDisLikeToVideo(videoId);
    }

    public void setUnDisLikeToVideo(String videoId) {
        videoDetailsYoutubeRepo.setUnDisLikeToVideo(videoId);
    }

    public void checkLike(String videoId) {
        videoDetailsYoutubeRepo.checkLike(videoId);
    }

    public void checkDisLike(String videoId) {
        videoDetailsYoutubeRepo1.checkDisLike(videoId);
    }

    public void getLoadFirstComment(String videoId) {
        videoDetailsYoutubeRepo2 = new VideoDetailsYoutubeRepo();
        failText = videoDetailsYoutubeRepo2.getFailText();
        videoDetailsYoutubeRepo2.getFirstComments(videoId);
    }

    public void getLoadNextReviews(String userId, String videoId) {
        videoDetailsYoutubeRepo3 = new VideoDetailsYoutubeRepo();
        failText = videoDetailsYoutubeRepo3.getFailText();
        videoDetailsYoutubeRepo3.getNextComments(userId, videoId);
    }


    public MutableLiveData<String> getFailText() {
        return failText;
    }

    public MutableLiveData<String> getCheckLikeLiveData() {
        return checkLikeLiveData;
    }

    public MutableLiveData<String> getCheckDisLikeLiveData() {
        return checkDisLikeLiveData;
    }

    public VideoDetailsYoutubeRepo getTotalLikes() {
        return videoDetailsYoutubeRepo;
    }

    public VideoDetailsYoutubeRepo getTotalDisLikes() {
        return videoDetailsYoutubeRepo1;
    }

    public MutableLiveData<ArrayList<VideoModel>> getVideoArrayListMutableLiveData() {
        return videoArrayListMutableLiveData;
    }

    public VideoDetailsYoutubeRepo<ArrayList<VideoCommentModel>> getVideoDetailsYoutubeRepo2() {
        return videoDetailsYoutubeRepo2;
    }

    public VideoDetailsYoutubeRepo<ArrayList<VideoCommentModel>> getVideoDetailsYoutubeRepo3() {
        return videoDetailsYoutubeRepo3;
    }

    public VideoDetailsYoutubeRepo<String> getTotalViewLiveData() {
        return videoDetailsYoutubeRepo4;
    }

}
