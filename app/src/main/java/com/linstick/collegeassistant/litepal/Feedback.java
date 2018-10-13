package com.linstick.collegeassistant.litepal;

import com.linstick.collegeassistant.beans.User;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Date;

public class Feedback extends DataSupport implements Serializable {
    private User sender;

    private int userId;
    private String content;
    private Date sendTime;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "sender=" + sender +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", sendTime=" + sendTime +
                '}';
    }
}
