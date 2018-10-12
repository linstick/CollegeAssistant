package com.linstick.collegeassistant.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.linstick.collegeassistant.App;
import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.adapters.NoteListAdapter;
import com.linstick.collegeassistant.adapters.listeners.OnNoteListPartialClickListener;
import com.linstick.collegeassistant.base.BaseSwipeNoteActivity;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.callbacks.LoadDataCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchActivity extends BaseSwipeNoteActivity implements OnNoteListPartialClickListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.mList = new ArrayList<>();
        NoteListAdapter mAdapter = new NoteListAdapter(mList);
        mAdapter.setOnNoteListPartialClickListener(this);
        mAdapter.setShowBelongModule(true);
        super.mAdapter = mAdapter;
        super.onCreate(savedInstanceState);
    }

    @Override
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
                List<Note> result = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    result.add(new Note());
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
                List<Note> result = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    result.add(new Note());
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                SearchActivity.this.finish();
                break;
        }
        return true;
    }

    @Override
    public void onUserInfoClick(int position) {
        // 查看用户信息
        UserInfoActivity.startAction(SearchActivity.this, mList.get(position).getPublisher());
    }

    @Override
    public void onAddCommentClick(int position) {
        // 跳转到评论页面
        if (App.getUser() == null) {
            Toast.makeText(SearchActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        NoteDetailActivity.startActionByAddComment(SearchActivity.this, mList.get(position));
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
        NoteListAdapter.OrdinaryViewHolder viewHolder = (NoteListAdapter.OrdinaryViewHolder) noteListRv.findViewHolderForAdapterPosition(position);
        viewHolder.likeIv.setImageResource(note.isLiked() ? R.drawable.ic_like_orange : R.drawable.ic_like_gray);
        viewHolder.likeCountTv.setText(note.getLikeCount() + "");
    }

    @Override
    public void onNoteItemClick(int position) {
        // 跳转到帖子详情界面
        if (App.getUser() == null) {
            Toast.makeText(SearchActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        NoteDetailActivity.startAction(SearchActivity.this, mList.get(position));
    }

    @Override
    public void onChangeCollectClick(int position) {
        // 改变收藏
        if (App.getUser() == null) {
            Toast.makeText(SearchActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        Note note = mList.get(position);
        note.setCollected(!note.isCollected());
        int collectCount = note.getCollectCount();
        if (note.isCollected()) {
            note.setCollectCount(collectCount + 1);
            Toast.makeText(SearchActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
        } else {
            note.setCollectCount(collectCount - 1);
        }
        NoteListAdapter.OrdinaryViewHolder viewHolder = (NoteListAdapter.OrdinaryViewHolder) noteListRv.findViewHolderForAdapterPosition(position);
        viewHolder.collectIv.setImageResource(note.isCollected() ? R.drawable.ic_star_orange : R.drawable.ic_star_gray);
        viewHolder.collectCountTv.setText(note.getCollectCount() + "");
    }
}
