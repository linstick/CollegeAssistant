package com.linstick.collegeassistant.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Toast;

import com.linstick.collegeassistant.App;
import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.base.BaseActivity;
import com.linstick.collegeassistant.beans.User;
import com.linstick.collegeassistant.fragments.LoginFragment;
import com.linstick.collegeassistant.fragments.RegisterFragment;
import com.linstick.collegeassistant.fragments.listeners.LoginFragmentClickListener;
import com.linstick.collegeassistant.fragments.listeners.RegisterFragmentClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginFragmentClickListener, RegisterFragmentClickListener {

    public final static String TYPE_TAG = "TYPE";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static void startAction(Context context, boolean isLogin) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(TYPE_TAG, isLogin);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        super.toolbar = toolbar;
        super.onCreate(savedInstanceState);

        boolean isLogin = getIntent().getBooleanExtra(TYPE_TAG, true);
        getSupportActionBar().setTitle(isLogin ? "用户登录" : "用户注册");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, isLogin ? new LoginFragment() : new RegisterFragment()).commit();
    }

    @Override
    public void onLoginButtonClick(String account, String password) {
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // 登录操作
        App.setUser(new User());
        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
        LoginActivity.this.finish();
    }

    @Override
    public void onNoAccountTextClick() {
        getSupportActionBar().setTitle("用户注册");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RegisterFragment()).commit();
    }

    @Override
    public void onForgetPasswordTextClick() {
        Toast.makeText(LoginActivity.this, "该功能正在开发中，敬请期待", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterButtonClick(String account, String nickname, String password, String confirmPassword) {
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(nickname)) {
            Toast.makeText(LoginActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(LoginActivity.this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(LoginActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        // 注册操作

        Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
        // 注册完成了顺便登录
        User user = new User();
        user.setNickName(nickname);
        App.setUser(user);
        LoginActivity.this.finish();
    }

    @Override
    public void onLoginTextClick() {
        getSupportActionBar().setTitle("用户登录");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
    }
}
