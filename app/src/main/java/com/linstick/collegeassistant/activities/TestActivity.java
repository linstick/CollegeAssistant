package com.linstick.collegeassistant.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.beans.Collection;
import com.linstick.collegeassistant.beans.Comment;
import com.linstick.collegeassistant.beans.Like;
import com.linstick.collegeassistant.beans.Module;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.beans.Relation;
import com.linstick.collegeassistant.beans.User;
import com.linstick.collegeassistant.sqlite.CommentDaoUtil;
import com.linstick.collegeassistant.sqlite.NoteDaoUtil;
import com.linstick.collegeassistant.sqlite.RelationDaoUtil;
import com.linstick.collegeassistant.sqlite.UserDaoUtil;
import com.linstick.collegeassistant.utils.TimeFactoryUtil;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";

    private List<Module> moduleList = new ArrayList<>();


    @OnClick({R.id.btn_create, R.id.btn_insert, R.id.btn_query_note, R.id.btn_query_user, R.id.btn_query_comment, R.id.btn_query_relation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create:
                create();
                break;
            case R.id.btn_insert:
                insert();
                break;
            case R.id.btn_query_note:
                queryNote();
                break;
            case R.id.btn_query_user:
                queryUser();
                break;
            case R.id.btn_query_comment:
                queryComment();
                break;

            case R.id.btn_query_relation:
                queryRelation();
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        moduleList.add(new Module(1, "企业宣讲会", "宣讲会"));
        moduleList.add(new Module(2, "社团活动", "社团"));
        moduleList.add(new Module(3, "高校讲座", "讲座"));
        moduleList.add(new Module(4, "体育活动", "体育"));
        moduleList.add(new Module(5, "校园生活", "校园"));
        moduleList.add(new Module(6, "其他活动", "其他"));
    }

    private void create() {
        LitePal.getDatabase();
    }

    private void insert() {
        insertModuleData();
        insertUserData();
        insertNoteData();
        insertCommentData();
        insertCollectionData();
        insertRelationData();
        insertLikeData();
    }

    private void insertModuleData() {
       for (Module item : moduleList ){
           item.save();
       }
    }

    private void insertUserData() {
        new User(R.drawable.icon_08, "Admin", "陆志豪", 26, "华南理工大学", "14级", "计算机学院", "软件工程", "广东省广州市番禺区小谷围街道", "1334456563", "admin", "admin", true, "测试员", TimeFactoryUtil.createBeforeRandomDate()).save();
        new User(R.drawable.icon_01, "linstick", "罗瑞泳", 23, "广东工业大学", "15级", "计算机学院", "软件工程", "广东省广州市番禺区小谷围街道", "15102032936", "linstick@163.com", "PSC213903", true, "Android应用开发入门Coder", TimeFactoryUtil.createBeforeRandomDate()).save();
        new User(R.drawable.icon_02, "路人甲", "洛奇", 23, "广东工业大学", "13级", "计算机学院", "网络工程", "广东省广州市番禺区小谷围街道", "1223344545", "lq@163.com", "123456", true, "IOS开发", TimeFactoryUtil.createBeforeRandomDate()).save();
        new User(R.drawable.icon_03, "路人乙", "罗志杰", 20, "广州大学", "12级", "计算机学院", "计算机科学与技术", "广东省广州市番禺区小谷围街道", "455676342", "lzj@163.com", "123456", true, "全栈工程师", TimeFactoryUtil.createBeforeRandomDate()).save();
        new User(R.drawable.icon_04, "会飞的猪", "张明超", 30, "广州大学", "17级", "计算机学院", "软件工程", "广东省广州市番禺区小谷围街道", "124574233", "zmc@163.com", "123456", true, "C++游戏开发工程师", TimeFactoryUtil.createBeforeRandomDate()).save();
        new User(R.drawable.icon_05, "我不会让你知", "李宏毅", 20, "华南理工大学", "14级", "计算机学院", "软件工程", "广东省广州市番禺区小谷围街道", "1743565343", "user1", "user1", false, "Java后台开发工程师", TimeFactoryUtil.createBeforeRandomDate()).save();
        new User(R.drawable.icon_06, "1", "钟海明", 21, "华南理工大学", "14级", "计算机学院", "软件工程", "广东省广州市番禺区小谷围街道", "1334456563", "user2", "user3", true, "测试员", TimeFactoryUtil.createBeforeRandomDate()).save();
        new User(R.drawable.icon_07, "不喜欢格子", "李晓红", 23, "华南理工大学", "14级", "计算机学院", "软件工程", "广东省广州市番禺区小谷围街道", "1334456563", "user3", "user3", false, "测试员", TimeFactoryUtil.createBeforeRandomDate()).save();
        new User(R.drawable.icon_09, "你的输入有误", "林嘉欣", 24, "华南理工大学", "14级", "计算机学院", "软件工程", "广东省广州市番禺区小谷围街道", "1334456563", "user", "user", false, "测试员", TimeFactoryUtil.createBeforeRandomDate()).save();
    }

    private void insertNoteData() {
        int userIndex = 1;
        TimeFactoryUtil.currentBefore = System.currentTimeMillis() - 1000 * 60 * 60 * 150;
        TimeFactoryUtil.currentAfter = System.currentTimeMillis() + 1000 * 60 * 60 * 200;
        for (int i = 60; i > 0; i--) {
            for (int j = 0; j < moduleList.size() ; j++) {
                new Note(userIndex, j + 1, moduleList.get(j).getName() + "标题" + i, moduleList.get(j).getName() + "测试内容测试内容测试内容测试内容" + i,  TimeFactoryUtil.createBeforeRandomDate(), TimeFactoryUtil.createAfterRandomDate(),"2小时", "广东工业大学(大学城校区) 教5-203",  "带上1支笔和2份简历").save();
                userIndex = (userIndex + 1) % 9 + 1;
            }
        }
    }

    private void insertCommentData() {
        Comment comment = new Comment();
        comment.setBelongNoteId(1);
        comment.setPublisherId(1);
        comment.setContent("大家踊跃过来参加，现场有奖品喔^_^");
        comment.setPublishTime(new Date());
        comment.save();
        Comment comment1 = new Comment();
        comment1.setBelongNoteId(1);
        comment1.setPublisherId(1);
        comment1.setContent("等着你们哟");
        comment1.setPublishTime(new Date());
        comment1.save();

        Comment comment2 = new Comment();
        comment2.setBelongNoteId(2);
        comment2.setPublisherId(2);
        comment2.setContent("等着你们哟");
        comment2.setPublishTime(new Date());
        comment2.save();

        Comment comment3 = new Comment();
        comment3.setBelongNoteId(1);
        comment3.setPublisherId(2);
        comment3.setContent("我会来参加的");
        comment3.setPublishTime(new Date());
        comment3.save();
    }

    private void insertCollectionData() {
        Collection collection = new Collection();
        collection.setCollectorId(1);
        collection.setBelongNoteId(2);
        collection.save();

        Collection collection1 = new Collection();
        collection1.setCollectorId(1);
        collection1.setBelongNoteId(1);
        collection1.save();

        Collection collection2 = new Collection();
        collection2.setCollectorId(2);
        collection2.setBelongNoteId(1);
        collection2.save();
    }

    private void insertRelationData() {

        Relation relation1 = new Relation();
        relation1.setSenderId(1);
        relation1.setRelatedNoteId(2);
        relation1.setRelatedUserId(2);
        relation1.setType(Relation.TYPE_COLLECT);
        relation1.setSendTime(new Date());
        relation1.save();

        Relation relation2 = new Relation();
        relation2.setSenderId(2);
        relation2.setRelatedNoteId(1);
        relation2.setRelatedUserId(1);
        relation2.setType(Relation.TYPE_COLLECT);
        relation2.setSendTime(new Date());
        relation2.save();

        Relation relation3 = new Relation();
        relation3.setSenderId(1);
        relation3.setRelatedNoteId(2);
        relation3.setRelatedUserId(2);
        relation3.setType(Relation.TYPE_COMMENT);
        relation3.setContent("我会来参加的");
        relation3.setSendTime(new Date());
        relation3.save();
    }

    private void insertLikeData() {
        Like like = new Like();
        like.setLikerId(1);
        like.setBelongNoteId(1);
        like.save();

        Like like1 = new Like();
        like1.setLikerId(2);
        like1.setBelongNoteId(1);
        like1.save();

        Like like2 = new Like();
        like2.setLikerId(2);
        like2.setBelongNoteId(2);
        like2.save();
    }

    private void queryNote() {
        List<Note> list = NoteDaoUtil.findAfterNotesByKeyword(1, -1, "宣讲会");
        for (Note item : list) {
            Log.d(TAG, "query3: " + item);
        }
    }

    private void queryUser() {
        User user = UserDaoUtil.findUser(1);
        Log.d(TAG, "findUser: " + user);
        user = UserDaoUtil.loginVerify("15102032936", "lihongshi7610131");
        Log.d(TAG, "loginVerify: " + user);
    }

    private void queryComment() {
        List<Comment> list = CommentDaoUtil.findCommentsByNoteId(1);
        for (Comment item : list) {
            Log.d(TAG, "queryComment: " + item);
        }
    }

    private void queryRelation() {
        List<Relation> list = RelationDaoUtil.findAfterRelationsByUserId(2, -1);
        for (Relation item : list) {
            Log.d(TAG, "queryRelation: " + item);
        }
    }

}
