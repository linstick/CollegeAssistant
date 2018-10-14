package com.linstick.collegeassistant.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.linstick.collegeassistant.App;
import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.base.BaseActivity;
import com.linstick.collegeassistant.beans.Feedback;
import com.linstick.collegeassistant.sqlite.FeedbackDaoUtil;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedBackActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_input)
    EditText inputEt;
    @BindView(R.id.rl_feedback_layout)
    RelativeLayout feedbackLayout;
    @BindView(R.id.ll_feedback_result_layout)
    LinearLayout feedbackResultLayout;

    @OnClick({R.id.iv_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_send:
                String content = inputEt.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(FeedBackActivity.this, "不能反馈空内容哦^_^", Toast.LENGTH_SHORT).show();
                    return;
                }
                feedbackLayout.requestFocus();
                // 发送反馈操作
                Feedback feedback = new Feedback();
                feedback.setContent(content);
                feedback.setSendTime(new Date());
                feedback.setUserId(App.getUserId());
                if (FeedbackDaoUtil.feedback(feedback)) {
                    feedbackLayout.setVisibility(View.GONE);
                    feedbackResultLayout.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(FeedBackActivity.this, "反馈失败", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);

        super.toolbar = toolbar;
        super.onCreate(savedInstanceState);
    }
}
