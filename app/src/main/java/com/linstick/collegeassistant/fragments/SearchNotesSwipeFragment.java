package com.linstick.collegeassistant.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.linstick.collegeassistant.App;
import com.linstick.collegeassistant.activities.SearchActivity;
import com.linstick.collegeassistant.base.BaseSwipeNoteFragment;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.callbacks.SwipeLoadDataCallback;
import com.linstick.collegeassistant.sqlite.NoteDaoUtil;

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
        List<Note> result = NoteDaoUtil.findAfterNotesByKeyword(App.getUserId(), getFirstItemId(), keyword);
        callBack.onRefreshCallback(result);
    }

    @Override
    public void loadMoreData(SwipeLoadDataCallback<Note> callBack) {
        List<Note> result = NoteDaoUtil.findBeforeNotesByKeyword(App.getUserId(), getLastItemId(), pageSize, keyword);
        callBack.onLoadMoreCallback(result);
    }

    @Override
    public void onLoadMoreCallback(List<Note> result) {
        super.onLoadMoreCallback(result);
        if (result != null && mList.size() == 0) {
            Toast.makeText(getContext(), "很抱歉，没找到相关数据", Toast.LENGTH_SHORT).show();
        }
    }
}
