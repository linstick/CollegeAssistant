package com.linstick.collegeassistant.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.base.BaseActivity;
import com.linstick.collegeassistant.beans.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInfoActivity extends BaseActivity {

    private final static String USER_INFO_TAG = "USER_INFO_TAG";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

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
        Toast.makeText(UserInfoActivity.this, mUser.getNickName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
