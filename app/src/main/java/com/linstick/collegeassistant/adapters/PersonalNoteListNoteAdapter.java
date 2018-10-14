package com.linstick.collegeassistant.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.adapters.listeners.OnPersonalNotePartialClickListener;
import com.linstick.collegeassistant.base.BaseSwipeNoteAdapter;
import com.linstick.collegeassistant.beans.Module;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.utils.TimeFactoryUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalNoteListNoteAdapter extends BaseSwipeNoteAdapter {


    private OnPersonalNotePartialClickListener mListener;

    public PersonalNoteListNoteAdapter(List<Note> list) {
        super(list);
    }

    public void setOnPersonalNotePartialClickListener(OnPersonalNotePartialClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == BaseSwipeNoteAdapter.TYPE_ORDINARY_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personal_note_list, parent, false);
            return new OrdinaryViewHolder(view);
        }
        return super.onCreateFooterViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrdinaryViewHolder) {
            Note note = mList.get(position);
            OrdinaryViewHolder viewHolder = (OrdinaryViewHolder) holder;
            viewHolder.noteTitleTv.setText(note.getTitle());
            viewHolder.noteContentTv.setText(note.getContent());
            viewHolder.publishTimeTv.setText(TimeFactoryUtil.dateToStringFormat(note.getPublishTime()));
            viewHolder.belongModuleTv.setText(note.getBelongModule().getName());
            viewHolder.collectCountTv.setText(note.getCollectCount() + "");
            viewHolder.commentCountTv.setText(note.getCommentCount() + "");
            viewHolder.likeCountTv.setText(note.getLikeCount() + "");

            viewHolder.itemLayout.setTag(position);
            viewHolder.deleteTv.setTag(position);

            // 标题为空的话，隐藏标题
            if (note.getBelongModule().getId() == Module.MODULE_LIST.length - 2 || note.getTitle().equals("")) {
                // 校园生活模块隐藏标题，其他活动模块当标题为空时也隐藏
                viewHolder.noteTitleTv.setVisibility(View.GONE);
            }
        } else {
            super.onBindFooterViewHolder(holder);
        }
    }

    class OrdinaryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_note_title)
        TextView noteTitleTv;
        @BindView(R.id.tv_note_content)
        TextView noteContentTv;
        @BindView(R.id.tv_publish_time)
        TextView publishTimeTv;
        @BindView(R.id.tv_belong_module)
        TextView belongModuleTv;
        @BindView(R.id.tv_collect_count)
        TextView collectCountTv;
        @BindView(R.id.tv_comment_count)
        TextView commentCountTv;
        @BindView(R.id.tv_like_count)
        TextView likeCountTv;
        @BindView(R.id.ll_item_layout)
        LinearLayout itemLayout;
        @BindView(R.id.tv_delete)
        TextView deleteTv;

        public OrdinaryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.ll_item_layout, R.id.tv_delete})
        public void onClick(View view) {
            if (mListener == null) {
                return;
            }
            int position = (int) view.getTag();
            switch (view.getId()) {
                case R.id.ll_item_layout:
                    mListener.onPersonalNoteItemClick(position);
                    break;
                case R.id.tv_delete:
                    mListener.onItemDeleteClick(position);
                    break;
            }
        }
    }
}
