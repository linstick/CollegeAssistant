package com.linstick.collegeassistant.fragments.listeners;

public interface RegisterFragmentClickListener {
    void onRegisterButtonClick(String account, String nickname, String password, String confirmPassword);

    void onLoginTextClick();
}
