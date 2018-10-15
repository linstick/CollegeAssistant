package com.linstick.collegeassistant.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.adapters.listeners.OnCommentListClickListener;
import com.linstick.collegeassistant.beans.Comment;
import com.linstick.collegeassistant.utils.TimeFactoryUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> implements View.OnClickListener {
    private List<Comment> mList;
    private OnCommentListClickListener mListener;

    public CommentListAdapter(List<Comment> mList) {
        this.mList = mList;
    }

    public void setOnCommentListClickListener(OnCommentListClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = mList.get(position);
        holder.userIconIv.setImageResource(comment.getPublisher().getIconUrl());
        holder.nicknameTv.setText(comment.getPublisher().getNickName());
        holder.publishTimeTv.setText(TimeFactoryUtil.dateToStringFormat(comment.getPublishTime()));
        holder.commentTv.setText(comment.getContent());
        if (position + 1 == mList.size()) {
            holder.divideLineView.setVisibility(View.GONE);
        }

        if (mListener != null) {
            holder.userIconIv.setOnClickListener(this);
            holder.userIconIv.setTag(position);
            holder.nicknameTv.setOnClickListener(this);
            holder.nicknameTv.setTag(position);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View view) {
        mListener.onUserInfoClick((int) view.getTag());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_user_icon)
        ImageView userIconIv;
        @BindView(R.id.tv_nickname)
        TextView nicknameTv;
        @BindView(R.id.tv_publish_time)
        TextView publishTimeTv;
        @BindView(R.id.tv_comment)
        TextView commentTv;
        @BindView(R.id.view_divide_line)
        View divideLineView;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
