package com.linstick.collegeassistant.sqlite;

import com.linstick.collegeassistant.beans.Comment;

import org.litepal.crud.DataSupport;

import java.util.List;

public class CommentDaoUtil {

    public static List<Comment> findCommentsByNoteId(int noteId) {
        List<Comment> list = DataSupport
                .where("belongNoteId = ?", noteId + "")
                .order("publishTime desc")
                .find(Comment.class);
        completeCommentData(list);
        return list;
    }

    private static void completeCommentData(List<Comment> list) {
        if (list == null) {
            return;
        }
        for (Comment comment : list) {
            comment.setPublisher(UserDaoUtil.findUser(comment.getPublisherId()));
        }
    }
}
