package com.linstick.collegeassistant.sqlite;

import com.linstick.collegeassistant.beans.Collection;
import com.linstick.collegeassistant.beans.Comment;
import com.linstick.collegeassistant.beans.Like;
import com.linstick.collegeassistant.beans.Module;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.beans.User;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class NoteDaoUtil {
    private static final String TAG = "NoteDaoUtil";

    private final static int CONDITION_BEFORE_NOTE_BY_NONE = 1;
    private final static int CONDITION_BEFORE_NOTE_BY_USER_ID = 2;
    private final static int CONDITION_BEFORE_NOTE_BY_MODULE_ID = 3;
    private final static int CONDITION_BEFORE_NOTE_BY_KEYWORD = 4;
    private final static int CONDITION_BEFORE_NOTE_BY_COLLECTOR_ID = 5;
    private final static int CONDITION_AFTER_NOTE_BY_NONE = 6;
    private final static int CONDITION_AFTER_NOTE_BY_USER_ID = 7;
    private final static int CONDITION_AFTER_NOTE_BY_MODULE_ID = 8;
    private final static int CONDITION_AFTER_NOTE_BY_KEYWORD = 9;
    private final static int CONDITION_AFTER_NOTE_BY_COLLECTOR_ID = 10;

    public static Note findNoteById(int currUserId, int id) {
        Note note = DataSupport.find(Note.class, id);
        note.setPublisher(UserDaoUtil.findUser(note.getPublisherId()));
        note.setBelongModule(ModuleDaoUtil.findModuleById(note.getBelongModuleId()));
        int collectCount = DataSupport.where("belongNoteId = ?", note.getId() + "").count(Collection.class);
        note.setCollectCount(collectCount);
        int commentCount = DataSupport.where("belongNoteId = ?", note.getId() + "").count(Comment.class);
        note.setCommentCount(commentCount);
        int likeCount = DataSupport.where("belongNoteId = ?", note.getId() + "").count(Like.class);
        note.setLikeCount(likeCount);
        if (currUserId != -1) {
            note.setLiked(DataSupport.where("likerId = ? and belongNoteId = ?", currUserId + "", note.getId() + "").findFirst(Like.class) == null ? false : true);
            note.setCollected(DataSupport.where("collectorId = ? and belongNoteId = ?", currUserId + "", note.getId() + "").findFirst(Collection.class) == null ? false : true);
        }
        return note;
    }

    // 无条件查询id比指定id的小的帖子，帖子的id越小，表示发布的越久，
    // 这里相当于无条件加载更多，限制页面大小
    public static List<Note> findBeforeNotes(int currUserId, int id, int pageSize) {
        return findNotes(currUserId, id, pageSize, CONDITION_BEFORE_NOTE_BY_NONE, "");
    }

    public static List<Note> findBeforeNotesByKeyword(int currUserId, int id, int pageSize, String keyword) {
        return findNotes(currUserId, id, pageSize, CONDITION_BEFORE_NOTE_BY_KEYWORD, keyword);
    }

    public static List<Note> findBeforeNotesByUserId(int currUserId, int id, int pageSize) {
        return findNotes(currUserId, id, pageSize, CONDITION_BEFORE_NOTE_BY_USER_ID, currUserId + "");
    }

    public static List<Note> findBeforeNotesByModuleId(int currUserId, int id, int pageSize, int moduleId) {
        return findNotes(currUserId, id, pageSize, CONDITION_BEFORE_NOTE_BY_MODULE_ID, moduleId + "");
    }

    public static List<Note> findBeforeNotesByCollectorId(int currUserId, int id, int pageSize) {
        return findNotes(currUserId, id, pageSize, CONDITION_BEFORE_NOTE_BY_COLLECTOR_ID, currUserId + "");
    }


    // 无条件查询id比指定id的大的帖子，帖子的id越大，表示发布的越早，
    // 这里相当于无条件下拉刷新
    public static List<Note> findAfterNotes(int currUserId, int id) {
        return findNotes(currUserId, id, Integer.MAX_VALUE, CONDITION_AFTER_NOTE_BY_NONE, "");
    }

    public static List<Note> findAfterNotesByKeyword(int currUserId, int id, String keyword) {
        return findNotes(currUserId, id, Integer.MAX_VALUE, CONDITION_AFTER_NOTE_BY_KEYWORD, keyword);
    }

    public static List<Note> findAfterNotesByUserId(int currUserId, int id) {
        return findNotes(currUserId, id, Integer.MAX_VALUE, CONDITION_AFTER_NOTE_BY_USER_ID, currUserId + "");
    }

    public static List<Note> findAfterNotesByModuleId(int currUserId, int id, int moduleId) {
        return findNotes(currUserId, id, Integer.MAX_VALUE, CONDITION_AFTER_NOTE_BY_MODULE_ID, moduleId + "");
    }

    public static List<Note> findAfterNotesByCollectorId(int currUserId, int id) {
        return findNotes(currUserId, id, Integer.MAX_VALUE, CONDITION_AFTER_NOTE_BY_COLLECTOR_ID, currUserId + "");
    }

    private static List<Note> findNotes(int currUserId, int id, int pageSize, int conditionType, String value) {
        List<Note> result = null;
        switch (conditionType) {
            case CONDITION_BEFORE_NOTE_BY_NONE:
                result = DataSupport
                        .where("id < ?", id + "")
                        .order("id desc")
                        .limit(pageSize)
                        .find(Note.class);
                break;
            case CONDITION_BEFORE_NOTE_BY_USER_ID:
                result = DataSupport
                        .where("id < ? and publisherId = ?", id + "", value)
                        .order("id desc")
                        .limit(pageSize)
                        .find(Note.class);
                break;
            case CONDITION_BEFORE_NOTE_BY_MODULE_ID:
                result = DataSupport
                        .where("id < ? and belongModuleId = ?", id + "", value)
                        .order("id desc")
                        .limit(pageSize)
                        .find(Note.class);
                break;
            case CONDITION_BEFORE_NOTE_BY_KEYWORD:
                result = DataSupport
                        .where("id < ? and (title like ? or content like ?)", id + "", "%" + value + "%", "%" + value + "%")
                        .order("id desc")
                        .limit(pageSize)
                        .find(Note.class);
                break;
            case CONDITION_BEFORE_NOTE_BY_COLLECTOR_ID:
                result = new ArrayList<>();
                List<Collection> collections = DataSupport
                        .where("id < ? and collectorId = ?", id + "", value)
                        .order("id desc")
                        .limit(pageSize)
                        .find(Collection.class);
                for (Collection collection : collections) {
                    result.add(DataSupport.find(Note.class, collection.getBelongNoteId()));
                }
                break;
            case CONDITION_AFTER_NOTE_BY_NONE:
                result = DataSupport
                        .where("id > ?", id + "")
                        .order("id desc")
                        .find(Note.class);
                break;
            case CONDITION_AFTER_NOTE_BY_USER_ID:
                result = DataSupport
                        .where("id > ? and publisherId = ?", id + "", value)
                        .order("id desc")
                        .find(Note.class);
                break;
            case CONDITION_AFTER_NOTE_BY_MODULE_ID:
                result = DataSupport
                        .where("id > ? and belongModuleId = ?", id + "", value)
                        .order("id desc")
                        .find(Note.class);
                break;
            case CONDITION_AFTER_NOTE_BY_KEYWORD:
                result = DataSupport
                        .where("id > ? and (title like ? or content like ?)", id + "", "%" + value + "%", "%" + value + "%")
                        .order("id desc")
                        .find(Note.class);
                break;
            case CONDITION_AFTER_NOTE_BY_COLLECTOR_ID:
                result = new ArrayList<>();
                List<Collection> collectionList = DataSupport
                        .where("id < ? and collectorId = ?", id + "", value)
                        .order("id desc")
                        .find(Collection.class);
                for (Collection collection : collectionList) {
                    result.add(DataSupport.find(Note.class, collection.getBelongNoteId()));
                }
                break;
        }
        completeNoteData(result, currUserId, conditionType);
        return result;
    }

    private static void completeNoteData(List<Note> list, int currUserId, int conditionType) {
        if (list == null || list.size() == 0) {
            return;
        }
        switch (conditionType) {
            case CONDITION_BEFORE_NOTE_BY_NONE:
            case CONDITION_BEFORE_NOTE_BY_KEYWORD:
            case CONDITION_BEFORE_NOTE_BY_COLLECTOR_ID:
            case CONDITION_AFTER_NOTE_BY_NONE:
            case CONDITION_AFTER_NOTE_BY_KEYWORD:
            case CONDITION_AFTER_NOTE_BY_COLLECTOR_ID:
                for (Note note : list) {
                    User publisher1 = UserDaoUtil.findUser(note.getPublisherId());
                    note.setPublisher(publisher1);
                    Module module = DataSupport.find(Module.class, note.getBelongModuleId());
                    note.setBelongModule(module);

                }
                break;
            case CONDITION_BEFORE_NOTE_BY_USER_ID:
            case CONDITION_AFTER_NOTE_BY_USER_ID:
                User publisher2 = UserDaoUtil.findUser(list.get(0).getPublisherId());
                for (Note note : list) {
                    note.setPublisher(publisher2);
                    Module module = DataSupport.find(Module.class, note.getBelongModuleId());
                    note.setBelongModule(module);
                }
                break;
            case CONDITION_BEFORE_NOTE_BY_MODULE_ID:
            case CONDITION_AFTER_NOTE_BY_MODULE_ID:
                Module module = DataSupport.find(Module.class, list.get(0).getBelongModuleId());
                for (Note note : list) {
                    User publisher = UserDaoUtil.findUser(note.getPublisherId());
                    note.setPublisher(publisher);
                    note.setBelongModule(module);
                }
                break;
        }
        for (Note note : list) {
            int collectCount = DataSupport.where("belongNoteId = ?", note.getId() + "").count(Collection.class);
            note.setCollectCount(collectCount);
            int commentCount = DataSupport.where("belongNoteId = ?", note.getId() + "").count(Comment.class);
            note.setCommentCount(commentCount);
            int likeCount = DataSupport.where("belongNoteId = ?", note.getId() + "").count(Like.class);
            note.setLikeCount(likeCount);
            if (currUserId != -1) {
                note.setLiked(DataSupport.where("likerId = ? and belongNoteId = ?", currUserId + "", note.getId() + "").findFirst(Like.class) == null ? false : true);
                note.setCollected(DataSupport.where("collectorId = ? and belongNoteId = ?", currUserId + "", note.getId() + "").findFirst(Collection.class) == null ? false : true);
            }
        }
    }

    public static boolean sendNote(Note note) {
        if (note == null) {
            return false;
        }
        note.save();
        return true;
    }
}
