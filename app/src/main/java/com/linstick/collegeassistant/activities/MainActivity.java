package com.linstick.collegeassistant.activities;

import android.app.AlertDialog;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linstick.collegeassistant.App;
import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.adapters.ViewPagerAdapter;
import com.linstick.collegeassistant.base.BaseActivity;
import com.linstick.collegeassistant.beans.Module;
import com.linstick.collegeassistant.fragments.ModuleSwipeNoteFragment;
import com.linstick.collegeassistant.sqlite.ModuleDaoUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

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

    private List<ModuleSwipeNoteFragment> mFragmentList;
    private List<String> mTitleList;

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
        LinearLayout headerLayout = (LinearLayout) sideNavigationBar.getHeaderView(0);
        headerLayout.findViewById(R.id.iv_user_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(GravityCompat.START);
                if (App.getUser() != null) {
                    startActivity(new Intent(MainActivity.this, EditUserInfoActivity.class));
                } else {
                    LoginActivity.startAction(MainActivity.this, true);
                }
            }
        });
        initFragment();
    }

    private void initFragment() {
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mFragmentList.add(new ModuleSwipeNoteFragment());
        mTitleList.add("全部");
        List<Module> modules = ModuleDaoUtil.findModules();
        for (Module module : modules) {
            ModuleSwipeNoteFragment fragment = new ModuleSwipeNoteFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ModuleSwipeNoteFragment.MODULE_ID, module.getId());
            fragment.setArguments(bundle);
            mFragmentList.add(fragment);
            mTitleList.add(module.getSimpleName());
        }
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        final SearchView.SearchAutoComplete autoCompleteView = searchView.findViewById(R.id.search_src_text);
        autoCompleteView.setHint("搜索帖子...");
        autoCompleteView.setTextSize(17);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    autoCompleteView.setText("");
                    //利用反射调用收起SearchView的onCloseClicked()方法
                    Method method = searchView.getClass().getDeclaredMethod("onCloseClicked");
                    method.setAccessible(true);
                    method.invoke(searchView);
                    searchView.clearFocus();
                    SearchActivity.startAction(MainActivity.this, query);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_edit:
                if (App.getUser() == null) {
                    Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(MainActivity.this, EditNoteActivity.class));
                }
                break;

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                if (App.getUser() == null) {
                    // 未登录
                    sideNavigationBar.getMenu().findItem(R.id.menu_log_out).setVisible(false);
                    sideNavigationBar.getMenu().findItem(R.id.menu_modify_info).setVisible(false);
                    sideNavigationBar.getMenu().findItem(R.id.menu_modify_password).setVisible(false);
                    sideNavigationBar.getMenu().findItem(R.id.menu_log_in).setVisible(true);
                    sideNavigationBar.getMenu().findItem(R.id.menu_register).setVisible(true);
                    ((ImageView) sideNavigationBar.getHeaderView(0).findViewById(R.id.iv_user_icon)).setImageResource(R.drawable.ic_user_white);
                    ((TextView) sideNavigationBar.getHeaderView(0).findViewById(R.id.tv_nickname)).setText("未登录");
                } else {
                    sideNavigationBar.getMenu().findItem(R.id.menu_log_in).setVisible(false);
                    sideNavigationBar.getMenu().findItem(R.id.menu_register).setVisible(false);
                    sideNavigationBar.getMenu().findItem(R.id.menu_log_out).setVisible(true);
                    sideNavigationBar.getMenu().findItem(R.id.menu_modify_info).setVisible(true);
                    sideNavigationBar.getMenu().findItem(R.id.menu_modify_password).setVisible(true);
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
                if (App.getUser() == null) {
                    Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                    LoginActivity.startAction(MainActivity.this, true);
                } else {
                    startActivity(new Intent(MainActivity.this, PersonalNotesActivity.class));
                }
                break;

            case R.id.menu_my_related:
                if (App.getUser() == null) {
                    Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                    LoginActivity.startAction(MainActivity.this, true);
                } else {
                    startActivity(new Intent(MainActivity.this, PersonalRelatedActivity.class));
                }
                break;

            case R.id.menu_my_collections:
                if (App.getUser() == null) {
                    Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                    LoginActivity.startAction(MainActivity.this, true);
                } else {
                    startActivity(new Intent(MainActivity.this, CollectionsActivity.class));
                }
                break;

            case R.id.menu_modify_info:
                startActivity(new Intent(MainActivity.this, EditUserInfoActivity.class));
                break;

            case R.id.menu_modify_password:
                startActivity(new Intent(MainActivity.this, ModifyPasswordActivity.class));
                break;

            case R.id.menu_feedback:
                if (App.getUser() == null) {
                    Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                    LoginActivity.startAction(MainActivity.this, true);
                } else {
                    startActivity(new Intent(MainActivity.this, FeedBackActivity.class));
                }
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
                App.setUser(null);
                mFragmentList.get(viewPager.getCurrentItem()).reLoadData();
                Toast.makeText(MainActivity.this, "已退出登录", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_log_in:
                LoginActivity.startAction(MainActivity.this, true);
                break;

            case R.id.menu_register:
                LoginActivity.startAction(MainActivity.this, false);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
