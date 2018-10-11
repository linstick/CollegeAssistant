package com.linstick.collegeassistant.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.callbacks.LoadDataCallBack;
import com.linstick.collegeassistant.events.LoadDataEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseSwipeNoteActivity extends BaseActivity {
    private static final String TAG = "BaseSwipeNoteActivity";
    protected List<Note> mList;
    protected BaseSwipeNoteAdapter mAdapter;
    protected LoadDataCallBack<Note> refreshCallBack = new LoadDataCallBack<Note>() {
        @Override
        public void onSuccess(List<Note> list) {
            mList.addAll(0, list);
            EventBus.getDefault().post(LoadDataEvent.REFRESH_SUCCESS);
        }

        @Override
        public void onSuccessEmpty() {
            EventBus.getDefault().post(LoadDataEvent.REFRESH_SUCCESS);
        }

        @Override
        public void onFail(String error) {
            EventBus.getDefault().post(LoadDataEvent.REFRESH_FAIL);
        }
    };
    protected LoadDataCallBack<Note> loadMoreCallBack = new LoadDataCallBack<Note>() {
        @Override
        public void onSuccess(List<Note> list) {
            mList.addAll(list);
            EventBus.getDefault().post(LoadDataEvent.LOAD_MORE_SUCCESS);
        }

        @Override
        public void onSuccessEmpty() {
            EventBus.getDefault().post(LoadDataEvent.LOAD_MORE_SUCCESS_EMPTY);
        }

        @Override
        public void onFail(String error) {
            EventBus.getDefault().post(LoadDataEvent.LOAD_MORE_FAIL);
        }
    };
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_note_list)
    RecyclerView noteListRv;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;
    private LinearLayoutManager mLayoutManager;
    private boolean isLoadingMore;
    private boolean hasMore = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_base_swipe);
        ButterKnife.bind(this);

        super.toolbar = toolbar;
        super.onCreate(savedInstanceState);

        noteListRv.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        noteListRv.setLayoutManager(mLayoutManager);
        noteListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = mLayoutManager.findLastVisibleItemPosition();
                if (!isLoadingMore && hasMore && position + 3 > mList.size()) {
                    isLoadingMore = true;
                    loadMoreData(loadMoreCallBack);
                }
            }
        });

        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData(refreshCallBack);
            }
        });
        if (mList.size() == 0) {
            refreshLayout.setRefreshing(true);
            isLoadingMore = true;
            loadMoreData(loadMoreCallBack);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStopLoadData(LoadDataEvent event) {
        refreshLayout.setRefreshing(false);
        switch (event) {
            case REFRESH_SUCCESS:
                mAdapter.notifyDataSetChanged();
                noteListRv.smoothScrollToPosition(0);
                break;

            case REFRESH_SUCCESS_EMPTY:
                Toast.makeText(this, "暂时无更多新数据了", Toast.LENGTH_SHORT).show();
                break;

            case REFRESH_FAIL:
                Toast.makeText(this, "更新失败，请求检查网络或稍后再试", Toast.LENGTH_SHORT).show();
                break;

            case LOAD_MORE_SUCCESS:
                isLoadingMore = false;
                mAdapter.notifyDataSetChanged();
                break;
            case LOAD_MORE_SUCCESS_EMPTY:
                isLoadingMore = false;
                hasMore = false;
                mAdapter.setHasMore(hasMore);
                mAdapter.notifyDataSetChanged();
                break;
            case LOAD_MORE_FAIL:
                isLoadingMore = false;
                Toast.makeText(this, "加载失败，请求检查网络或稍后再试", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public abstract void refreshData(LoadDataCallBack callBack);

    public abstract void loadMoreData(LoadDataCallBack callBack);
}
