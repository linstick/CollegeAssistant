package com.linstick.collegeassistant.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.linstick.collegeassistant.adapters.CollectionListNoteAdapter;
import com.linstick.collegeassistant.adapters.listeners.OnCollectionClickListener;
import com.linstick.collegeassistant.base.BaseSwipeNoteActivity;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.callbacks.LoadDataCallBack;

import java.util.ArrayList;
import java.util.List;

public class CollectionsActivity extends BaseSwipeNoteActivity implements OnCollectionClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.mList = new ArrayList<>();
        CollectionListNoteAdapter mAdapter = new CollectionListNoteAdapter(mList);
        mAdapter.setOnCollectionClickListener(this);
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
    public void onCollectionClick(int position) {
        NoteDetailActivity.startAction(CollectionsActivity.this, mList.get(position));
    }

    @Override
    public void onCancelCollectClick(int position) {
        mList.remove(position);
        mAdapter.notifyDataSetChanged();
    }
}
