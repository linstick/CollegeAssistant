package com.linstick.collegeassistant;

import android.app.Application;

import com.linstick.collegeassistant.beans.User;

public class App extends Application {

    private static User mUser;

    public static User getUser() {
        return mUser;
    }

    public static void setUser(User user) {
        mUser = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
