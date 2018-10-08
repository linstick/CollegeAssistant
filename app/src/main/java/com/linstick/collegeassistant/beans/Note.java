package com.linstick.collegeassistant.beans;

import java.util.Date;

public class Note {
    private User publisher;
    private Module belongModule;

    private int noteId;
    private String title;
    private String content;
    private Date publishTime;

    private int browseCount;
    private int commentCount;
    private int likeCount;
    private boolean isLike;

    public Note() {
        this.publisher = new User();
        this.belongModule = new Module();
        this.title = "标题" + (int) (Math.random() * 10000);
        this.content = "内容阿的说法打发大师傅撒打撒打发阿斯蒂芬方式四大非敢死队发撒打发";
        this.publishTime = new Date();
        this.browseCount = (int) (Math.random() * 100);
        this.commentCount = (int) (Math.random() * 1000);
        this.likeCount = (int) (Math.random() * 500);
        this.isLike = false;


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

    public int getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(int browseCount) {
        this.browseCount = browseCount;
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

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
