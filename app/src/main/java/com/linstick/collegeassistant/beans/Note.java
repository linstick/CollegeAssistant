package com.linstick.collegeassistant.beans;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
    private User publisher;
    private Module belongModule;

    private int noteId;
    private String title;
    private String content;
    private Date publishTime;

    private Date startTime;
    private String keepTime;
    private String address;
    private String remarks;

    private int collectCount;
    private int commentCount;
    private int likeCount;
    private boolean isLiked;
    private boolean isCollected;

    public Note() {
        this.publisher = new User();
        this.belongModule = new Module();
        this.title = "标题" + (int) (Math.random() * 10000);
        this.content = "测试内容阿的说法打发大师傅撒打撒打发阿斯蒂芬方式四大非敢死队发撒打发";
        this.publishTime = new Date();
        this.collectCount = (int) (Math.random() * 100);
        this.commentCount = (int) (Math.random() * 1000);
        this.likeCount = (int) (Math.random() * 500);
        this.isLiked = false;
        this.isCollected = false;
        this.startTime = new Date();
        this.keepTime = "2个小时";
        this.address = "广东工业大学(大学城校区)";
        this.remarks = "记得带上1支笔和3份简历";


    }

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

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
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

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
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
}
