package com.linstick.collegeassistant.utils;

import java.util.Date;

public class TimeFactoryUtil {

    public static String timeToLeftTime(Date date) {
        Date currDate = new Date();
        long minusValue = currDate.getYear() - date.getYear();   // 单位：年
        if (minusValue > 0) {
            return minusValue + "年前";
        }
        // 同一年
        minusValue = currDate.getMonth() - date.getMonth();  // 单位：月
        if (minusValue > 0) {
            return minusValue + "月前";
        }
        // 同一个月
        minusValue = currDate.getDay() - date.getDay(); // 单位：天
        if (minusValue > 1) {
            return minusValue + "天前";
        }
        if (minusValue == 1) {
            return "昨天";
        }
        // 同一天
        minusValue = (new Date().getTime() - date.getTime()) / 1000; // 单位：秒
        if (minusValue < 60) {
            return "刚刚";
        }
        minusValue /= 60; // 单位：分钟
        if (minusValue < 60) {
            return minusValue + "分钟前";
        }
        minusValue /= 60; // 单位：时
        return minusValue + "小时前";
    }
}
