package com.linstick.collegeassistant.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.linstick.collegeassistant.App;
import com.linstick.collegeassistant.activities.SearchActivity;
import com.linstick.collegeassistant.base.BaseSwipeNoteFragment;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.callbacks.SwipeLoadDataCallback;
import com.linstick.collegeassistant.events.LoadDataEvent;
import com.linstick.collegeassistant.sqlite.NoteDaoUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class SearchNotesSwipeFragment extends BaseSwipeNoteFragment {

    private String keyword = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keyword = getArguments().getString(SearchActivity.KEYWORD_TAG);
    }

    @Override
    public void refreshData(final SwipeLoadDataCallback<Note> callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<Note> result = NoteDaoUtil.findAfterNotesByKeyword(App.getUserId(), getFirstItemPublishDate(), keyword);
                callBack.onRefreshCallback(result);
            }
        }).start();

    }

    @Override
    public void loadMoreData(final SwipeLoadDataCallback<Note> callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<Note> result = NoteDaoUtil.findBeforeNotesByKeyword(App.getUserId(), getLastItemId(), pageSize, keyword);
                callBack.onLoadMoreCallback(result);
            }
        }).start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadDataEvent(LoadDataEvent event) {
        super.onLoadDataEvent(event);
        if (mList == null || mList.size() == 0) {
            Toast.makeText(getContext(), "抱歉，找不到包含关键字的帖子。", Toast.LENGTH_LONG).show();
        }
    }
}
