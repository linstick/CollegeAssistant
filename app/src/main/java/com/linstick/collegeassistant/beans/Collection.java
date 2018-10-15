package com.linstick.collegeassistant.beans;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Date;

/**
 * 收藏关系表
 */
public class Collection extends DataSupport implements Serializable {

    private int collectorId;
    private int belongNoteId;

    public Collection() {
    }

    public Collection(int collectorId, int belongNoteId) {
        this.collectorId = collectorId;
        this.belongNoteId = belongNoteId;
    }


    public int getBelongNoteId() {
        return belongNoteId;
    }

    public void setBelongNoteId(int belongNoteId) {
        this.belongNoteId = belongNoteId;
    }

    public int getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(int collectorId) {
        this.collectorId = collectorId;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "collectorId=" + collectorId +
                ", belongNoteId=" + belongNoteId +
                '}';
    }
}
