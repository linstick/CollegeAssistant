package com.linstick.collegeassistant.beans;

import java.util.Date;

public class Feedback {
    private String content;
    private User sender;
    private Date sendTime;

    public Feedback() {
        this.sender = new User();
        this.sendTime = new Date();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
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
                "content='" + content + '\'' +
                ", sender=" + sender +
                ", sendTime=" + sendTime +
                '}';
    }
}
