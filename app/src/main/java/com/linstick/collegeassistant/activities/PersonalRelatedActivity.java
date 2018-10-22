package com.linstick.collegeassistant.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.linstick.collegeassistant.App;
import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.adapters.SwipeRelatedMessageAdapter;
import com.linstick.collegeassistant.adapters.listeners.OnRelatedMessageClickListener;
import com.linstick.collegeassistant.base.BaseActivity;
import com.linstick.collegeassistant.beans.Relation;
import com.linstick.collegeassistant.callbacks.SwipeLoadDataCallback;
import com.linstick.collegeassistant.events.LoadDataEvent;
import com.linstick.collegeassistant.sqlite.RelationDaoUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonalRelatedActivity extends BaseActivity implements
        SwipeLoadDataCallback<Relation>, OnRelatedMessageClickListener {


    private static final String TAG = "PersonalRelatedActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_note_list)
    RecyclerView messageListRv;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    private List<Relation> mList;
    private SwipeRelatedMessageAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private boolean isLoadingMore;
    private boolean hasMore = true;
    private int pageSize = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.base_activity_swipe);
        ButterKnife.bind(this);

        super.toolbar = toolbar;
        super.onCreate(savedInstanceState);

        mList = new ArrayList<>();
        mAdapter = new SwipeRelatedMessageAdapter(mList);
        mAdapter.setOnRelatedMessageClickListener(this);
        messageListRv.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        messageListRv.setLayoutManager(mLayoutManager);
        messageListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = mLayoutManager.findLastVisibleItemPosition();
                if (!isLoadingMore && hasMore && position + 5 > mList.size()) {
                    isLoadingMore = true;
                    loadMoreData(PersonalRelatedActivity.this);
                }
            }
        });

        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData(PersonalRelatedActivity.this);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        if (mList.size() == 0) {
            refreshLayout.setRefreshing(true);
            isLoadingMore = true;
            loadMoreData(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private int getFirstItemId() {
        return (mList == null || mList.size() == 0) ? -1 : mList.get(0).getId();
    }

    private int getLastItemId() {
        return (mList == null || mList.size() == 0) ? Integer.MAX_VALUE : mList.get(mList.size() - 1).getId();
    }

    public void refreshData(final SwipeLoadDataCallback<Relation> callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<Relation> result = RelationDaoUtil.findAfterRelationsByUserId(App.getUserId(), getFirstItemId());
                callBack.onRefreshCallback(result);
            }
        }).start();
    }

    public void loadMoreData(final SwipeLoadDataCallback<Relation> callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<Relation> result = RelationDaoUtil.findBeforeRelationsByUserId(App.getUserId(), getLastItemId(), pageSize);
                callBack.onLoadMoreCallback(result);
            }
        }).start();
    }

    @Override
    public void onRelatedMessageClick(int position) {
        NoteDetailActivity.startAction(PersonalRelatedActivity.this, mList.get(position).getRelatedNote());
    }

    // ============================ 加载数据事件回调 ============================
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadDataEvent(LoadDataEvent event) {
        refreshLayout.setRefreshing(false);
        switch (event) {
            case REFRESH_SUCCESS:
                mAdapter.notifyDataSetChanged();
                messageListRv.smoothScrollToPosition(0);
                break;
            case REFRESH_SUCCESS_EMPTY:
                Toast.makeText(PersonalRelatedActivity.this, "暂时没有新内容喔", Toast.LENGTH_SHORT).show();
                break;
            case REFRESH_FAIL:
                Toast.makeText(PersonalRelatedActivity.this, "更新失败，请求检查网络或稍后再试", Toast.LENGTH_SHORT).show();
                break;
            case LOAD_MORE_SUCCESS:
            case LOAD_MORE_SUCCESS_EMPTY:
                mAdapter.notifyDataSetChanged();
                break;
            case LOAD_MORE_FAIL:
                Toast.makeText(PersonalRelatedActivity.this, "加载失败，请求检查网络或稍后再试", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // =========================================================================

    // 下拉刷新事件回调
    @Override
    public void onRefreshCallback(List<Relation> result) {
        if (result == null) {
            EventBus.getDefault().post(LoadDataEvent.REFRESH_FAIL);
        } else if (result.size() == 0) {
            EventBus.getDefault().post(LoadDataEvent.REFRESH_SUCCESS_EMPTY);
        } else {
            mList.addAll(0, result);
            EventBus.getDefault().post(LoadDataEvent.REFRESH_SUCCESS);
        }
    }

    // 上拉加载更多事件回调
    @Override
    public void onLoadMoreCallback(List<Relation> result) {
        isLoadingMore = false;
        if (result == null) {
            EventBus.getDefault().post(LoadDataEvent.LOAD_MORE_FAIL);
        } else {
            mList.addAll(result);
            EventBus.getDefault().post(LoadDataEvent.LOAD_MORE_SUCCESS);
            if (result.size() < pageSize) {
                hasMore = false;
                mAdapter.setHasMore(hasMore);
                EventBus.getDefault().post(LoadDataEvent.LOAD_MORE_SUCCESS_EMPTY);
            }
        }
    }
}
