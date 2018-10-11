package com.linstick.collegeassistant.activities;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.linstick.collegeassistant.App;
import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.adapters.ViewPagerAdapter;
import com.linstick.collegeassistant.base.BaseActivity;
import com.linstick.collegeassistant.base.BaseSwipeNoteFragment;
import com.linstick.collegeassistant.beans.User;
import com.linstick.collegeassistant.fragments.AllNotesSwipeNoteFragment;
import com.linstick.collegeassistant.fragments.CampusTalkSwipeNoteFragment;
import com.linstick.collegeassistant.fragments.ClubNoteSwipeNoteFragment;
import com.linstick.collegeassistant.fragments.LectureNoteSwipeNoteFragment;
import com.linstick.collegeassistant.fragments.LifeNoteSwipeNoteFragment;
import com.linstick.collegeassistant.fragments.OtherNoteSwipeNoteFragment;
import com.linstick.collegeassistant.fragments.SportNoteSwipeNoteFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.side_bar)
    NavigationView sideNavigationBar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private List<BaseSwipeNoteFragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        super.toolbar = toolbar;
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white);
        }

        sideNavigationBar.setNavigationItemSelectedListener(this);
        initFragment();

    }

    private void initFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new AllNotesSwipeNoteFragment());
        mFragmentList.add(new ClubNoteSwipeNoteFragment());
        mFragmentList.add(new LectureNoteSwipeNoteFragment());
        mFragmentList.add(new CampusTalkSwipeNoteFragment());
        mFragmentList.add(new SportNoteSwipeNoteFragment());
        mFragmentList.add(new LifeNoteSwipeNoteFragment());
        mFragmentList.add(new OtherNoteSwipeNoteFragment());
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragmentList));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;

            case R.id.menu_edit:
                startActivity(new Intent(MainActivity.this, EditNoteActivity.class));
                break;

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                if (App.getUser() == null) {
                    // 未登录
                    sideNavigationBar.getMenu().findItem(R.id.menu_log_out).setVisible(false);
                    sideNavigationBar.getMenu().findItem(R.id.menu_log_in).setVisible(true);
                    sideNavigationBar.getMenu().findItem(R.id.menu_sign_in).setVisible(true);
                    ((ImageView) sideNavigationBar.getHeaderView(0).findViewById(R.id.iv_user_icon)).setImageResource(R.drawable.ic_user_white);
                    ((TextView) sideNavigationBar.getHeaderView(0).findViewById(R.id.tv_nickname)).setText("未登录");
                } else {
                    sideNavigationBar.getMenu().findItem(R.id.menu_log_in).setVisible(false);
                    sideNavigationBar.getMenu().findItem(R.id.menu_sign_in).setVisible(false);
                    sideNavigationBar.getMenu().findItem(R.id.menu_log_out).setVisible(true);
                    ((ImageView) sideNavigationBar.getHeaderView(0).findViewById(R.id.iv_user_icon)).setImageResource(R.mipmap.ic_launcher);
                    ((TextView) sideNavigationBar.getHeaderView(0).findViewById(R.id.tv_nickname)).setText(App.getUser().getNickName());
                }
                break;
        }
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_my_notes:
                startActivity(new Intent(MainActivity.this, PersonalNotesActivity.class));
                break;

            case R.id.menu_my_related:
                startActivity(new Intent(MainActivity.this, PersonalRelatedActivity.class));
                break;

            case R.id.menu_my_collections:
                startActivity(new Intent(MainActivity.this, CollectionsActivity.class));
                break;

            case R.id.menu_feedback:
                startActivity(new Intent(MainActivity.this, FeedBackActivity.class));
                break;

            case R.id.menu_give_praise:
                Toast.makeText(MainActivity.this, "该功能正在开发，敬请期待", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_about_us:
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("关于我们")
                        .setMessage("本应用由广东工业大学19届学生罗瑞泳自主开发，有什么问题或建议可发送至邮箱：linstick@163.com，感谢您的使用。")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create();
                dialog.show();

                break;

            case R.id.menu_log_out:
                Toast.makeText(MainActivity.this, "退出登录", Toast.LENGTH_SHORT).show();
                App.setUser(null);
                break;

            case R.id.menu_log_in:
                Toast.makeText(MainActivity.this, "登录", Toast.LENGTH_SHORT).show();
                App.setUser(new User());
                break;

            case R.id.menu_sign_in:
                Toast.makeText(MainActivity.this, "注册", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
