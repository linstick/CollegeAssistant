package com.linstick.collegeassistant.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.adapters.SwipeRelatedMessageAdapter;
import com.linstick.collegeassistant.adapters.listeners.OnRelatedMessageClickListener;
import com.linstick.collegeassistant.base.BaseActivity;
import com.linstick.collegeassistant.beans.Relation;
import com.linstick.collegeassistant.callbacks.LoadDataCallBack;
import com.linstick.collegeassistant.events.LoadDataEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonalRelatedActivity extends BaseActivity implements OnRelatedMessageClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_note_list)
    RecyclerView messageListRv;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    private List<Relation> mList;
    protected LoadDataCallBack<Relation> refreshCallBack = new LoadDataCallBack<Relation>() {
        @Override
        public void onSuccess(List<Relation> list) {
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
    protected LoadDataCallBack<Relation> loadMoreCallBack = new LoadDataCallBack<Relation>() {
        @Override
        public void onSuccess(List<Relation> list) {
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
    private SwipeRelatedMessageAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private boolean isLoadingMore;
    private boolean hasMore = true;

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
                messageListRv.smoothScrollToPosition(0);
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

    public void refreshData(final LoadDataCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 模拟耗时操作
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 获取数据
                List<Relation> result = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    result.add(new Relation());
                }
                // 对数据结果继续判断
                if (result == null) {
                    callBack.onFail("加载失败");
                } else if (result.size() == 0) {
                    callBack.onSuccessEmpty();
                } else {
                    callBack.onSuccess(result);
                }
            }
        }).start();
    }

    public void loadMoreData(final LoadDataCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 模拟耗时操作
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 获取数据
                List<Relation> result = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    result.add(new Relation());
                }
                // 对数据结果继续判断
                if (result == null) {
                    callBack.onFail("加载失败");
                } else if (result.size() == 0) {
                    callBack.onSuccessEmpty();
                } else {
                    callBack.onSuccess(result);
                }
            }
        }).start();
    }

    @Override
    public void onRelatedMessageClick(int position) {
        NoteDetailActivity.startAction(PersonalRelatedActivity.this, mList.get(position).getRelatedNote());
    }
}
