package com.linstick.collegeassistant.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.linstick.collegeassistant.App;
import com.linstick.collegeassistant.activities.NoteDetailActivity;
import com.linstick.collegeassistant.adapters.CollectedNotesAdapter;
import com.linstick.collegeassistant.adapters.listeners.OnCollectionClickListener;
import com.linstick.collegeassistant.base.BaseSwipeNoteFragment;
import com.linstick.collegeassistant.beans.Collection;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.callbacks.SwipeLoadDataCallback;
import com.linstick.collegeassistant.events.LoadDataEvent;
import com.linstick.collegeassistant.sqlite.NoteDaoUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

public class CollectedNotesSwipeFragment extends BaseSwipeNoteFragment implements OnCollectionClickListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CollectedNotesAdapter(mList);
        ((CollectedNotesAdapter) mAdapter).setOnCollectionClickListener(this);
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
                List<Note> result = NoteDaoUtil.findAfterNotesByCollectorId(App.getUserId(), getFirstItemPublishDate());
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
                List<Note> result = NoteDaoUtil.findBeforeNotesByCollectorId(App.getUserId(), getLastItemId(), pageSize);
                callBack.onLoadMoreCallback(result);
            }
        }).start();

    }

    @Override
    public void onLoadDataEvent(LoadDataEvent event) {
        super.onLoadDataEvent(event);
        if (mList == null || mList.size() == 0) {
            Toast.makeText(getContext(), "很抱歉，没找到相关数据", Toast.LENGTH_SHORT).show();
        }
    }

    // ======================= 我的收藏 列表点击事件回调 =========================
    @Override
    public void onCollectedItemClick(int position) {
        NoteDetailActivity.startAction(getContext(), mList.get(position));
    }

    @Override
    public void onCancelCollectClick(int position) {
        DataSupport.deleteAll(Collection.class, "collectorId = ? and belongNoteId = ?", App.getUserId() + "", mList.get(position).getId() + "");
        mList.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    // =========================================================================
}
