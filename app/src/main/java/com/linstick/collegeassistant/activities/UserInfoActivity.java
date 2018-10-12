package com.linstick.collegeassistant.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.base.BaseActivity;
import com.linstick.collegeassistant.beans.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInfoActivity extends BaseActivity {

    private final static String USER_INFO_TAG = "USER_INFO_TAG";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_user_icon)
    ImageView userIconIv;
    @BindView(R.id.tv_nickname)
    TextView nicknameTv;
    @BindView(R.id.tv_real_name)
    TextView realNameTv;
    @BindView(R.id.tv_age)
    TextView ageTv;
    @BindView(R.id.tv_gender)
    TextView genderTv;
    @BindView(R.id.tv_university)
    TextView universityTv;
    @BindView(R.id.tv_department)
    TextView departmentTv;
    @BindView(R.id.tv_major)
    TextView majorTv;
    @BindView(R.id.tv_klazz)
    TextView klazzTv;
    @BindView(R.id.tv_description)
    TextView descriptionTv;

    private User mUser;

    public static void startAction(Context context, User user) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.putExtra(USER_INFO_TAG, user);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);

        super.toolbar = toolbar;
        super.onCreate(savedInstanceState);

        initData();
    }

    private void initData() {
        mUser = (User) getIntent().getSerializableExtra(USER_INFO_TAG);
        if (mUser == null) {
            return;
        }
        userIconIv.setImageResource(R.drawable.bg_setting_header);
        nicknameTv.setText(mUser.getNickName());
        realNameTv.setText(mUser.getRealName());
        ageTv.setText(mUser.getAge() + "");
        genderTv.setText(mUser.isMale() ? "男" : "女");
        universityTv.setText(mUser.getUniversity());
        departmentTv.setText(mUser.getDepartment());
        majorTv.setText(mUser.getMajor());
        klazzTv.setText(mUser.getKlazz());
        descriptionTv.setText(mUser.getDescription());
    }
}
