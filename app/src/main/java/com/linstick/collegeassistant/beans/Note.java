package com.linstick.collegeassistant.beans;


import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Date;

/**
 * 帖子表
 */
public class Note extends DataSupport implements Serializable {

    // 不能能够保存到SQLite中的字段
    private User publisher;
    private Module belongModule;

    // 能够保存到SQLite中的字段
    private int publisherId;
    private int belongModuleId;

    private int id;
    private String title;
    private String content;
    private Date publishTime;

    private Date startTime;
    private String keepTime;
    private String address;
    private String remarks;

    // 保存到SQLite中但无实际意义的字段
    private int collectCount;
    private int commentCount;
    private int likeCount;
    private boolean isLiked;
    private boolean isCollected;

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public Module getBelongModule() {
        return belongModule;
    }

    public void setBelongModule(Module belongModule) {
        this.belongModule = belongModule;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public int getBelongModuleId() {
        return belongModuleId;
    }

    public void setBelongModuleId(int belongModuleId) {
        this.belongModuleId = belongModuleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getKeepTime() {
        return keepTime;
    }

    public void setKeepTime(String keepTime) {
        this.keepTime = keepTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    @Override
    public String toString() {
        return "Note{" +
                "publisher=" + publisher +
                ", belongModule=" + belongModule +
                ", publisherId=" + publisherId +
                ", belongModuleId=" + belongModuleId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publishTime=" + publishTime +
                ", startTime=" + startTime +
                ", keepTime='" + keepTime + '\'' +
                ", address='" + address + '\'' +
                ", remarks='" + remarks + '\'' +
                ", likeCount=" + likeCount +
                ", collectCount=" + collectCount +
                ", commentCount=" + commentCount +
                ", isLiked=" + isLiked +
                ", isCollected=" + isCollected +
                '}';
    }
}
