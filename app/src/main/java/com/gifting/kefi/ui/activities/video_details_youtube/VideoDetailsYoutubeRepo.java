package com.gifting.kefi.ui.activities.video_details_youtube;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gifting.kefi.data.model.ReelVideoModel;
import com.gifting.kefi.data.model.VideoCommentModel;
import com.gifting.kefi.data.model.VideoModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VideoDetailsYoutubeRepo<T> extends LiveData<T> {

    private MutableLiveData<String> checkLikeLiveData;
    private MutableLiveData<String> checkDisLikeLiveData;
    private MutableLiveData<ArrayList<VideoModel>> videoArrayListMutableLiveData;
    private MutableLiveData<ArrayList<VideoModel>> videoArrayListMutableLiveData1;
    private MutableLiveData<ArrayList<ReelVideoModel>> reelVideoArrayListMutableLiveData;
    private ArrayList<VideoModel> videoModelArrayList;
    private ArrayList<String> usersIds;
    private MutableLiveData<ArrayList<String>> usersIdsMutableLiveData;
    private MutableLiveData<String> failText;
    private DatabaseReference reference, reference1, referenceDisLike, reference2, reference3, reference4, reference5, reference6, reference7;
    private Query query, query1;
    private FirebaseUser firebaseUser;
    private MyValueEventListener listener = new MyValueEventListener();
    private MyValueEventListener listener1 = new MyValueEventListener();
    private MyValueEventListener1 listener2 = new MyValueEventListener1();
    private ArrayList<VideoCommentModel> videoCommentModelArrayList;
    private ArrayList<ReelVideoModel> reelVideoModels;


    public VideoDetailsYoutubeRepo() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        checkLikeLiveData = new MutableLiveData<>();
        checkDisLikeLiveData = new MutableLiveData<>();
        videoArrayListMutableLiveData = new MutableLiveData<>();
        videoArrayListMutableLiveData1 = new MutableLiveData<>();
        reelVideoArrayListMutableLiveData = new MutableLiveData<>();
        usersIdsMutableLiveData = new MutableLiveData<>();
        failText = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("VideoStructure").child("Likes");
        reference2 = FirebaseDatabase.getInstance().getReference("VideoStructure").child("ViewsVideo");
        reference3 = FirebaseDatabase.getInstance().getReference("VideoStructure").child("ViewsVideo");
        reference4 = FirebaseDatabase.getInstance().getReference("VideoStructure").child("Videos");
        reference5 = FirebaseDatabase.getInstance().getReference("ReelVideos");
        reference6 = FirebaseDatabase.getInstance().getReference();
        reference7 = FirebaseDatabase.getInstance().getReference();

        referenceDisLike = FirebaseDatabase.getInstance().getReference("VideoStructure").child("DisLikes");

        query1 = FirebaseDatabase.getInstance().getReference().child("ReelVideos");
        reference1 = FirebaseDatabase.getInstance().getReference("VideoStructure").child("Comments");
        videoModelArrayList = new ArrayList<>();
        reelVideoModels = new ArrayList<>();
        usersIds = new ArrayList<>();
    }

    public void getRelatedVideo(String videoType, String videoId, String language) {
        query = FirebaseDatabase.getInstance().getReference("VideoStructure").child("Videos").child(language).orderByChild("videoType");
        query.equalTo(videoType).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        VideoModel videoModel = snapshot1.getValue(VideoModel.class);
                        if (!videoModel.getId().equals(videoId))
                            videoModelArrayList.add(videoModel);
                    }
                    videoArrayListMutableLiveData.postValue(videoModelArrayList);
                } else {
                    checkLikeLiveData.postValue("unlike");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void getTotalLikes(String videoId) {
        Log.d("sadsadsad", "onActive: ");
        reference.child(videoId).addValueEventListener(listener);
    }

    public void setTotalViewInReelVideo(ReelVideoModel reelVideo, String s) {
        reference5.child(reelVideo.getUser_id()).child(reelVideo.getId()).child("total_views").setValue(Integer.parseInt(s)).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                failText.postValue(task.getException().getMessage());
            }
        });
    }


    public void getTotalViews(String videoId) {
        Log.d("sadsadsad", "onActive: ");
        reference3.child(videoId).addValueEventListener(listener);
    }

    public void getTotalDisLikes(String videoId) {
        Log.d("sadsadsad", "onActive: ");
        referenceDisLike.child(videoId).addValueEventListener(listener1);
    }

    public void setLikeToVideo(String VideoId) {
        setUnDisLikeToVideo(VideoId);
        reference.child(VideoId).child(firebaseUser.getUid()).setValue(true).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                failText.postValue(task.getException().getMessage());
            }
        });
    }


    public void setViewToVideo(String userId, String VideoId) {
        reference2.child(VideoId).child(userId).setValue(true).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                failText.postValue(task.getException().getMessage());
            }
        });
    }

    public void setUnLikeToVideo(String videoId) {
        reference.child(videoId).child(firebaseUser.getUid()).removeValue().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                failText.postValue(task.getException().getMessage());
            }
        });
    }

    public void setDisLikeToVideo(String VideoId) {
        setUnLikeToVideo(VideoId);
        referenceDisLike.child(VideoId).child(firebaseUser.getUid()).setValue(true).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                failText.postValue(task.getException().getMessage());
            }
        });
    }


    public void setUnDisLikeToVideo(String VideoId) {
        referenceDisLike.child(VideoId).child(firebaseUser.getUid()).removeValue().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                failText.postValue(task.getException().getMessage());
            }
        });
    }


    public void checkLike(String videoId) {
        reference.child(videoId).child(firebaseUser.getUid()).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    checkLikeLiveData.postValue("like");
                } else {
                    checkLikeLiveData.postValue("unlike");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                failText.postValue(error.getMessage());
            }
        });

    }

    public void checkDisLike(String videoId) {
        referenceDisLike.child(videoId).child(firebaseUser.getUid()).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    checkDisLikeLiveData.postValue("dislike");
                } else {
                    checkDisLikeLiveData.postValue("undislike");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                failText.postValue(error.getMessage());
            }
        });

    }

    public void getFirstComments(String VideoId) {
        reference1.child(VideoId).orderByKey().limitToLast(10).addValueEventListener(listener2);
    }

    public void getNextComments(String userId, String videoId) {
        reference1.child(videoId).orderByKey().endBefore(userId).limitToLast(10).addValueEventListener(listener2);
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            int count = 0;
            if (snapshot.exists()) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    count++;
                }
            }
            postValue((T) (count + ""));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            failText.postValue(error.getMessage());
        }

    }

    private class MyValueEventListener1 implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            videoCommentModelArrayList = new ArrayList<>();
            if (snapshot.exists()) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    VideoCommentModel videoCommentModel = snapshot1.getValue(VideoCommentModel.class);
                    videoCommentModelArrayList.add(videoCommentModel);
                }
                postValue((T) videoCommentModelArrayList);
            } else {
                failText.postValue("No comments found");

            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.e("nooooooooooooooor", "Can't listen to query " + query, error.toException());

        }
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

    public MutableLiveData<ArrayList<VideoModel>> getVideoArrayListMutableLiveData() {
        return videoArrayListMutableLiveData;
    }

    public MutableLiveData<ArrayList<VideoModel>> getVideoArrayListMutableLiveData1() {
        return videoArrayListMutableLiveData1;
    }

    public MutableLiveData<ArrayList<String>> getUsersIdsMutableLiveData() {
        return usersIdsMutableLiveData;
    }

    public MutableLiveData<ArrayList<ReelVideoModel>> getReelVideoArrayListMutableLiveData() {
        return reelVideoArrayListMutableLiveData;
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        reference.removeEventListener(listener);
        referenceDisLike.removeEventListener(listener1);
        reference1.removeEventListener(listener2);

        Log.d("sadsadsad", "onInactive: ");
    }


}
