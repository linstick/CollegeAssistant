package com.linstick.collegeassistant.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.adapters.ViewPagerAdapter;
import com.linstick.collegeassistant.base.BaseFragment;
import com.linstick.collegeassistant.fragments.AllNotesFragment;
import com.linstick.collegeassistant.fragments.CampusTalkFragment;
import com.linstick.collegeassistant.fragments.ClubNoteFragment;
import com.linstick.collegeassistant.fragments.LectureNoteFragment;
import com.linstick.collegeassistant.fragments.LifeNoteFragment;
import com.linstick.collegeassistant.fragments.OtherNoteFragment;
import com.linstick.collegeassistant.fragments.SportNoteFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
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

    private List<BaseFragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white);
        }
        sideNavigationBar.setNavigationItemSelectedListener(this);

        initFragment();
    }

    private void initFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new AllNotesFragment());
        mFragmentList.add(new ClubNoteFragment());
        mFragmentList.add(new LectureNoteFragment());
        mFragmentList.add(new CampusTalkFragment());
        mFragmentList.add(new SportNoteFragment());
        mFragmentList.add(new LifeNoteFragment());
        mFragmentList.add(new OtherNoteFragment());
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
                startActivity(new Intent(MainActivity.this, EditActivity.class));
                break;

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_my_notes:
                Toast.makeText(MainActivity.this, "我的帖子", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_my_related:
                Toast.makeText(MainActivity.this, "与我相关", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_my_collections:
                Toast.makeText(MainActivity.this, "我的收藏", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_feedback:
                Toast.makeText(MainActivity.this, "用户反馈", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_give_praise:
                Toast.makeText(MainActivity.this, "给个好评", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_about_us:
                Toast.makeText(MainActivity.this, "关于我们", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_log_out:
                Toast.makeText(MainActivity.this, "退出登录", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
