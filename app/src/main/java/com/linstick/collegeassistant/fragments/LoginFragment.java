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
import com.linstick.collegeassistant.fragments.listeners.LoginFragmentClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends Fragment {

    @BindView(R.id.et_account_input)
    EditText accountInputEt;
    @BindView(R.id.et_password_input)
    EditText passwordInputEt;

    @OnClick({R.id.btn_login, R.id.tv_no_account, R.id.tv_forget_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String account = accountInputEt.getText().toString().trim();
                String password = passwordInputEt.getText().toString().trim();
                ((LoginFragmentClickListener) getActivity()).onLoginButtonClick(account, password);
                break;

            case R.id.tv_no_account:
                ((LoginFragmentClickListener) getActivity()).onNoAccountTextClick();
                break;

            case R.id.tv_forget_password:
                ((LoginFragmentClickListener) getActivity()).onForgetPasswordTextClick();
                break;
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
