package com.gifting.kefi.ui.fragments.liked;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gifting.kefi.R;
import com.gifting.kefi.data.local_database.AppRoomDatabase;
import com.gifting.kefi.data.model.Article2Model;
import com.gifting.kefi.data.model.ArticlesModel;
import com.gifting.kefi.data.model.Products;
import com.gifting.kefi.data.model.VideoModel;
import com.gifting.kefi.databinding.LikedFragmentBinding;
import com.gifting.kefi.ui.activities.main.MainActivity;
import com.gifting.kefi.ui.activities.notifications.NotificationsActivity;
import com.gifting.kefi.ui.adapters.LikedArticleAdapter;
import com.gifting.kefi.ui.adapters.LikedDescriptionArticleAdapter;
import com.gifting.kefi.ui.adapters.LikedNaturalArticleAdapter;
import com.gifting.kefi.ui.adapters.LikedProductAdapter;
import com.gifting.kefi.ui.adapters.LikedTipsArticleAdapter;
import com.gifting.kefi.ui.adapters.LikedVideoAdapter;
import com.gifting.kefi.ui.fragments.option_fragment.OptionFragment;

import java.util.ArrayList;

public class LikedFragment extends Fragment implements View.OnClickListener {

    private LikedViewModel mViewModel;
    private LikedFragmentBinding binding;
    private LikedArticleAdapter adapter;
    private LikedVideoAdapter adapter1;
    private LikedProductAdapter adapter2;
    private AppRoomDatabase db;


    public static LikedFragment newInstance() {
        return new LikedFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.liked_fragment, container, false);
        View view = binding.getRoot();


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LikedViewModel.class);
        // TODO: Use the ViewModel

        db = AppRoomDatabase.getDatabase(getContext().getApplicationContext());
        ArrayList<ArticlesModel> articlesModels = new ArrayList<>(db.userDao().getAllFavouriteArticle());
        if (articlesModels.size()==0){
            //binding.noArticle.setVisibility(View.VISIBLE);
        }
       // binding.articleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext(), RecyclerView.HORIZONTAL, false));
        adapter = new LikedArticleAdapter(getContext(),articlesModels);
       // binding.articleRecyclerView.setAdapter(adapter);


        ArrayList<VideoModel> videoModels = new ArrayList<>(db.userDao().getAllFavouriteVideo());
        if (videoModels.size()==0){
          //  binding.noVideoText.setVisibility(View.VISIBLE);
        }
        //binding.videosRecycler.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext(), RecyclerView.HORIZONTAL, false));
        adapter1 = new LikedVideoAdapter(getContext(),videoModels);
        //binding.videosRecycler.setAdapter(adapter1);


        ArrayList<Products> products = new ArrayList<>(db.userDao().getAllFavoriteProduct());
        if (products.size()==0){
            binding.noProductText.setVisibility(View.VISIBLE);
        }
        binding.productsRecycler.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext(), RecyclerView.HORIZONTAL, false));
        adapter2 = new LikedProductAdapter(getContext(),products);
        binding.productsRecycler.setAdapter(adapter2);





        binding.option.setOnClickListener(this);
        binding.notificationLayout.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).getBinding().likedImage.setImageResource(R.drawable.ic_like_active);
        ((MainActivity) getActivity()).getBinding().likedText.setTextColor(getResources().getColor(R.color.bink));
    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity) getActivity()).getBinding().likedImage.setImageResource(R.drawable.ic_like_un_active);
        ((MainActivity) getActivity()).getBinding().likedText.setTextColor(getResources().getColor(R.color.bink_lighter));
    }


    @Override
    public void onClick(View v) {
        if (v == binding.option) {
            OptionFragment optionFragment = OptionFragment.newInstance();
            optionFragment.show(getChildFragmentManager(), "Bottom Sheet Dialog Fragment");
        } else if (v == binding.notificationLayout) {
            startActivity(new Intent(getContext(), NotificationsActivity.class));
        }

    }
}