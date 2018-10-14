package com.linstick.collegeassistant.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.linstick.collegeassistant.App;
import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.base.BaseActivity;
import com.linstick.collegeassistant.sqlite.UserDaoUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyPasswordActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_original_password_input)
    EditText originalPasswordInputEt;
    @BindView(R.id.et_new_password_input)
    EditText newPasswordInputEt;
    @BindView(R.id.et_confirm_password_input)
    EditText confirmPasswordInputEt;

    @OnClick({R.id.btn_commit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit:
                checkAndCommit();
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);

        super.toolbar = toolbar;
        super.onCreate(savedInstanceState);
    }

    private void checkAndCommit() {
        String originalPassword = originalPasswordInputEt.getText().toString().trim();
        String newPassword = newPasswordInputEt.getText().toString().trim();
        String confirmPassword = confirmPasswordInputEt.getText().toString().trim();
        if (TextUtils.isEmpty(originalPassword)) {
            Toast.makeText(ModifyPasswordActivity.this, "原密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(newPassword)) {
            Toast.makeText(ModifyPasswordActivity.this, "新密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(ModifyPasswordActivity.this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(ModifyPasswordActivity.this, "两次新密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        // 修改操作
        if (UserDaoUtil.resetPassword(App.getUserId(), originalPassword, newPassword)) {
            Toast.makeText(ModifyPasswordActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
            ModifyPasswordActivity.this.finish();
        } else {
            Toast.makeText(ModifyPasswordActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
        }


    }
}
