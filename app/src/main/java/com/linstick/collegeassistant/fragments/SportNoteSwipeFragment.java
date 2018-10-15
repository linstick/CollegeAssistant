package com.linstick.collegeassistant.fragments;

import com.linstick.collegeassistant.App;
import com.linstick.collegeassistant.base.BaseSwipeNoteFragment;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.callbacks.SwipeLoadDataCallback;
import com.linstick.collegeassistant.sqlite.NoteDaoUtil;

import java.util.List;

public class SportNoteSwipeFragment extends BaseSwipeNoteFragment {

    @Override
    public String getTitle() {
        return "体育";
    }

    @Override
    public int getModuleId() {
        return 4;
    }

    @Override
    public void refreshData(final SwipeLoadDataCallback<Note> callBack) {
        List<Note> result = NoteDaoUtil.findAfterNotesByModuleId(App.getUserId(), getFirstItemPublishDate(), getModuleId());
        callBack.onRefreshCallback(result);
    }

    @Override
    public void loadMoreData(SwipeLoadDataCallback<Note> callBack) {
        List<Note> result = NoteDaoUtil.findBeforeNotesByModuleId(App.getUserId(), getLastItemId(), pageSize, getModuleId());
        callBack.onLoadMoreCallback(result);
    }
}
