package com.linstick.collegeassistant;

import com.linstick.collegeassistant.beans.User;

import org.litepal.LitePalApplication;

public class App extends LitePalApplication {

    private static User mUser = new User();

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
