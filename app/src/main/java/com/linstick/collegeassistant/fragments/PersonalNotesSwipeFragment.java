package com.linstick.collegeassistant.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.linstick.collegeassistant.App;
import com.linstick.collegeassistant.activities.NoteDetailActivity;
import com.linstick.collegeassistant.adapters.PersonalNoteListNoteAdapter;
import com.linstick.collegeassistant.adapters.listeners.OnPersonalNotePartialClickListener;
import com.linstick.collegeassistant.base.BaseSwipeNoteFragment;
import com.linstick.collegeassistant.beans.Collection;
import com.linstick.collegeassistant.beans.Comment;
import com.linstick.collegeassistant.beans.Like;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.beans.Relation;
import com.linstick.collegeassistant.callbacks.SwipeLoadDataCallback;
import com.linstick.collegeassistant.sqlite.NoteDaoUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

public class PersonalNotesSwipeFragment extends BaseSwipeNoteFragment implements OnPersonalNotePartialClickListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new PersonalNoteListNoteAdapter(mList);
        ((PersonalNoteListNoteAdapter) mAdapter).setOnPersonalNotePartialClickListener(this);
    }

    @Override
    public void refreshData(final SwipeLoadDataCallback<Note> callBack) {
        List<Note> result = NoteDaoUtil.findAfterNotesByUserId(App.getUserId(), getFirstItemPublishDate());
        callBack.onRefreshCallback(result);
    }

    @Override
    public void loadMoreData(SwipeLoadDataCallback<Note> callBack) {
        List<Note> result = NoteDaoUtil.findBeforeNotesByUserId(App.getUserId(), getLastItemId(), pageSize);
        callBack.onLoadMoreCallback(result);
    }

    @Override
    public void onLoadMoreCallback(List<Note> result) {
        super.onLoadMoreCallback(result);
        if (result != null && mList.size() == 0) {
            Toast.makeText(getContext(), "很抱歉，没找到相关数据", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPersonalNoteItemClick(int position) {
        NoteDetailActivity.startAction(getContext(), mList.get(position));
    }

    @Override
    public void onItemDeleteClick(final int position) {
        final Note note = mList.get(position);
        new AlertDialog.Builder(getContext())
                .setTitle("温馨提示")
                .setMessage("您确定要删除标题为“" + note.getTitle() + "”的帖子吗？")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 删除相关收藏，相关评论，相关点赞，相关消息,相关帖子
                        DataSupport.deleteAll(Collection.class, "belongNoteId = ?", note.getId() + "");
                        DataSupport.deleteAll(Like.class, "belongNoteId = ?", note.getId() + "");
                        DataSupport.deleteAll(Relation.class, "relatedNoteId = ?", note.getId() + "");
                        DataSupport.deleteAll(Comment.class, "belongNoteId = ?", note.getId() + "");
                        DataSupport.delete(Note.class, note.getId());
                        mList.remove(position);
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
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
