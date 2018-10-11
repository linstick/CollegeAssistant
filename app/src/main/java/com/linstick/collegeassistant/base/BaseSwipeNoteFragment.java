package com.linstick.collegeassistant.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.activities.NoteDetailActivity;
import com.linstick.collegeassistant.activities.UserInfoActivity;
import com.linstick.collegeassistant.adapters.NoteListAdapter;
import com.linstick.collegeassistant.adapters.listeners.OnNoteListPartialClickListener;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.callbacks.LoadDataCallBack;
import com.linstick.collegeassistant.events.LoadDataEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseSwipeNoteFragment extends Fragment implements OnNoteListPartialClickListener {

    protected NoteListAdapter mAdapter;
    @BindView(R.id.rv_note_list)
    RecyclerView noteListRv;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;
    private List<Note> mList;
    private LinearLayoutManager mLayoutManager;
    private boolean isLoadingMore;
    private boolean hasMore = true;

    private LoadDataCallBack<Note> refreshCallBack = new LoadDataCallBack<Note>() {
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

    private LoadDataCallBack<Note> loadMoreCallBack = new LoadDataCallBack<Note>() {
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<>();
        mAdapter = new NoteListAdapter(mList);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_note, null);
        ButterKnife.bind(this, view);
        mAdapter.setOnNoteListPartialClickListener(this);
        noteListRv.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getContext());
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
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onUserInfoClick(int position) {
        // 查看用户信息
        UserInfoActivity.startAction(getContext(), mList.get(position).getPublisher());
    }

    @Override
    public void onAddCommentClick(int position) {
        // 跳转到评论页面
        NoteDetailActivity.startActionByAddComment(getContext(), mList.get(position));
    }

    @Override
    public void onChangeLikeClick(int position) {
        // 改变点赞
        Note note = mList.get(position);
        note.setLiked(!note.isLiked());
        int likeCount = note.getLikeCount();
        if (note.isLiked()) {
            note.setLikeCount(likeCount + 1);
        } else {
            note.setLikeCount(likeCount - 1);
        }
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void onNoteItemClick(int position) {
        // 跳转到帖子详情界面
        NoteDetailActivity.startAction(getContext(), mList.get(position));
    }

    @Override
    public void onChangeCollectClick(int position) {
        // 改变收藏
        Note note = mList.get(position);
        note.setCollected(!note.isCollected());
        int collectCount = note.getCollectCount();
        if (note.isCollected()) {
            note.setCollectCount(collectCount + 1);
            Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
        } else {
            note.setCollectCount(collectCount - 1);
        }
        mAdapter.notifyItemChanged(position);
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
                Toast.makeText(getContext(), "暂时无更多新数据了", Toast.LENGTH_SHORT).show();
                break;

            case REFRESH_FAIL:
                Toast.makeText(getContext(), "更新失败，请求检查网络或稍后再试", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "加载失败，请求检查网络或稍后再试", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public abstract String getTitle();

    public abstract void refreshData(LoadDataCallBack callBack);

    public abstract void loadMoreData(LoadDataCallBack callBack);
}
