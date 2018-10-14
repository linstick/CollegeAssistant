package com.linstick.collegeassistant.beans;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class Like extends DataSupport implements Serializable {
    private int likerId;
    private int belongNoteId;

    public Like() {
    }

    public Like(int likerId, int belongNoteId) {
        this.likerId = likerId;
        this.belongNoteId = belongNoteId;
    }

    public int getLikerId() {
        return likerId;
    }

    public void setLikerId(int likerId) {
        this.likerId = likerId;
    }

    public int getBelongNoteId() {
        return belongNoteId;
    }

    public void setBelongNoteId(int belongNoteId) {
        this.belongNoteId = belongNoteId;
    }

    @Override
    public String toString() {
        return "Like{" +
                "likerId=" + likerId +
                ", belongNoteId=" + belongNoteId +
                '}';
    }
}
