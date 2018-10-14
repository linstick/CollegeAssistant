package com.linstick.collegeassistant.beans;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Date;

/**
 * 动态 关系表
 */
public class Relation extends DataSupport implements Serializable {

    public final static int TYPE_COLLECT = 1;
    public final static int TYPE_COMMENT = 2;
    public final static int TYPE_LIKE = 3;

    private Note relatedNote;
    private User sender;

    private int id;
    private int relatedNoteId;
    private int relatedUserId;
    private int senderId;
    private int type;
    private String content;
    private Date sendTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Note getRelatedNote() {
        return relatedNote;
    }

    public void setRelatedNote(Note relatedNote) {
        this.relatedNote = relatedNote;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public int getRelatedUserId() {
        return relatedUserId;
    }

    public void setRelatedUserId(int relatedUserId) {
        this.relatedUserId = relatedUserId;
    }

    public int getRelatedNoteId() {
        return relatedNoteId;
    }

    public void setRelatedNoteId(int relatedNoteId) {
        this.relatedNoteId = relatedNoteId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
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
        return "Relation{" +
                "relatedNote=" + relatedNote +
                ", sender=" + sender +
                ", id=" + id +
                ", relatedNoteId=" + relatedNoteId +
                ", relatedUserId=" + relatedUserId +
                ", senderId=" + senderId +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", sendTime=" + sendTime +
                '}';
    }
}