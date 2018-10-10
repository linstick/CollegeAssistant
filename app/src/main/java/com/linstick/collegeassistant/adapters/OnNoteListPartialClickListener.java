package com.linstick.collegeassistant.adapters;

public interface OnNoteListPartialClickListener {
    void onUserInfoClick(int position);

    void onAddCommentClick(int position);

    void onChangeLikeClick(int position);

    void onChangeCollectClick(int position);

    void onNoteItemClick(int position);
}
