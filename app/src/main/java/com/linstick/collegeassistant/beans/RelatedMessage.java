package com.linstick.collegeassistant.beans;

import java.util.Date;

public class RelatedMessage {

    public final static int TYPE_COLLECT = 1;
    public final static int TYPE_COMMENT = 2;
    public final static int TYPE_LIKE = 3;

    private User sender;
    private Note relatedNote;
    private int type;
    private String content;
    private Date sendTime;

    public RelatedMessage() {
        this.sender = new User();
        this.relatedNote = new Note();
        this.type = (int) (Math.random() * 100) % 3 + 1;
        this.content = "我知道这是什么" + (int) (Math.random() * 1000);
        this.sendTime = new Date();
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Note getRelatedNote() {
        return relatedNote;
    }

    public void setRelatedNote(Note relatedNote) {
        this.relatedNote = relatedNote;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
        return "RelatedMessage{" +
                "sender=" + sender +
                ", relatedNote=" + relatedNote +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", sendTime=" + sendTime +
                '}';
    }
}
