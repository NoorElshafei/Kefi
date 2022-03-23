package com.gifting.kefi.ui.activities.video_details_youtube;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.VideoCommentModel;
import com.gifting.kefi.data.model.VideoModel;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivityVideoDetailsYoutubeBinding;
import com.gifting.kefi.ui.adapters.CommentYoutubeVideosAdapter;
import com.gifting.kefi.ui.adapters.RelatedVideosAdapter;
import com.gifting.kefi.ui.fragments.add_comment_youtube_video.AddCommentYoutubeVideoFragment;
import com.gifting.kefi.util.CheckInternet;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;

public class VideoDetailsYoutubeActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityVideoDetailsYoutubeBinding binding;
    private VideoDetailsYoutubeViewModel mViewModel;
    private VideoModel videoModel;
    private RelatedVideosAdapter adapter;
    private CommentYoutubeVideosAdapter adapter2;
    private ArrayList<VideoCommentModel> videoCommentModelArrayList;
    private boolean isLoadingFromFirst = false;
    private boolean isLastPage = false;
    //private YouTubePlayer youTubePlayer1;
    private boolean isFullScreen = false;
    private UserSharedPreference userSharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_details_youtube);
        mViewModel = new ViewModelProvider(this).get(VideoDetailsYoutubeViewModel.class);
        userSharedPreference = new UserSharedPreference(getApplicationContext());

        checkInternet();

        binding.relatedTab.setOnClickListener(this);
        binding.commentTab.setOnClickListener(this);
        binding.addCommentLayout.setOnClickListener(this);
        binding.likeImage.setOnClickListener(this);
        binding.dislikeImage.setOnClickListener(this);
        binding.shareImage.setOnClickListener(this);


        binding.relatedRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        adapter = new RelatedVideosAdapter(this);
        binding.relatedRecyclerView.setAdapter(adapter);

        binding.commentRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        adapter2 = new CommentYoutubeVideosAdapter(this);
        binding.commentRecycler.setAdapter(adapter2);


        binding.nestedScroll.getViewTreeObserver()
                .addOnScrollChangedListener(() -> {
                    if (binding.relatedRecyclerView.getVisibility() == View.GONE && !isLastPage && isLoadingFromFirst) {
                        if (binding.nestedScroll.getChildAt(0).getBottom() <= (binding.nestedScroll.getHeight() + binding.nestedScroll.getScrollY())) {
                            //scroll view is at bottom
                            loadNextPage();
                            Log.d("noooor", "onScrollChanged: ");
                        }
                    }
                });

        videoModel = getIntent().getParcelableExtra("VideoModel");
        setDataUi();
        callMethodFromFirebase();

    }

    private void loadFirstPage() {
        adapter2.clear();
        videoCommentModelArrayList = new ArrayList<>();
        mViewModel.getLoadFirstComment(videoModel.getId());
        mViewModel.getVideoDetailsYoutubeRepo2().observe(this, reviewModels -> {
            mViewModel.getVideoDetailsYoutubeRepo2().removeObservers(this);
            binding.noCommentText.setVisibility(View.GONE);
            binding.loadProgress.setVisibility(View.GONE);


            ArrayList<VideoCommentModel> reversed = new ArrayList<>();
            reversed.addAll(reviewModels);
            Collections.reverse(reversed);


            videoCommentModelArrayList.addAll(reversed);

            adapter2.addAll(reversed);
            isLoadingFromFirst = true;
            if (reviewModels.size() == 10) adapter2.addLoadingFooter();
            else isLastPage = true;

        });
        mViewModel.getFailText().observe(this, s -> {
            mViewModel.getFailText().removeObservers(this);
            if (videoCommentModelArrayList.size() == 0) {
                binding.noCommentText.setVisibility(View.VISIBLE);
                binding.loadProgress.setVisibility(View.GONE);
            }
        });

    }

    private void loadNextPage() {
        String userId = videoCommentModelArrayList.get(videoCommentModelArrayList.size() - 1).getCommentID();
        mViewModel.getLoadNextReviews(userId, videoModel.getId());
        mViewModel.getVideoDetailsYoutubeRepo3().observe(this, reviewModels -> {
            mViewModel.getVideoDetailsYoutubeRepo3().removeObservers(this);
            adapter2.removeLoadingFooter(); // 2

            ArrayList<VideoCommentModel> reversed = new ArrayList<>();
            reversed.addAll(reviewModels);
            Collections.reverse(reversed);


            videoCommentModelArrayList.addAll(reversed);

            adapter2.addAll(reversed);
            if (reviewModels.size() == 10) adapter2.addLoadingFooter();
            else isLastPage = true;

        });

        mViewModel.getFailText().observe(this, s -> {
            mViewModel.getFailText().removeObservers(this);
            isLastPage = true;
            adapter2.removeLoadingFooter();
        });

    }

    private void callMethodFromFirebase() {

        mViewModel.setViewToVideo(FirebaseAuth.getInstance().getUid(), videoModel.getId());

        mViewModel.getTotalViews(videoModel.getId());
        mViewModel.getTotalViewLiveData().observe(this, s -> binding.viewsText.setText(s + getString(R.string.views)));


        mViewModel.getTotalLikes(videoModel.getId());
        mViewModel.getTotalLikes().observe(this, s -> binding.likesText.setText((CharSequence) s));
        mViewModel.getFailText().observe(this, s -> {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

        });

        mViewModel.getTotalDisLikes(videoModel.getId());
        mViewModel.getTotalDisLikes().observe(this, s -> binding.dislikeText.setText((CharSequence) s));
        mViewModel.getFailText().observe(this, s -> {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

        });

        mViewModel.checkLike(videoModel.getId());
        mViewModel.getCheckLikeLiveData().observe(this, s -> {
            if (s.equals("unlike")) {
                binding.likeImage1.setImageResource(R.drawable.ic_like_video);
                binding.likeImage.setTag("unlike");

            } else {
                binding.likeImage1.setImageResource(R.drawable.ic_liked_videos);
                binding.likeImage.setTag("like");

            }
        });

        mViewModel.checkDisLike(videoModel.getId());
        mViewModel.getCheckDisLikeLiveData().observe(this, s -> {
            if (s.equals("undislike")) {
                binding.dislikeImage1.setImageResource(R.drawable.ic_dislike_video);
                binding.dislikeImage.setTag("undislike");

            } else {
                binding.dislikeImage1.setImageResource(R.drawable.ic_disliked_videos);
                binding.dislikeImage.setTag("dislike");
            }
        });
        binding.loadProgress.setVisibility(View.VISIBLE);

        mViewModel.getRelatedVideos(videoModel.getVideoType(), videoModel.getId(), userSharedPreference.getLanguage());
        mViewModel.getVideoArrayListMutableLiveData().observe(this, videoModels -> {
            binding.loadProgress.setVisibility(View.GONE);
            adapter.setRelatedVideo(videoModels);
        });
    }

    private void setDataUi() {
        binding.relatedTab.setCardElevation(6);
        binding.commentTab.setCardElevation(0);

        binding.descriptionText.setTrimCollapsedText(getString(R.string.show_more));
        binding.descriptionText.setTrimExpandedText(getString(R.string.show_less));

        binding.titleText.setText(videoModel.getTitle());
        binding.descriptionText.setText(videoModel.getDescription());

       /* YouTubePlayerFragment youtubeFragment = (YouTubePlayerFragment)
                getFragmentManager().findFragmentById(R.id.youtubeFragment);

        youtubeFragment.initialize("AIzaSyBPKuUk86TVQq_vTNZnY0MttjuPRCwT-00", new YouTubePlayer.OnInitializedListener() {


            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer1 = youTubePlayer;
                youTubePlayer.loadVideo(videoModel.getLink());

                youTubePlayer.setOnFullscreenListener(b1 -> {
                    isFullScreen = b1;
                });

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(VideoDetailsYoutubeActivity.this, youTubeInitializationResult.name(), Toast.LENGTH_SHORT).show();

            }
        });
*/

    }

    public void updateComment() {
        isLoadingFromFirst = false;
        isLastPage = false;
        loadFirstPage();
    }


    @Override
    public void onBackPressed() {
     /*   if (youTubePlayer1 != null && isFullScreen) {
            youTubePlayer1.setFullscreen(false);
        } else {
            super.onBackPressed();
        }*/
    }

    @Override
    public void onClick(View v) {
        if (v == binding.relatedTab) {
            binding.noCommentText.setVisibility(View.GONE);
            binding.loadProgress.setVisibility(View.GONE);
            binding.relatedTab.setCardElevation(6);
            binding.commentTab.setCardElevation(0);
            binding.commentRecycler.setVisibility(View.GONE);
            binding.relatedRecyclerView.setVisibility(View.VISIBLE);
            binding.addCommentLayout.setVisibility(View.GONE);

        } else if (v == binding.commentTab) {
            isLoadingFromFirst = false;
            isLastPage = false;
            binding.noCommentText.setVisibility(View.GONE);
            binding.loadProgress.setVisibility(View.VISIBLE);

            loadFirstPage();
            binding.relatedTab.setCardElevation(0);
            binding.commentTab.setCardElevation(6);
            binding.relatedRecyclerView.setVisibility(View.GONE);
            binding.commentRecycler.setVisibility(View.VISIBLE);
            binding.addCommentLayout.setVisibility(View.VISIBLE);


        } else if (v == binding.addCommentLayout) {
            Bundle bundle = new Bundle();
            bundle.putString("VideoId", videoModel.getId());
            AddCommentYoutubeVideoFragment addCommentVideoFragment = AddCommentYoutubeVideoFragment.newInstance();
            addCommentVideoFragment.setArguments(bundle);
            addCommentVideoFragment.show(getSupportFragmentManager(), "addCommentFragment");
        } else if (v == binding.likeImage) {
            if (binding.likeImage.getTag() == "unlike") {
                mViewModel.setLikeToVideo(videoModel.getId());
                binding.likeImage1.setImageResource(R.drawable.ic_liked_videos);
                binding.dislikeImage1.setImageResource(R.drawable.ic_dislike_video);
                binding.likeImage.setTag("like");
                binding.dislikeImage.setTag("undislike");
            } else {
                mViewModel.setUnLikeToVideo(videoModel.getId());
                binding.likeImage1.setImageResource(R.drawable.ic_like_video);
                binding.likeImage.setTag("unlike");
            }
        } else if (v == binding.dislikeImage) {
            if (binding.dislikeImage.getTag().equals("undislike")) {
                mViewModel.setDisLikeToVideo(videoModel.getId());
                binding.dislikeImage1.setImageResource(R.drawable.ic_disliked_videos);
                binding.likeImage1.setImageResource(R.drawable.ic_like_video);
                binding.dislikeImage.setTag("dislike");
                binding.likeImage.setTag("unlike");

            } else {
                mViewModel.setUnDisLikeToVideo(videoModel.getId());
                binding.dislikeImage1.setImageResource(R.drawable.ic_dislike_video);
                binding.dislikeImage.setTag("undislike");
            }
        } else if (v == binding.shareImage) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v=" + videoModel.getLink());
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, "Share video to..");
            startActivity(shareIntent);
        }
    }

    private void checkInternet() {
        CheckInternet.hasInternetConnection().subscribe((hasInternet) -> {
            if (!hasInternet) {
                Toast.makeText(getApplicationContext(), getString(R.string.internet_may_be_unstable_or_not_connected), Toast.LENGTH_LONG).show();

            }
        });
    }

}