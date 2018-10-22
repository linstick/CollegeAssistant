package com.linstick.collegeassistant.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.linstick.collegeassistant.App;
import com.linstick.collegeassistant.base.BaseSwipeNoteFragment;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.callbacks.SwipeLoadDataCallback;
import com.linstick.collegeassistant.sqlite.NoteDaoUtil;

import java.util.List;

public class ModuleSwipeNoteFragment extends BaseSwipeNoteFragment {

    public final static String MODULE_ID = "MODULE_ID";

    private int moduleId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            moduleId = bundle.getInt(MODULE_ID);
        }
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
                List<Note> result;
                if (moduleId == 0) {
                    result = NoteDaoUtil.findAfterNotes(App.getUserId(), getFirstItemPublishDate());
                } else {
                    result = NoteDaoUtil.findAfterNotesByModuleId(App.getUserId(), getFirstItemPublishDate(), moduleId);
                }
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
                List<Note> result;
                if (moduleId == 0) {
                    result = NoteDaoUtil.findBeforeNotes(App.getUserId(), getLastItemId(), pageSize);
                } else {
                    result = NoteDaoUtil.findBeforeNotesByModuleId(App.getUserId(), getLastItemId(), pageSize, moduleId);
                }
                callBack.onLoadMoreCallback(result);
            }
        }).start();
    }
}
