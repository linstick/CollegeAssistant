package com.linstick.collegeassistant.beans;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {

    private int id;
    private User publisher;
    private Date publishTime;
    private String content;

    public Comment() {
        this.publisher = new User();
        this.publishTime = new Date();
        this.content = "测试评论内容是大法官水电费打发是" + (int) (Math.random() * 1000);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", publisher=" + publisher +
                ", publishTime=" + publishTime +
                ", content='" + content + '\'' +
                '}';
    }
}
