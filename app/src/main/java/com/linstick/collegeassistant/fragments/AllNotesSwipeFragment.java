package com.linstick.collegeassistant.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.linstick.collegeassistant.App;
import com.linstick.collegeassistant.adapters.listeners.OnNoteListPartialClickListener;
import com.linstick.collegeassistant.base.BaseSwipeNoteFragment;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.callbacks.SwipeLoadDataCallback;
import com.linstick.collegeassistant.sqlite.NoteDaoUtil;

import java.util.List;

public class AllNotesSwipeFragment extends BaseSwipeNoteFragment implements OnNoteListPartialClickListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public String getTitle() {
        return "全部";
    }

    @Override
    public int getModuleId() {
        return -1;
    }

    @Override
    public void refreshData(final SwipeLoadDataCallback<Note> callBack) {
        List<Note> result = NoteDaoUtil.findAfterNotes(App.getUserId(), getFirstItemId());
        callBack.onRefreshCallback(result);
    }

    @Override
    public void loadMoreData(SwipeLoadDataCallback<Note> callBack) {
        List<Note> result = NoteDaoUtil.findBeforeNotes(App.getUserId(), getLastItemId(), pageSize);
        callBack.onLoadMoreCallback(result);
    }
}
