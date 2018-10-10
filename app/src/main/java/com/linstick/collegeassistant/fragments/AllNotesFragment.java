package com.linstick.collegeassistant.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.activities.NoteDetailActivity;
import com.linstick.collegeassistant.activities.UserInfoActivity;
import com.linstick.collegeassistant.adapters.NoteListAdapter;
import com.linstick.collegeassistant.adapters.OnNoteListPartialClickListener;
import com.linstick.collegeassistant.base.BaseFragment;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.events.LoadDataEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllNotesFragment extends BaseFragment implements OnNoteListPartialClickListener {

    private static final String TAG = "AllNotesFragment";

    @BindView(R.id.rv_note_list)
    RecyclerView noteListRv;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    private List<Note> mList = new ArrayList<>();
    private NoteListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private boolean isLoadingMore;
    private boolean hasMore = true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_note, null);
        ButterKnife.bind(this, view);
        mAdapter = new NoteListAdapter(mList);
        mAdapter.setOnNoteListPartialClickListener(this);
        mLayoutManager = new LinearLayoutManager(getContext());
        noteListRv.setAdapter(mAdapter);
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
                    loadMore();
                }
            }
        });

        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        if (mList.size() == 0) {
            refresh();
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void refresh() {
        refreshLayout.setRefreshing(true);
        new Thread(new RefreshTask()).start();
    }

    private void loadMore() {
        isLoadingMore = true;
        new Thread(new LoadMoreTask()).start();
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
        Toast.makeText(getActivity(), "点赞" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoteItemClick(int position) {
        // 跳转到帖子详情界面
        NoteDetailActivity.startAction(getContext(), mList.get(position));
    }

    @Override
    public void onChangeCollectClick(int position) {
        // 改变收藏
        Toast.makeText(getActivity(), "收藏" + position, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStopRefresh(LoadDataEvent event) {
        switch (event) {
            case REFRESH_SUCCESS:
                refreshLayout.setRefreshing(false);
                mAdapter.notifyDataSetChanged();
                noteListRv.smoothScrollToPosition(0);
                break;

            case REFRESH_SUCCESS_EMPTY:
                refreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), "暂时无更多新数据了", Toast.LENGTH_SHORT).show();
                break;

            case REFRESH_FAIL:
                refreshLayout.setRefreshing(false);
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

    @Override
    public String getTitle() {
        return "全部";
    }

    class RefreshTask implements Runnable {
        @Override
        public void run() {
            Log.d(TAG, "run: refreshing");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // ----使用Gson解析数据----
            List<Note> result = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                result.add(new Note());
            }
            // -----------------------
            if (result.size() > 0) {
                mList.addAll(0, result);
                EventBus.getDefault().post(LoadDataEvent.REFRESH_SUCCESS);
            } else {
                EventBus.getDefault().post(LoadDataEvent.REFRESH_SUCCESS_EMPTY);
            }
        }
    }

    class LoadMoreTask implements Runnable {
        @Override
        public void run() {
            Log.d(TAG, "run: loading more");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // ----使用Gson解析数据----
            List<Note> result = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                result.add(new Note());
            }
            // ----------------------
            if (result.size() > 0) {
                mList.addAll(result);
                EventBus.getDefault().post(LoadDataEvent.LOAD_MORE_SUCCESS);
            } else {
                EventBus.getDefault().post(LoadDataEvent.LOAD_MORE_SUCCESS_EMPTY);
            }
        }
    }
}
