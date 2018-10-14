package com.linstick.collegeassistant.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.base.BaseActivity;
import com.linstick.collegeassistant.fragments.SearchNotesSwipeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity {
    public final static String KEYWORD_TAG = "KEYWORD_TAG";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private SearchNotesSwipeFragment mFragment;

    public static void startAction(Context context, String keyword) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(KEYWORD_TAG, keyword);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        super.toolbar = toolbar;
        super.onCreate(savedInstanceState);

        String keyword = getIntent().getStringExtra(KEYWORD_TAG).trim();
        getSupportActionBar().setTitle("搜索 - " + keyword);
        mFragment = new SearchNotesSwipeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEYWORD_TAG, keyword);
        mFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mFragment).commit();
    }
}
