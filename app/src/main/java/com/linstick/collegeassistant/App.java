package com.linstick.collegeassistant;

import com.linstick.collegeassistant.beans.User;
import com.linstick.collegeassistant.sqlite.UserDaoUtil;

import org.litepal.LitePalApplication;

public class App extends LitePalApplication {

    private static User mUser;

    public static int getUserId() {
        return mUser == null ? -1 : mUser.getId();
    }

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
