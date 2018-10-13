package com.linstick.collegeassistant.litepal;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * 收藏关系表
 */
public class Collection extends DataSupport implements Serializable {

    private int belongNoteId;
    private int collectorId;

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
                "belongNoteId=" + belongNoteId +
                ", collectorId=" + collectorId +
                '}';
    }
}
