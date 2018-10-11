package com.linstick.collegeassistant.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.linstick.collegeassistant.adapters.PersonalNoteListNoteAdapter;
import com.linstick.collegeassistant.adapters.listeners.OnPersonalNotePartialClickListener;
import com.linstick.collegeassistant.base.BaseSwipeNoteActivity;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.callbacks.LoadDataCallBack;

import java.util.ArrayList;
import java.util.List;

public class PersonalNotesActivity extends BaseSwipeNoteActivity implements OnPersonalNotePartialClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.mList = new ArrayList<>();
        PersonalNoteListNoteAdapter mAdapter = new PersonalNoteListNoteAdapter(mList);
        mAdapter.setOnPersonalNotePartialClickListener(this);
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
    public void onNoteItemClick(int position) {
        NoteDetailActivity.startAction(PersonalNotesActivity.this, mList.get(position));
    }

    @Override
    public void onItemDeleteClick(final int position) {
        Note note = mList.get(position);
        new AlertDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage("您确定要删除标题为“" + note.getTitle() + "”的帖子吗？")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mList.remove(position);
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(PersonalNotesActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()
                .show();
    }
}
