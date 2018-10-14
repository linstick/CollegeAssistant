package com.linstick.collegeassistant.beans;


import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论表
 */
public class Comment extends DataSupport implements Serializable {
    private User publisher;

    private int belongNoteId;
    private int publisherId;

    private String content;
    private Date publishTime;

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public int getBelongNoteId() {
        return belongNoteId;
    }

    public void setBelongNoteId(int belongNoteId) {
        this.belongNoteId = belongNoteId;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "publisher=" + publisher +
                ", belongNoteId=" + belongNoteId +
                ", publisherId=" + publisherId +
                ", content='" + content + '\'' +
                ", publishTime=" + publishTime +
                '}';
    }
}
