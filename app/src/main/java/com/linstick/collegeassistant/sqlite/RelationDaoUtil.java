package com.linstick.collegeassistant.sqlite;

import com.linstick.collegeassistant.litepal.Note;
import com.linstick.collegeassistant.litepal.Relation;

import org.litepal.crud.DataSupport;

import java.util.List;

public class RelationDaoUtil {

    private static final int DEFAULT_SIZE = 20;

    public static List<Relation> findRelationsByUserId(int userId) {
        return findRelationsByUserId(userId, 0, DEFAULT_SIZE);
    }

    public static List<Relation> findRelationsByUserId(int userId, int fromIndex) {
        return findRelationsByUserId(userId, fromIndex, DEFAULT_SIZE);
    }

    public static List<Relation> findRelationsByUserId(int userId, int fromIndex, int pageSize) {
        List<Relation> list = DataSupport.where("relatedUserId = ?", userId + "").limit(pageSize).offset(fromIndex).find(Relation.class);
        completeRelationData(list);
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
