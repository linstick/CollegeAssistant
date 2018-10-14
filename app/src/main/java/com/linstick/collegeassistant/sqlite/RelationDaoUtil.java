package com.linstick.collegeassistant.sqlite;

import android.util.Log;

import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.beans.Relation;

import org.litepal.crud.DataSupport;

import java.util.List;

public class RelationDaoUtil {

    private static final String TAG = "RelationDaoUtil";


    public static List<Relation> findBeforeRelationsByUserId(int userId, int lastId, int pageSize) {
        List<Relation> list = DataSupport
                .where("id < ? and relatedUserId = ?", lastId + "", userId + "")
                .order("id desc")
                .limit(pageSize)
                .find(Relation.class);
        completeRelationData(list);
        Log.d(TAG, "findBeforeRelationsByUserId: 来过 " + list.size());
        return list;
    }

    public static List<Relation> findAfterRelationsByUserId(int userId, int firstId) {
        List<Relation> list = DataSupport
                .where("id > ? and relatedUserId = ?", firstId + "", userId + "")
                .order("id desc")
                .find(Relation.class);
        completeRelationData(list);
        Log.d(TAG, "findAfterRelationsByUserId: laiguo " + list.size());
        return list;
    }

    private static void completeRelationData(List<Relation> list) {
        if (list == null) {
            return;
        }
        for (Relation relation : list) {
            relation.setRelatedNote(DataSupport.find(Note.class, relation.getRelatedNoteId()));
            relation.setSender(UserDaoUtil.findUser(relation.getSenderId()));
        }
    }

}
