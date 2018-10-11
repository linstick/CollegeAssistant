package com.linstick.collegeassistant.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.adapters.listeners.OnNoteListPartialClickListener;
import com.linstick.collegeassistant.base.BaseSwipeNoteAdapter;
import com.linstick.collegeassistant.beans.Module;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.utils.TimeFactoryUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoteListAdapter extends BaseSwipeNoteAdapter {


    private OnNoteListPartialClickListener mListener;
    private boolean showBelongModule = false;

    public NoteListAdapter(List<Note> list) {
        super(list);
    }

    public void setOnNoteListPartialClickListener(OnNoteListPartialClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ORDINARY_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_list, parent, false);
            return new OrdinaryViewHolder(view);
        }
        return super.onCreateFooterViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrdinaryViewHolder) {
            OrdinaryViewHolder viewHolder = (OrdinaryViewHolder) holder;
            Note note = mList.get(position);
            viewHolder.userIconIv.setImageResource(R.drawable.bg_setting_header);
            viewHolder.nicknameTv.setText(note.getPublisher().getNickName());
            viewHolder.publishTimeTv.setText(TimeFactoryUtil.timeToLeftTime(note.getPublishTime()));
            viewHolder.noteTitleTv.setText(note.getTitle());
            viewHolder.collectCountTv.setText(note.getCollectCount() + "");
            viewHolder.commentCountTv.setText(note.getCommentCount() + "");
            viewHolder.likeCountTv.setText(note.getLikeCount() + "");
            viewHolder.collectIv.setImageResource(note.isCollected() ? R.drawable.ic_star_orange : R.drawable.ic_star_gray);
            viewHolder.likeIv.setImageResource(note.isLiked() ? R.drawable.ic_like_orange : R.drawable.ic_like_gray);

            // 标题为空的话，隐藏标题
            if (note.getBelongModule().getId() == Module.MODULE_LIST.length - 2 || note.getTitle().equals("")) {
                // 校园生活模块隐藏标题，其他活动模块当标题为空时也隐藏
                viewHolder.noteTitleTv.setVisibility(View.GONE);
            }

            // 为点击事件做标记
            viewHolder.collectCountLayout.setTag(position);
            viewHolder.commentCountLayout.setTag(position);
            viewHolder.likeCountLayout.setTag(position);
            viewHolder.itemLayout.setTag(position);
            viewHolder.userIconIv.setTag(position);
            viewHolder.nicknameTv.setTag(position);

            // 判断是否需要显示帖子所属的模块
            if (showBelongModule) {
                viewHolder.belongModuleTv.setVisibility(View.VISIBLE);
                viewHolder.belongModuleTv.setText(note.getBelongModule().getName());
            }
        } else {
            super.onBindFooterViewHolder(holder);
        }
    }

    public void setShowBelongModule(boolean showBelongModule) {
        this.showBelongModule = showBelongModule;
    }

    class OrdinaryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_user_icon)
        ImageView userIconIv;
        @BindView(R.id.tv_nickname)
        TextView nicknameTv;
        @BindView(R.id.tv_publish_time)
        TextView publishTimeTv;
        @BindView(R.id.tv_belong_module)
        TextView belongModuleTv;
        @BindView(R.id.tv_note_title)
        TextView noteTitleTv;
        @BindView(R.id.tv_note_content)
        TextView noteContentTv;
        @BindView(R.id.tv_collect_count)
        TextView collectCountTv;
        @BindView(R.id.tv_comment_count)
        TextView commentCountTv;
        @BindView(R.id.tv_like_count)
        TextView likeCountTv;
        @BindView(R.id.iv_collect)
        ImageView collectIv;
        @BindView(R.id.iv_like)
        ImageView likeIv;
        @BindView(R.id.ll_collect_count_layout)
        LinearLayout collectCountLayout;
        @BindView(R.id.ll_comment_count_layout)
        LinearLayout commentCountLayout;
        @BindView(R.id.ll_like_count_layout)
        LinearLayout likeCountLayout;
        @BindView(R.id.ll_item_layout)
        LinearLayout itemLayout;

        public OrdinaryViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({
                R.id.iv_user_icon,
                R.id.tv_nickname,
                R.id.ll_item_layout,
                R.id.ll_collect_count_layout,
                R.id.ll_comment_count_layout,
                R.id.ll_like_count_layout
        })
        public void onClick(View view) {
            if (mListener == null) {
                return;
            }
            int position = (int) view.getTag();
            switch (view.getId()) {
                case R.id.iv_user_icon:
                case R.id.tv_nickname:
                    mListener.onUserInfoClick(position);
                    break;
                case R.id.ll_item_layout:
                    mListener.onNoteItemClick(position);
                    break;
                case R.id.ll_collect_count_layout:
                    mListener.onChangeCollectClick(position);
                    break;
                case R.id.ll_comment_count_layout:
                    mListener.onAddCommentClick(position);
                    break;
                case R.id.ll_like_count_layout:
                    mListener.onChangeLikeClick(position);
                    break;
            }
        }
    }
}
