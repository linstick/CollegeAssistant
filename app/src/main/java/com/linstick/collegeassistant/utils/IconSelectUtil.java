package com.linstick.collegeassistant.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.linstick.collegeassistant.R;

public class IconSelectUtil {
    public static Drawable selectLikeIcon(Context context, boolean isLike) {
        Drawable drawable;
        if (isLike) {
            drawable = ContextCompat.getDrawable(context, R.drawable.ic_like_red);
        } else {
            drawable = ContextCompat.getDrawable(context, R.drawable.ic_like_gray);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }
}
