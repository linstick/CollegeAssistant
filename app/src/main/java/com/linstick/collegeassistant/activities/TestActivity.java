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

import org.litepal.LitePal;

import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";


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
        Module module = new Module();
        module.setName("企业宣讲会");
        module.setSimpleName("宣讲会");
        module.save();
        Module module1 = new Module();
        module1.setName("社团活动");
        module1.setSimpleName("社团");
        module1.save();
        Module module2 = new Module();
        module2.setName("高校讲座");
        module2.setSimpleName("讲座");
        module2.save();
        Module module3 = new Module();
        module3.setName("体育活动");
        module3.setSimpleName("体育");
        module3.save();
        Module module4 = new Module();
        module4.setName("校园生活");
        module4.setSimpleName("生活");
        module4.save();
        Module module5 = new Module();
        module5.setName("其他活动");
        module5.setSimpleName("其他");
        module5.save();
    }

    private void insertUserData() {
        User user = new User();
        user.setIconUrl("https://www.baidu.com");
        user.setNickName("linstick");
        user.setRealName("罗瑞泳");
        user.setAge(23);
        user.setMale(true);
        user.setCellNumber("15102032936");
        user.setEmail("linstick@163.com");
        user.setAddress("广东省广州市番禺区小谷围街道广东工业大学西生活区");
        user.setUniversity("广东工业大学");
        user.setDepartment("计算机学院");
        user.setMajor("软件工程");
        user.setKlazz("15级");
        user.setDescription("Android应用开发路人Coder");
        user.setRegisterTime(new Date());
        user.setPassword("lihongshi7610131");
        user.save();

        User user1 = new User();
        user1.setIconUrl("https://www.sina.com");
        user1.setNickName("PSC");
        user1.setRealName("罗明浩");
        user1.setAge(21);
        user1.setMale(true);
        user1.setCellNumber("13434595667");
        user1.setEmail("psc@163.com");
        user1.setAddress("广东省广州市番禺区小谷围街道华南理工大学北苑10栋");
        user1.setUniversity("华南理工大学");
        user1.setDepartment("计算机学院");
        user1.setMajor("网络工程");
        user1.setKlazz("17级");
        user1.setDescription("Java后台开发");
        user1.setRegisterTime(new Date());
        user1.setPassword("PSC213903");
        user1.save();

        User user2 = new User();
        user2.setIconUrl("https://www.tencent.com");
        user2.setNickName("小花");
        user2.setRealName("甄子明");
        user2.setAge(32);
        user2.setMale(true);
        user2.setCellNumber("15763426678");
        user2.setEmail("zzm@163.com");
        user2.setAddress("广东省广州市天河区黄村大道");
        user2.setUniversity("中山大学");
        user2.setDepartment("软件学院");
        user2.setMajor("软件工程");
        user2.setKlazz("12级");
        user2.setDescription("C++游戏开发");
        user2.setRegisterTime(new Date());
        user2.setPassword("PSC213903");
        user2.save();

        User user3 = new User();
        user3.setIconUrl("https://www.alibaba.com");
        user3.setNickName("路人甲");
        user3.setRealName("周洁莹");
        user3.setAge(29);
        user3.setMale(false);
        user3.setCellNumber("18132409834");
        user3.setEmail("admin");
        user3.setAddress("广东省广州市天河区黄村大道");
        user3.setUniversity("广州大学");
        user3.setDepartment("计算甲学院");
        user3.setMajor("计算机科学与技术");
        user3.setKlazz("15级");
        user3.setDescription("IOS开发");
        user3.setRegisterTime(new Date());
        user3.setPassword("admin");
        user3.save();
    }

    private void insertNoteData() {
        Note note = new Note();
        note.setTitle("2018 BIGO秋招宣讲会");
        note.setContent("2018 BIGO秋招已正式启动,广州站线下宣讲+笔试");
        note.setAddress("广东工业大学(大学城校区) 教5-203");
        note.setBelongModuleId(1);
        note.setPublisherId(1);
        note.setPublishTime(new Date());
        note.setStartTime(new Date());
        note.setKeepTime("2小时");
        note.setLikeCount(0);
        note.setRemarks("带上1支笔和3份简历");
        note.save();
        Note note1 = new Note();
        note1.setTitle("2018 金山WPS秋招宣讲会");
        note1.setContent("2018 金山WPS秋招已正式启动,广州站线下宣讲+笔试");
        note1.setAddress("广东工业大学(大学城校区) 教5-103");
        note1.setBelongModuleId(1);
        note1.setPublisherId(2);
        note1.setPublishTime(new Date());
        note1.setStartTime(new Date());
        note1.setKeepTime("2小时");
        note1.setLikeCount(0);
        note1.setRemarks("带上1支笔和1份简历");
        note1.save();

        Note note2 = new Note();
        note2.setTitle("触宝秋招宣讲会");
        note2.setContent("触宝秋招已正式启动,广州站线下宣讲+笔试");
        note2.setAddress("广州大学(大学城校区) 教5-103");
        note2.setBelongModuleId(1);
        note2.setPublisherId(2);
        note2.setPublishTime(new Date());
        note2.setStartTime(new Date());
        note2.setKeepTime("2小时");
        note2.setRemarks("带上1支笔和1份简历");
        note2.save();

        Note note3 = new Note();
        note3.setTitle("腾讯秋招宣讲会");
        note3.setContent("腾讯秋招已正式启动,深圳站线下宣讲+笔试");
        note3.setAddress("中山大学(大学城校区) 教5-103");
        note3.setBelongModuleId(1);
        note3.setPublisherId(1);
        note3.setPublishTime(new Date());
        note3.setStartTime(new Date());
        note3.setKeepTime("2.5小时");
        note3.setRemarks("带上1支笔和1份简历");
        note3.save();

        Note note4 = new Note();
        note4.setTitle("阿里巴巴秋招宣讲会");
        note4.setContent("阿里巴巴秋招已正式启动,杭州线下宣讲+笔试，欢迎有兴趣的2019届学生可到现场参加");
        note4.setAddress("浙江大学 教3-209");
        note4.setBelongModuleId(1);
        note4.setPublisherId(1);
        note4.setPublishTime(new Date());
        note4.setStartTime(new Date());
        note4.setKeepTime("3小时");
        note4.setRemarks("带上1支笔和2份简历");
        note4.save();

        Note note5 = new Note();
        note5.setTitle("百度秋招宣讲会");
        note5.setContent("百度秋招已正式启动,杭州线下宣讲+笔试");
        note5.setAddress("华南理工大学(大学城校区) 教1-402");
        note5.setBelongModuleId(1);
        note5.setPublisherId(1);
        note5.setPublishTime(new Date());
        note5.setStartTime(new Date());
        note5.setKeepTime("3小时");
        note5.setRemarks("带上1支笔和2份简历");
        note5.save();
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
