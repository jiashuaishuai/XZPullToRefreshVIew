package com.example.shuaijia.xzpulltorefreshview.recycleutils;

/**
 * Created by JiaShuai on 2018/6/7.
 */

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shuaijia.xzpulltorefreshview.R;

import butterknife.ButterKnife;

/**
 * recyclerview 加载更多
 */
public class RecycleViewLoadUtils {

    private final RecyclerView.OnScrollListener onScrollListener;

    public interface RecyclerCallBack {
        void doLoadMore();
    }

    private int lastVisibleItemPosition;
    private boolean isLoadingMore = false;

    RecyclerView mRecyclerView;
    RecyclerCallBack mCallBack;

    View mLoadMoreView;
    TextView tv_loadmore_retry;
    ProgressBar loadmore_progress;

    /**
     * @param callBack       加载更多回调
     * @param recyclerView   recyclerview
     * @param adapter        recyclerview.adapter
     * @param isAutoLoadMore 是否自动加载（第一页如果没有满屏，true可以自动加载）
     */
    public RecycleViewLoadUtils(RecyclerCallBack callBack, RecyclerView recyclerView, final RecyclerView.Adapter adapter, final boolean isAutoLoadMore) {
        this.mCallBack = callBack;
        this.mRecyclerView = recyclerView;
        mLoadMoreView = LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.recycler_load_layout, mRecyclerView, false);
        mLoadMoreView.setVisibility(View.GONE);
        tv_loadmore_retry = ButterKnife.findById(mLoadMoreView, R.id.foot_tv);
        loadmore_progress = ButterKnife.findById(mLoadMoreView, R.id.progressBar);
        HeaderViewRecyclerAdapter headerViewRecyclerAdapter;
        if (adapter instanceof HeaderViewRecyclerAdapter) {
            headerViewRecyclerAdapter = (HeaderViewRecyclerAdapter) adapter;
        } else {
            headerViewRecyclerAdapter = new HeaderViewRecyclerAdapter(adapter);
            headerViewRecyclerAdapter.addFooterView(mLoadMoreView);
        }

        onScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int count = adapter.getItemCount();
                if (count > 0 && (visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE &&
                        (lastVisibleItemPosition) >= totalItemCount - 1) && !isLoadingMore) {
                    isLoadingMore = true;
                    loadmore_progress.setVisibility(View.VISIBLE);
                    tv_loadmore_retry.setText("正在加载");
                    mLoadMoreView.setVisibility(View.VISIBLE);
                    mCallBack.doLoadMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = ((LinearLayoutManager) (recyclerView.getLayoutManager())).findLastVisibleItemPosition();//最后一个可见的条目
                int count = adapter.getItemCount();
                if (!isLoadingMore && isAutoLoadMore && count > 0 && lastVisibleItemPosition >= count - 1) {
                    isLoadingMore = true;
                    loadmore_progress.setVisibility(View.VISIBLE);
                    tv_loadmore_retry.setText("正在加载");
                    mLoadMoreView.setVisibility(View.VISIBLE);
                    mCallBack.doLoadMore();
                }
            }
        };


        mRecyclerView.setAdapter(headerViewRecyclerAdapter);
        mRecyclerView.addOnScrollListener(onScrollListener);
    }


    /**
     * 在成功的action里面调用
     *
     * @param isLoadCompleted 是否全部加载完成 可以通过list.size < pageSize来获取此参数
     */
    public void onLoadComplete(boolean isLoadCompleted) {
        isLoadingMore = isLoadCompleted;
        if (isLoadCompleted) {
            mLoadMoreView.setVisibility(View.VISIBLE);
            tv_loadmore_retry.setText("暂无更多数据");
            tv_loadmore_retry.setVisibility(View.VISIBLE);
            loadmore_progress.setVisibility(View.INVISIBLE);

        } else {
            mLoadMoreView.setVisibility(View.GONE);
        }
    }

    /**
     * 加载失败，在失败的action里面调用
     */
    public void onLoadMoreFailed(String s) {
        isLoadingMore = false;
        loadmore_progress.setVisibility(View.INVISIBLE);
        tv_loadmore_retry.setText(s);
        tv_loadmore_retry.setVisibility(View.VISIBLE);
    }


    public void unrigister() {
        if (mRecyclerView != null) {
            mRecyclerView.removeOnScrollListener(onScrollListener);
        }
    }

//    public RecyclerViewUtil(final RecyclerCallBack mCallBack, RecyclerView mRecyclerView, RecyclerView.Adapter adapter, boolean isLoadMore,FullyLinearLayoutManager fullyLinearLayoutManager) {
//        this.mCallBack = mCallBack;
//        this.mRecyclerView = mRecyclerView;
//        mRecyclerView.setLayoutManager(fullyLinearLayoutManager);
//        mRecyclerView.setAdapter(adapter);
//        if (isLoadMore) {
//            mLoadMoreView = LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.progress_loadmore, mRecyclerView, false);
//            tv_loadmore_retry = ButterKnife.findById(mLoadMoreView, R.id.loadmore_tv_loadmore);
//            loadmore_progress = ButterKnife.findById(mLoadMoreView, R.id.loadmore_progress);
//            tv_loadmore_retry.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mCallBack.doLoadMore();
//                }
//            });
//            HeaderViewRecyclerAdapter headerViewRecyclerAdapter;
//            if(adapter instanceof HeaderViewRecyclerAdapter){
//                headerViewRecyclerAdapter = (HeaderViewRecyclerAdapter) adapter;
//            }else{
//                headerViewRecyclerAdapter = new HeaderViewRecyclerAdapter(adapter);
//                headerViewRecyclerAdapter.addFooterView(mLoadMoreView);
//            }
//            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                    super.onScrollStateChanged(recyclerView, newState);
//                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//                    int visibleItemCount = layoutManager.getChildCount();
//                    int totalItemCount = layoutManager.getItemCount();
//                    if ((visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE &&
//                            (lastVisibleItemPosition) >= totalItemCount - 1) && !isLoadingMore) {
//                        isLoadingMore = true;
//                        tv_loadmore_retry.setVisibility(View.INVISIBLE);
//                        mLoadMoreView.setVisibility(View.VISIBLE);
//                        loadmore_progress.setVisibility(View.VISIBLE);
//                        mCallBack.doLoadMore();
//                    }
//                }
//
//                @Override
//                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//                    lastVisibleItemPosition = ((LinearLayoutManager) (recyclerView.getLayoutManager())).findLastVisibleItemPosition();
//                }
//            });
//        }
//    }
}

