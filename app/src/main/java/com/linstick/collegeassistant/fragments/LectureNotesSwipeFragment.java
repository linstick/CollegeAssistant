package com.linstick.collegeassistant.fragments;

import com.linstick.collegeassistant.App;
import com.linstick.collegeassistant.base.BaseSwipeNoteFragment;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.callbacks.SwipeLoadDataCallback;
import com.linstick.collegeassistant.sqlite.NoteDaoUtil;

import java.util.List;

public class LectureNotesSwipeFragment extends BaseSwipeNoteFragment {

    @Override
    public String getTitle() {
        return "讲座";
    }

    @Override
    public int getModuleId() {
        return 3;
    }

    @Override
    public void refreshData(final SwipeLoadDataCallback<Note> callBack) {
        List<Note> result = NoteDaoUtil.findAfterNotesByModuleId(App.getUserId(), getFirstItemId(), getModuleId());
        callBack.onRefreshCallback(result);
    }

    @Override
    public void loadMoreData(SwipeLoadDataCallback<Note> callBack) {
        List<Note> result = NoteDaoUtil.findBeforeNotesByModuleId(App.getUserId(), getLastItemId(), pageSize, getModuleId());
        callBack.onLoadMoreCallback(result);
    }
}
