package com.linstick.collegeassistant.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.adapters.listeners.OnRelatedMessageClickListener;
import com.linstick.collegeassistant.base.BaseSwipeAdapter;
import com.linstick.collegeassistant.beans.Relation;
import com.linstick.collegeassistant.utils.TimeFactoryUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SwipeRelatedMessageAdapter extends BaseSwipeAdapter {

    private List<Relation> mList;
    private OnRelatedMessageClickListener mListener;

    public SwipeRelatedMessageAdapter(List<Relation> mList) {
        this.mList = mList;
    }

    public void setOnRelatedMessageClickListener(OnRelatedMessageClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == BaseSwipeAdapter.TYPE_ORDINARY_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personal_related, parent, false);
            return new OrdinaryViewHolder(view);
        }
        return super.onCreateFooterViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrdinaryViewHolder) {
            Relation message = mList.get(position);
            OrdinaryViewHolder viewHolder = (OrdinaryViewHolder) holder;
            StringBuilder builder = new StringBuilder();
            builder.append("用户 ");
            builder.append(message.getSender().getNickName());
            builder.append(" ");
            switch (message.getType()) {
                case Relation.TYPE_COLLECT:
                    builder.append("在标题为“");
                    builder.append(message.getRelatedNote().getTitle());
                    builder.append("”的帖子中回复了你：");
                    builder.append(message.getContent());
                    break;
                case Relation.TYPE_COMMENT:
                    builder.append("点赞了标题为“");
                    builder.append(message.getRelatedNote().getTitle());
                    builder.append("”的帖子");
                    break;
                case Relation.TYPE_LIKE:
                    builder.append("收藏了标题为“");
                    builder.append(message.getRelatedNote().getTitle());
                    builder.append("”的帖子");
                    break;
            }
            viewHolder.contentTv.setText(builder.toString());
            viewHolder.sendTimeTv.setText(TimeFactoryUtil.dateToStringFormat(message.getSendTime()));
            viewHolder.browseDetailsTv.setTag(position);
        } else {
            super.onBindFooterViewHolder(holder);
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

    class OrdinaryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_send_time)
        TextView sendTimeTv;
        @BindView(R.id.tv_content)
        TextView contentTv;
        @BindView(R.id.tv_browse_details)
        TextView browseDetailsTv;

        public OrdinaryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.tv_browse_details})
        public void onClick(View view) {
            if (mListener == null) {
                return;
            }
            switch (view.getId()) {
                case R.id.tv_browse_details:
                    mListener.onRelatedMessageClick((int) view.getTag());
                    break;
            }
        }
    }

}
