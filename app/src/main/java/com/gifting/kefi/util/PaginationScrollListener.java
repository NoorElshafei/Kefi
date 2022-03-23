package com.gifting.kefi.util;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayoutManager;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {
    FlexboxLayoutManager layoutManager;
    LinearLayoutManager layoutManager1;

    public PaginationScrollListener(FlexboxLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public PaginationScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager1 = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItemCount;
        int totalItemCount;
        int firstVisibleItemPosition;
        if (layoutManager != null) {
            visibleItemCount = layoutManager.getChildCount();
            totalItemCount = layoutManager.getItemCount();
            firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        } else {
            visibleItemCount = layoutManager1.getChildCount();
            totalItemCount = layoutManager1.getItemCount();
            firstVisibleItemPosition = layoutManager1.findFirstVisibleItemPosition();
        }

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                loadMoreItems();
            }
        }
    }

    protected abstract void loadMoreItems();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();
}
