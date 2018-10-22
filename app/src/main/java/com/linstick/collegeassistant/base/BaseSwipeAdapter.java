package com.linstick.collegeassistant.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.linstick.collegeassistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseSwipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected static final int TYPE_ORDINARY_ITEM = 0;
    protected static final int TYPE_FOOTER_ITEM = 1;
    protected boolean hasMore = true;

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    protected RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_list_footer, parent, false);
        return new FooterViewHolder(view);
    }

    protected void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
        BaseSwipeAdapter.FooterViewHolder viewHolder = (BaseSwipeAdapter.FooterViewHolder) holder;
        if (hasMore) {
            viewHolder.progressBar.setVisibility(View.VISIBLE);
            viewHolder.footerTipTv.setText("正在加载，请稍后...");
        } else {
            viewHolder.progressBar.setVisibility(View.GONE);
            viewHolder.footerTipTv.setText("没有更多数据了");
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
