package com.linstick.collegeassistant.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.linstick.collegeassistant.adapters.listeners.OnNoteListPartialClickListener;
import com.linstick.collegeassistant.base.BaseSwipeNoteFragment;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.callbacks.LoadDataCallBack;

import java.util.ArrayList;
import java.util.List;

public class AllNotesSwipeNoteFragment extends BaseSwipeNoteFragment implements OnNoteListPartialClickListener {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter.setShowBelongModule(true);
    }

    @Override
    public String getTitle() {
        return "全部";
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
}
