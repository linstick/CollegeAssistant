package com.linstick.collegeassistant.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.linstick.collegeassistant.App;
import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.adapters.CommentListAdapter;
import com.linstick.collegeassistant.adapters.listeners.OnCommentListClickListener;
import com.linstick.collegeassistant.base.BaseActivity;
import com.linstick.collegeassistant.beans.Comment;
import com.linstick.collegeassistant.beans.Module;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.utils.TimeFactoryUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoteDetailActivity extends BaseActivity implements OnCommentListClickListener {

    private final static String NOTE_TAG = "NOTE";
    private final static String ADD_COMMENT_TAG = "ADD_COMMENT_TAG";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.iv_user_icon)
    ImageView userIconIv;
    @BindView(R.id.tv_nickname)
    TextView nicknameTv;
    @BindView(R.id.tv_publish_time)
    TextView publishTimeTv;

    @BindView(R.id.iv_collect)
    ImageView collectIv;
    @BindView(R.id.iv_like)
    ImageView likeIv;
    @BindView(R.id.tv_collect_count)
    TextView collectCountTv;
    @BindView(R.id.tv_like_count)
    TextView likeCountTv;

    @BindView(R.id.tv_note_title)
    TextView noteTitleTv;
    @BindView(R.id.tv_note_content)
    TextView noteContentTv;

    @BindView(R.id.ll_start_time_layout)
    LinearLayout startTimeLayout;
    @BindView(R.id.ll_address_layout)
    LinearLayout addressLayout;
    @BindView(R.id.ll_remarks_layout)
    LinearLayout remarksLayout;
    @BindView(R.id.tv_start_time)
    TextView startTimeTv;
    @BindView(R.id.tv_keep_time)
    TextView keepTimeTv;
    @BindView(R.id.tv_location)
    TextView locationTv;
    @BindView(R.id.tv_remarks)
    TextView remarksTv;

    @BindView(R.id.tv_comment_count)
    TextView commentCountTv;
    @BindView(R.id.rv_comment_list)
    RecyclerView commentListRv;

    @BindView(R.id.et_input)
    EditText inputEt;


    private Note mNote;
    private List<Comment> mCommentList;
    private CommentListAdapter mCommentAdapter;

    public static void startAction(Context context, Note note) {
        Intent intent = new Intent(context, NoteDetailActivity.class);
        intent.putExtra(NOTE_TAG, note);
        intent.putExtra(ADD_COMMENT_TAG, false);
        context.startActivity(intent);
    }

    public static void startActionByAddComment(Context context, Note note) {
        Intent intent = new Intent(context, NoteDetailActivity.class);
        intent.putExtra(NOTE_TAG, note);
        intent.putExtra(ADD_COMMENT_TAG, true);
        context.startActivity(intent);
    }

    @OnClick({R.id.iv_user_icon, R.id.ll_collect_count_layout, R.id.ll_like_count_layout, R.id.iv_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_icon:
                UserInfoActivity.startAction(NoteDetailActivity.this, mNote.getPublisher());
                break;
            case R.id.ll_collect_count_layout:
                mNote.setCollected(!mNote.isCollected());
                int collectCount = mNote.getCollectCount();
                if (mNote.isCollected()) {
                    mNote.setCollectCount(collectCount + 1);
                    Toast.makeText(NoteDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                } else {
                    mNote.setCollectCount(collectCount - 1);
                }
                collectCountTv.setText(mNote.getCollectCount() + "");
                collectIv.setImageResource(mNote.isCollected() ? R.drawable.ic_star_orange : R.drawable.ic_star_gray);
                break;
            case R.id.ll_like_count_layout:
                mNote.setLiked(!mNote.isLiked());
                int likeCount = mNote.getLikeCount();
                if (mNote.isLiked()) {
                    mNote.setLikeCount(likeCount + 1);
                } else {
                    mNote.setLikeCount(likeCount - 1);
                }
                likeCountTv.setText(mNote.getLikeCount() + "");
                likeIv.setImageResource(mNote.isLiked() ? R.drawable.ic_like_orange : R.drawable.ic_like_gray);
                break;
            case R.id.iv_send:
                if (App.getUser() == null) {
                    Toast.makeText(NoteDetailActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }

                String content = inputEt.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(NoteDetailActivity.this, "不能发表空评论喔^_^", Toast.LENGTH_SHORT).show();
                    return;
                }
                Comment comment = new Comment();
                comment.setContent(content);
                mCommentList.add(0, comment);
                mCommentAdapter.notifyDataSetChanged();
                mNote.setCommentCount(mNote.getCommentCount() + 1);
                commentCountTv.setText("评论(" + mNote.getCommentCount() + ")");
                inputEt.setText("");
                commentCountTv.requestFocus();
                Toast.makeText(NoteDetailActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_note_details);
        ButterKnife.bind(this);

        super.toolbar = toolbar;
        super.onCreate(savedInstanceState);

        initNoteData();
        initCommentData();
    }

    private void initNoteData() {
        if (getIntent().getBooleanExtra(ADD_COMMENT_TAG, false)) {
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            inputEt.requestFocus();
        }
        mNote = (Note) getIntent().getSerializableExtra(NOTE_TAG);
        userIconIv.setImageResource(R.drawable.bg_setting_header);
        nicknameTv.setText(mNote.getPublisher().getNickName());
        publishTimeTv.setText(TimeFactoryUtil.dateToStringFormat(mNote.getPublishTime()));
        noteTitleTv.setText(mNote.getTitle());
        collectCountTv.setText(mNote.getCollectCount() + "");
        commentCountTv.setText("评论(" + mNote.getCommentCount() + ")");
        likeCountTv.setText(mNote.getLikeCount() + "");
        likeIv.setImageResource(mNote.isLiked() ? R.drawable.ic_like_orange : R.drawable.ic_like_gray);
        collectIv.setImageResource(mNote.isCollected() ? R.drawable.ic_star_orange : R.drawable.ic_star_gray);

        startTimeTv.setText(TimeFactoryUtil.dateToStringFormat(mNote.getStartTime()));
        keepTimeTv.setText(mNote.getKeepTime());
        locationTv.setText(mNote.getAddress());
        remarksTv.setText(mNote.getRemarks());

        if (mNote.getBelongModule().getId() >= Module.MODULE_LIST.length - 2) {
            startTimeLayout.setVisibility(View.GONE);
            addressLayout.setVisibility(View.GONE);
            remarksLayout.setVisibility(View.GONE);
            if (mNote.getBelongModule().getId() == Module.MODULE_LIST.length - 2 || mNote.getTitle().equals("")) {
                noteTitleTv.setVisibility(View.GONE);
            }
        }
    }

    private void initCommentData() {
        mCommentList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mCommentList.add(new Comment());
        }
        mCommentAdapter = new CommentListAdapter(mCommentList);
        mCommentAdapter.setOnCommentListClickListener(this);
        commentListRv.setAdapter(mCommentAdapter);
        commentListRv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onUserInfoClick(int position) {
        UserInfoActivity.startAction(NoteDetailActivity.this, mCommentList.get(position).getPublisher());
    }
}
