package com.linstick.collegeassistant.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.fragments.listeners.RegisterFragmentClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterFragment extends Fragment {

    @BindView(R.id.et_account_input)
    EditText accountInputEt;
    @BindView(R.id.et_nickname_input)
    EditText nicknameInputEt;
    @BindView(R.id.et_password_input)
    EditText passwordInputEt;
    @BindView(R.id.et_confirm_password_input)
    EditText confirmPasswordInputEt;

    @OnClick({R.id.btn_register, R.id.tv_login, R.id.iv_user_icon})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                String account = accountInputEt.getText().toString().trim();
                String nickname = nicknameInputEt.getText().toString().trim();
                String password = passwordInputEt.getText().toString().trim();
                String confirmPassword = confirmPasswordInputEt.getText().toString().trim();

                ((RegisterFragmentClickListener) getActivity()).onRegisterButtonClick(account, nickname, password, confirmPassword);
                break;

            case R.id.tv_login:
                ((RegisterFragmentClickListener) getActivity()).onLoginTextClick();
                break;

            case R.id.iv_user_icon:

                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
