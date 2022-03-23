package com.gifting.kefi.ui.activities.video_details;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.model.ReelVideoModel;
import com.gifting.kefi.data.model.VideoCommentModel;
import com.gifting.kefi.data.shared_preference.UserSharedPreference;
import com.gifting.kefi.databinding.ActivityVideoDetailsBinding;
import com.gifting.kefi.ui.activities.video_details_youtube.VideoDetailsYoutubeViewModel;
import com.gifting.kefi.ui.adapters.CommentVideosAdapter;
import com.gifting.kefi.ui.adapters.RelatedReelVideosAdapter;
import com.gifting.kefi.ui.fragments.add_comment_reel_video.AddCommentReelVideoFragment;
import com.gifting.kefi.ui.fragments.reel.ReelViewModel;
import com.gifting.kefi.util.CheckInternet;
import com.gifting.kefi.util.Language;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.ArrayList;
import java.util.Collections;

public class VideoDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityVideoDetailsBinding binding;
    private SimpleExoPlayer player;
    private PlayerView playerView;
    private ImageView backImage, fullScreenImageView;
    private boolean fullscreen = false;
    private RelatedReelVideosAdapter adapter;
    private CommentVideosAdapter adapter2;
    private ReelVideoModel reelVideoModel;
    private ArrayList<ReelVideoModel> relatedReelVideoModelArrayList;
    private VideoDetailsYoutubeViewModel mViewModel;
    private ArrayList<VideoCommentModel> videoCommentModelArrayList;
    private boolean isLoadingFromFirst = false;
    private boolean isLastPage = false;
    private UserSharedPreference userSharedPreference;
    private ReelViewModel mReelViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_details);
        mViewModel = new ViewModelProvider(this).get(VideoDetailsYoutubeViewModel.class);
        mReelViewModel = new ViewModelProvider(this).get(ReelViewModel.class);

        userSharedPreference = new UserSharedPreference(this);

        relatedReelVideoModelArrayList = new ArrayList<>();
        reelVideoModel = getIntent().getParcelableExtra("REEL_VIDEO");

        Language.changeBackDependsLanguage(binding.back11, getApplicationContext());


        binding.relatedTab.setOnClickListener(this);
        binding.commentTab.setOnClickListener(this);
        binding.addCommentLayout.setOnClickListener(this);
        binding.likeImage.setOnClickListener(this);
        binding.dislikeImage.setOnClickListener(this);
        binding.shareImage.setOnClickListener(this);


        player = new SimpleExoPlayer.Builder(this).build();
        playerView = findViewById(R.id.player);


        binding.relatedTab.setOnClickListener(this);
        binding.commentTab.setOnClickListener(this);
        binding.addCommentLayout.setOnClickListener(this);
        fullScreenImageView = playerView.findViewById(R.id.exo_fullscreen_icon);
        // backImage = playerView.findViewById(R.id.back11);
        fullScreenImageView.setOnClickListener(this);
        // backImage.setOnClickListener(this);
        // amany
        binding.back11.setOnClickListener(this);


        binding.relatedRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        adapter = new RelatedReelVideosAdapter(this);
        binding.relatedRecyclerView.setAdapter(adapter);

        binding.commentRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        adapter2 = new CommentVideosAdapter(this);
        binding.commentRecycler.setAdapter(adapter2);

        checkInternet();
        callMethodFromFirebase();
        setDataUi();


    }

    private void getRelatedVideos(ArrayList<ReelVideoModel> videoModels) {

        String[] tagsArray = reelVideoModel.getVideoTags().split("\\t|,|;|\\.|\\?|!|-|:|@|\\[|\\]|\\(|\\)|\\{|\\}|_|\\*|/");

        for (int i = 1; i < tagsArray.length; i++) {
            for (int j = 0; j < videoModels.size(); j++) {
                if (videoModels.get(j).getVideoTags().contains(tagsArray[i])) {
                    if (!relatedReelVideoModelArrayList.contains(videoModels.get(j)) && !videoModels.get(j).getId().equals(reelVideoModel.getId()))
                        relatedReelVideoModelArrayList.add(videoModels.get(j));
                }
            }
        }


        if (relatedReelVideoModelArrayList.size() == 0) {
            binding.loadProgress.setVisibility(View.GONE);
            if (binding.relatedRecyclerView.getVisibility() != View.GONE) {
                binding.noCommentText.setText(R.string.no_related_video);
                binding.noCommentText.setVisibility(View.VISIBLE);
            }
        } else {
            binding.loadProgress.setVisibility(View.GONE);
            binding.noCommentText.setVisibility(View.GONE);
        }

        adapter.setRelatedVideo(relatedReelVideoModelArrayList);
    }

    private void setDataUi() {

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


        binding.relatedTab.setCardElevation(6);
        binding.commentTab.setCardElevation(0);

        binding.descriptionText.setTrimCollapsedText(getString(R.string.show_more));
        binding.descriptionText.setTrimExpandedText(getString(R.string.show));

        binding.titleText.setText(reelVideoModel.getTitle());
        binding.descriptionText.setText(reelVideoModel.getDescription());

        playerView.setPlayer(player);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);

        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(reelVideoModel.getLink()));
        player.setMediaItem(mediaItem);
        player.prepare();

    }


    private void callMethodFromFirebase() {

        mViewModel.setViewToVideo(reelVideoModel.getUser_id(), reelVideoModel.getId());

        mViewModel.getTotalViews(reelVideoModel.getId());

        mViewModel.getTotalViewLiveData().observe(this, s -> {
            mViewModel.setTotalViewInReelVideo(reelVideoModel, s);
            binding.viewsText.setText(s + getString(R.string.views));
        });


        mViewModel.getTotalLikes(reelVideoModel.getId());
        mViewModel.getTotalLikes().observe(this, s -> binding.likesText.setText((CharSequence) s));
        mViewModel.getFailText().observe(this, s -> Toast.makeText(this, s, Toast.LENGTH_SHORT).show());

        mViewModel.getTotalDisLikes(reelVideoModel.getId());
        mViewModel.getTotalDisLikes().observe(this, s -> binding.dislikeText.setText((CharSequence) s));
        mViewModel.getFailText().observe(this, s -> Toast.makeText(this, s, Toast.LENGTH_SHORT).show());

        mViewModel.checkLike(reelVideoModel.getId());
        mViewModel.getCheckLikeLiveData().observe(this, s -> {
            mViewModel.getCheckLikeLiveData().removeObservers(this);
            if (s.equals("unlike")) {
                binding.likeImage1.setImageResource(R.drawable.ic_like_video);
                binding.likeImage.setTag("unlike");

            } else {
                binding.likeImage1.setImageResource(R.drawable.ic_liked_videos);
                binding.likeImage.setTag("like");

            }
        });

        mViewModel.checkDisLike(reelVideoModel.getId());
        mViewModel.getCheckDisLikeLiveData().observe(this, s -> {
            mViewModel.getCheckDisLikeLiveData().removeObservers(this);
            if (s.equals("undislike")) {
                binding.dislikeImage1.setImageResource(R.drawable.ic_dislike_video);
                binding.dislikeImage.setTag("undislike");

            } else {
                binding.dislikeImage1.setImageResource(R.drawable.ic_disliked_videos);
                binding.dislikeImage.setTag("dislike");
            }
        });

        getUserIds();
    }

    private void getPosts(ArrayList<String> listUser) {
        mReelViewModel.getPostsOfUsers(listUser);
        mReelViewModel.getArrayListMutableLiveData().observe(this, videoModels -> {
            getRelatedVideos(videoModels);
        });

        mReelViewModel.getFailText().observe(this, s -> {
            mReelViewModel.getFailText().removeObservers(this);
            binding.loadProgress.setVisibility(View.GONE);
            binding.noCommentText.setText(R.string.no_related_video);
            binding.noCommentText.setVisibility(View.VISIBLE);
        });


    }

    private void getUserIds() {
        adapter.removeAllData();
        binding.loadProgress.setVisibility(View.VISIBLE);
        mReelViewModel.getUserFollowingIds(userSharedPreference.getUserDetails().getId());
        mReelViewModel.getFollowingNumberLiveData().observe(this, strings -> {
            mReelViewModel.getFollowingNumberLiveData().removeObservers(this);
            getPosts(strings);
        });

        mReelViewModel.getFailText().observe(this, s -> {
            mReelViewModel.getFailText().removeObservers(this);
            binding.loadProgress.setVisibility(View.GONE);
            binding.noCommentText.setText(R.string.no_related_video);
            binding.noCommentText.setVisibility(View.VISIBLE);
        });
    }

    private void loadFirstPage() {
        adapter2.clear();
        videoCommentModelArrayList = new ArrayList<>();
        mViewModel.getLoadFirstComment(reelVideoModel.getId());
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
                binding.noCommentText.setText(R.string.no_comments);
                binding.noCommentText.setVisibility(View.VISIBLE);
                binding.loadProgress.setVisibility(View.GONE);

            }
        });
    }

    private void loadNextPage() {

        String userId = videoCommentModelArrayList.get(videoCommentModelArrayList.size() - 1).getCommentID();
        mViewModel.getLoadNextReviews(userId, reelVideoModel.getId());
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

    public void updateComment() {
        isLoadingFromFirst = false;
        isLastPage = false;
        loadFirstPage();
    }

    @Override
    public void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
    }

    @Override
    public void onDestroy() {
        player.release();
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        if (v == fullScreenImageView) {
            if (fullscreen) {
                portraitScreen();
            } else {
                landScapeScreen();
            }
        } else if (v == binding.relatedTab) {
            binding.noCommentText.setVisibility(View.GONE);
            binding.loadProgress.setVisibility(View.VISIBLE);
            binding.relatedTab.setCardElevation(6);
            binding.commentTab.setCardElevation(0);
            binding.commentRecycler.setVisibility(View.GONE);
            binding.relatedRecyclerView.setVisibility(View.VISIBLE);
            binding.addCommentLayout.setVisibility(View.GONE);
            getUserIds();
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
            bundle.putString("reelVideoModelId", reelVideoModel.getId());
            AddCommentReelVideoFragment addCommentReelVideoFragment = AddCommentReelVideoFragment.newInstance();
            addCommentReelVideoFragment.setArguments(bundle);
            addCommentReelVideoFragment.show(getSupportFragmentManager(), "addCommentFragment");
        } else if (v == binding.likeImage) {
            if (binding.likeImage.getTag() == "unlike") {
                mViewModel.setLikeToVideo(reelVideoModel.getId());
                binding.likeImage1.setImageResource(R.drawable.ic_liked_videos);
                binding.dislikeImage1.setImageResource(R.drawable.ic_dislike_video);
                binding.likeImage.setTag("like");
                binding.dislikeImage.setTag("undislike");
            } else {
                mViewModel.setUnLikeToVideo(reelVideoModel.getId());
                binding.likeImage1.setImageResource(R.drawable.ic_like_video);
                binding.likeImage.setTag("unlike");
            }
        } else if (v == binding.dislikeImage) {
            if (binding.dislikeImage.getTag().equals("undislike")) {
                mViewModel.setDisLikeToVideo(reelVideoModel.getId());
                binding.dislikeImage1.setImageResource(R.drawable.ic_disliked_videos);
                binding.likeImage1.setImageResource(R.drawable.ic_like_video);
                binding.dislikeImage.setTag("dislike");
                binding.likeImage.setTag("unlike");

            } else {
                mViewModel.setUnDisLikeToVideo(reelVideoModel.getId());
                binding.dislikeImage1.setImageResource(R.drawable.ic_dislike_video);
                binding.dislikeImage.setTag("undislike");
            }
        } else if (v == binding.shareImage) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            final String appPackageName = getPackageName();
            sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.to_watch_this_video_please_join_to_our_community) + " https://play.google.com/store/apps/details?id=" + appPackageName);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, "Share video to..");
            startActivity(shareIntent);
        }
        // amany
        else if (v == binding.back11) {
            onBackPressed();
        }
    }


    private void checkInternet() {
        CheckInternet.hasInternetConnection().subscribe((hasInternet) -> {
            if (!hasInternet) {
                Toast.makeText(getApplicationContext(), getString(R.string.internet_may_be_unstable_or_not_connected), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void portraitScreen() {
        binding.addCommentLayout.setVisibility(View.VISIBLE);

        fullScreenImageView.setImageDrawable(ContextCompat.getDrawable(VideoDetailsActivity.this, R.drawable.ic_full_screen));

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
        }

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) playerView.getLayoutParams();
        params.width = params.MATCH_PARENT;
        params.height = (int) (213 * getApplicationContext().getResources().getDisplayMetrics().density);
        playerView.setLayoutParams(params);

        fullscreen = false;
    }

    private void landScapeScreen() {
        binding.addCommentLayout.setVisibility(View.GONE);

        fullScreenImageView.setImageDrawable(ContextCompat.getDrawable(VideoDetailsActivity.this, R.drawable.ic_full_screen));

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) playerView.getLayoutParams();
        params.width = params.MATCH_PARENT;
        params.height = params.MATCH_PARENT;
        playerView.setLayoutParams(params);

        fullscreen = true;
    }

    @Override
    public void onBackPressed() {
        if (fullscreen) {
            portraitScreen();
        } else {
            super.onBackPressed();
        }
    }
}