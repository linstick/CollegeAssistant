package com.linstick.collegeassistant.sqlite;

import com.linstick.collegeassistant.litepal.Feedback;

public class FeedbackDaoUtil {

    public static boolean feedback(Feedback info) {
        if (info == null) {
            return false;
        }
        info.save();
        return true;
    }
}
