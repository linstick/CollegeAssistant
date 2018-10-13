package com.linstick.collegeassistant.sqlite;

import com.linstick.collegeassistant.litepal.Collection;
import com.linstick.collegeassistant.litepal.Comment;
import com.linstick.collegeassistant.litepal.Module;
import com.linstick.collegeassistant.litepal.Note;
import com.linstick.collegeassistant.litepal.User;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class NoteDaoUtil {
    private static final String TAG = "NoteDaoUtil";

    private final static int CONDITION_NONE = 1;
    private final static int CONDITION_BY_USER_ID = 2;
    private final static int CONDITION_BY_MODULE_ID = 3;
    private final static int CONDITION_BY_KEYWORD = 4;
    private final static int CONDITION_COLLECTED_NOTE_BY_USER_ID = 5;

    private final static int DEFAULT_PAGE_SIZE = 10;

    // 通过用户id查找
    public static List<Note> findNotesByPublisherId(int userId) {
        return findNotes(0, DEFAULT_PAGE_SIZE, CONDITION_BY_USER_ID, userId + "");
    }

    public static List<Note> findNotesByPublisherId(int userId, int fromIndex) {
        return findNotes(fromIndex, DEFAULT_PAGE_SIZE, CONDITION_BY_USER_ID, userId + "");
    }

    public static List<Note> findNotesByPublisherId(int userId, int fromIndex, int pageSize) {
        return findNotes(fromIndex, pageSize, CONDITION_BY_USER_ID, userId + "");
    }

    // 通过所属模块id来找
    public static List<Note> findNotesByBelongModuleId(int moduleId) {
        return findNotes(0, DEFAULT_PAGE_SIZE, CONDITION_BY_MODULE_ID, moduleId + "");
    }

    public static List<Note> findNotesByBelongModuleId(int moduleId, int fromIndex) {
        return findNotes(fromIndex, DEFAULT_PAGE_SIZE, CONDITION_BY_MODULE_ID, moduleId + "");
    }

    public static List<Note> findNotesByBelongModuleId(int moduleId, int fromIndex, int pageSize) {
        return findNotes(fromIndex, pageSize, CONDITION_BY_MODULE_ID, moduleId + "");
    }

    // 通过标题或内容关键字查找
    public static List<Note> findNotesByKeyword(String keyword) {
        return findNotesByKeyword(keyword, 0, DEFAULT_PAGE_SIZE);
    }

    public static List<Note> findNotesByKeyword(String keyword, int fromIndex) {
        return findNotesByKeyword(keyword, fromIndex, DEFAULT_PAGE_SIZE);
    }

    public static List<Note> findNotesByKeyword(String keyword, int fromIndex, int pageSize) {
        return findNotes(fromIndex, pageSize, CONDITION_BY_KEYWORD, keyword);
    }

    // 通过用户id查找收藏的帖子
    public static List<Note> findCollectedNotesForUser(int userId) {
        return findCollectedNotesForUser(userId, 0, DEFAULT_PAGE_SIZE);
    }

    public static List<Note> findCollectedNotesForUser(int userId, int fromIndex) {
        return findCollectedNotesForUser(userId, fromIndex, DEFAULT_PAGE_SIZE);
    }

    public static List<Note> findCollectedNotesForUser(int userId, int fromIndex, int pageSize) {
        return findNotes(fromIndex, pageSize, CONDITION_COLLECTED_NOTE_BY_USER_ID, userId + "");
    }

    // 无条件查找
    public static List<Note> findNotes() {
        return findNotes(0, DEFAULT_PAGE_SIZE, CONDITION_NONE, "");
    }

    public static List<Note> findNotes(int fromIndex) {
        return findNotes(fromIndex, DEFAULT_PAGE_SIZE, CONDITION_NONE, "");
    }

    private static List<Note> findNotes(int fromIndex, int pageSize, int conditionType, String value) {
        List<Note> result = null;
        switch (conditionType) {
            case CONDITION_NONE:
                result = DataSupport.limit(pageSize).offset(fromIndex).find(Note.class);
                break;
            case CONDITION_BY_USER_ID:
                result = DataSupport.where("publisherId = ?", value).limit(pageSize).offset(fromIndex).find(Note.class);
                break;
            case CONDITION_BY_MODULE_ID:
                result = DataSupport.where("belongModuleId = ?", value).limit(pageSize).offset(fromIndex).find(Note.class);
                break;
            case CONDITION_BY_KEYWORD:
                result = DataSupport.where("title like ? or content like ?", "%" + value + "%", "%" + value + "%").limit(pageSize).offset(fromIndex).find(Note.class);
                break;
            case CONDITION_COLLECTED_NOTE_BY_USER_ID:
                result = new ArrayList<>();
                List<Collection> collections = DataSupport.where("collectorId = ?", value).limit(pageSize).offset(fromIndex).find(Collection.class);
                for (Collection collection : collections) {
                    result.add(DataSupport.find(Note.class, collection.getBelongNoteId()));
                }
                break;
        }
        completeNoteData(result, conditionType);
        return result;
    }

    private static void completeNoteData(List<Note> list, int conditionType) {
        if (list == null) {
            return;
        }
        switch (conditionType) {
            case CONDITION_NONE:
            case CONDITION_BY_KEYWORD:
            case CONDITION_COLLECTED_NOTE_BY_USER_ID:
                for (Note note : list) {
                    User publisher1 = UserDaoUtil.findUser(note.getPublisherId());
                    note.setPublisher(publisher1);
                    Module module = DataSupport.find(Module.class, note.getBelongModuleId());
                    note.setBelongModule(module);
                    int collectCount = DataSupport.where("belongNoteId = ?", note.getId() + "").count(Collection.class);
                    note.setCollectCount(collectCount);
                    int commentCount = DataSupport.where("belongNoteId = ?", note.getId() + "").count(Comment.class);
                    note.setCommentCount(commentCount);
                }
                break;
            case CONDITION_BY_USER_ID:
                User publisher2 = UserDaoUtil.findUser(list.get(0).getPublisherId());
                for (Note note : list) {
                    note.setPublisher(publisher2);
                    Module module = DataSupport.find(Module.class, note.getBelongModuleId());
                    note.setBelongModule(module);
                    int collectCount = DataSupport.where("belongNoteId = ?", note.getId() + "").count(Collection.class);
                    note.setCollectCount(collectCount);
                    int commentCount = DataSupport.where("belongNoteId = ?", note.getId() + "").count(Comment.class);
                    note.setCommentCount(commentCount);
                }
                break;
            case CONDITION_BY_MODULE_ID:
                Module module = DataSupport.find(Module.class, list.get(0).getBelongModuleId());
                for (Note note : list) {
                    User publisher = UserDaoUtil.findUser(note.getPublisherId());
                    note.setPublisher(publisher);
                    note.setBelongModule(module);
                    int collectCount = DataSupport.where("belongNoteId = ?", note.getId() + "").count(Collection.class);
                    note.setCollectCount(collectCount);
                    int commentCount = DataSupport.where("belongNoteId = ?", note.getId() + "").count(Comment.class);
                    note.setCommentCount(commentCount);
                }
                break;
        }
    }

    public static Note findNoteById(int id) {
        Note note = DataSupport.find(Note.class, id);
        if (note != null) {
            note.setPublisher(UserDaoUtil.findUser(note.getPublisherId()));
            note.setBelongModule(DataSupport.find(Module.class, note.getBelongModuleId()));
        }
        return note;
    }

    public static boolean sendNote(Note note) {
        if (note == null) {
            return false;
        }
        note.save();
        return true;
    }


}
