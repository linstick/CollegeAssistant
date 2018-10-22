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

import com.linstick.collegeassistant.App;
import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.activities.NoteDetailActivity;
import com.linstick.collegeassistant.activities.UserInfoActivity;
import com.linstick.collegeassistant.adapters.NoteListAdapter;
import com.linstick.collegeassistant.adapters.listeners.OnNoteListPartialClickListener;
import com.linstick.collegeassistant.beans.Collection;
import com.linstick.collegeassistant.beans.Like;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.beans.Relation;
import com.linstick.collegeassistant.beans.User;
import com.linstick.collegeassistant.callbacks.SwipeLoadDataCallback;
import com.linstick.collegeassistant.events.LoadDataEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseSwipeNoteFragment extends Fragment implements
        OnNoteListPartialClickListener, SwipeLoadDataCallback<Note> {
    private static final String TAG = "BaseSwipeNoteFragment";
    protected List<Note> mList;
    protected BaseSwipeNoteAdapter mAdapter;
    protected User mUser;
    protected int pageSize = 20;
    @BindView(R.id.rv_note_list)
    RecyclerView noteListRv;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;
    private LinearLayoutManager mLayoutManager;
    private boolean isLoadingMore;
    private boolean hasMore = true;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<>();
        mAdapter = new NoteListAdapter(mList);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_fragment_note, null);
        ButterKnife.bind(this, view);
        if (mAdapter instanceof NoteListAdapter) {
            ((NoteListAdapter) mAdapter).setOnNoteListPartialClickListener(this);
        }
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
                if (!isLoadingMore && hasMore && position + 5 > mList.size()) {
                    isLoadingMore = true;
                    loadMoreData(BaseSwipeNoteFragment.this);
                }
            }
        });

        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData(BaseSwipeNoteFragment.this);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        // 登录状态改变或者初次加载
        if (App.getUser() != mUser || mList.size() == 0) {
            reLoadData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    // -------------------正常帖子列表点击事件回调------------------------------------
    @Override
    public void onUserInfoClick(int position) {
        // 查看用户信息
        UserInfoActivity.startAction(getContext(), mList.get(position).getPublisher());
    }

    @Override
    public void onAddCommentClick(int position) {
        // 跳转到评论页面
        if (App.getUser() == null) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        NoteDetailActivity.startActionByAddComment(getContext(), mList.get(position));
    }

    @Override
    public void onChangeLikeClick(int position) {
        // 改变点赞
        if (App.getUser() == null) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        Note note = mList.get(position);
        note.setLiked(!note.isLiked());
        int likeCount = note.getLikeCount();
        if (note.isLiked()) {
            // 添加点赞记录
            note.setLikeCount(likeCount + 1);
            new Like(App.getUserId(), note.getId()).save();
            // 添加点赞消息
            new Relation(App.getUserId(), note.getPublisherId(), note.getId(), Relation.TYPE_LIKE).save();
        } else {
            note.setLikeCount(likeCount - 1);
            DataSupport.deleteAll(Like.class, "likerId = ? and belongNoteId = ?", App.getUserId() + "", note.getId() + "");
        }

        NoteListAdapter.OrdinaryViewHolder viewHolder = (NoteListAdapter.OrdinaryViewHolder) noteListRv.findViewHolderForAdapterPosition(position);
        viewHolder.likeIv.setImageResource(note.isLiked() ? R.drawable.ic_like_orange : R.drawable.ic_like_gray);
        viewHolder.likeCountTv.setText(note.getLikeCount() + "");
    }

    @Override
    public void onNoteItemClick(int position) {
        // 跳转到帖子详情界面

        NoteDetailActivity.startAction(getContext(), mList.get(position));
    }

    @Override
    public void onChangeCollectClick(int position) {
        // 改变收藏
        if (App.getUser() == null) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        Note note = mList.get(position);
        note.setCollected(!note.isCollected());
        int collectCount = note.getCollectCount();
        if (note.isCollected()) {
            // 添加收藏关系记录
            note.setCollectCount(collectCount + 1);
            new Collection(App.getUserId(), note.getId()).save();
            // 添加收藏消息
            new Relation(App.getUserId(), note.getPublisherId(), note.getId(), Relation.TYPE_COLLECT).save();
            // 提示用户
            Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
        } else {
            note.setCollectCount(collectCount - 1);
            DataSupport.deleteAll(Collection.class, "collectorId = ? and belongNoteId = ?", App.getUserId() + "", note.getId() + "");
        }

        NoteListAdapter.OrdinaryViewHolder viewHolder = (NoteListAdapter.OrdinaryViewHolder) noteListRv.findViewHolderForAdapterPosition(position);
        viewHolder.collectIv.setImageResource(note.isCollected() ? R.drawable.ic_star_orange : R.drawable.ic_star_gray);
        viewHolder.collectCountTv.setText(note.getCollectCount() + "");
    }

    // =========================================================================

    // ============================ 加载数据事件回调 ============================
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadDataEvent(LoadDataEvent event) {
        refreshLayout.setRefreshing(false);
        switch (event) {
            case REFRESH_SUCCESS:
                mAdapter.notifyDataSetChanged();
                noteListRv.smoothScrollToPosition(0);
                break;
            case REFRESH_SUCCESS_EMPTY:
                Toast.makeText(getContext(), "暂时没有新内容喔", Toast.LENGTH_SHORT).show();
                break;
            case REFRESH_FAIL:
                Toast.makeText(getContext(), "更新失败，请求检查网络或稍后再试", Toast.LENGTH_SHORT).show();
                break;
            case LOAD_MORE_SUCCESS:
            case LOAD_MORE_SUCCESS_EMPTY:
                mAdapter.notifyDataSetChanged();
                break;
            case LOAD_MORE_FAIL:
                Toast.makeText(getContext(), "加载失败，请求检查网络或稍后再试", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // =========================================================================

    // 下拉刷新事件回调
    @Override
    public void onRefreshCallback(List<Note> result) {
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
    public void onLoadMoreCallback(List<Note> result) {
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

    public void reLoadData() {
        mList.clear();
        mAdapter.notifyDataSetChanged();
        mUser = App.getUser();
        refreshLayout.setRefreshing(true);
        isLoadingMore = true;
        loadMoreData(this);
    }


    protected int getFirstItemPublishDate() {
        return (mList != null && mList.size() > 0) ? mList.get(0).getId() : -1;
    }

    protected int getLastItemId() {
        return (mList != null && mList.size() > 0) ? mList.get(mList.size() - 1).getId() : Integer.MAX_VALUE;
    }

    public abstract void refreshData(SwipeLoadDataCallback<Note> callBack);

    public abstract void loadMoreData(SwipeLoadDataCallback<Note> callBack);
}
