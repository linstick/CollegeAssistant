package com.linstick.collegeassistant.base;

import com.linstick.collegeassistant.beans.Note;

import java.util.List;

public abstract class BaseSwipeNoteAdapter extends BaseSwipeAdapter {

    protected List<Note> mList;

    public BaseSwipeNoteAdapter(List<Note> mList) {
        this.mList = mList;
    }

    @Override
    public int getItemCount() {
        return mList.size() > 0 ? mList.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position < mList.size() ? TYPE_ORDINARY_ITEM : TYPE_FOOTER_ITEM;
    }

}
