package com.linstick.collegeassistant.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.utils.TimeFactoryUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        View.OnClickListener {

    private static final String TAG = "NoteListAdapter";

    private final int TYPE_ORDINARY_ITEM = 0;
    private final int TYPE_FOOTER_ITEM = 1;

    private List<Note> mList;
    private OnNoteListPartialClickListener mListener;
    private boolean hasMore;

    public NoteListAdapter(List<Note> mList) {
        this.mList = mList;
        this.hasMore = true;
    }

    public void setOnNoteListPartialClickListener(OnNoteListPartialClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_ORDINARY_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_list, parent, false);
            return new OrdinaryViewHolder(view);
        }
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_list_footer, parent, false);
        return new FooterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrdinaryViewHolder) {
            OrdinaryViewHolder viewHolder = (OrdinaryViewHolder) holder;
            Note note = mList.get(position);
            viewHolder.userIconIv.setImageResource(R.drawable.bg_setting_header);
            viewHolder.nicknameTv.setText(note.getPublisher().getNickName());
            viewHolder.publishTimeTv.setText(TimeFactoryUtil.timeToLeftTime(note.getPublishTime()));
            viewHolder.belongModuleTv.setText(note.getBelongModule().getName());
            viewHolder.noteTitleTv.setText(note.getTitle());
            viewHolder.collectCountTv.setText(note.getCollectCount() + "");
            viewHolder.commentCountTv.setText(note.getCommentCount() + "");
            viewHolder.likeCountTv.setText(note.getLikeCount() + "");
            viewHolder.likeIv.setImageResource(note.isLiked() ? R.drawable.ic_like_red : R.drawable.ic_like_gray);

            if (mListener != null) {
                viewHolder.collectCountLayout.setOnClickListener(this);
                viewHolder.collectCountLayout.setTag(position);
                viewHolder.commentCountLayout.setOnClickListener(this);
                viewHolder.commentCountLayout.setTag(position);
                viewHolder.likeCountLayout.setOnClickListener(this);
                viewHolder.likeCountLayout.setTag(position);
                viewHolder.itemLayout.setOnClickListener(this);
                viewHolder.itemLayout.setTag(position);
                viewHolder.userIconIv.setOnClickListener(this);
                viewHolder.userIconIv.setTag(position);
                viewHolder.nicknameTv.setOnClickListener(this);
                viewHolder.nicknameTv.setTag(position);
            }
        } else {
            FooterViewHolder viewHolder = (FooterViewHolder) holder;
            if (hasMore) {
                viewHolder.progressBar.setVisibility(View.VISIBLE);
                viewHolder.footerTipTv.setText("正在加载，请稍后...");
            } else {
                viewHolder.progressBar.setVisibility(View.GONE);
                viewHolder.footerTipTv.setText("没有更多数据了");
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() > 0 ? mList.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position < mList.size() ? TYPE_ORDINARY_ITEM : TYPE_FOOTER_ITEM;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    @Override
    public void onClick(View view) {
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

    static class OrdinaryViewHolder extends RecyclerView.ViewHolder {

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
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.footer_progress_bar)
        ProgressBar progressBar;
        @BindView(R.id.footer_tip)
        TextView footerTipTv;

        public FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
