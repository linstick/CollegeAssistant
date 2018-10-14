package com.linstick.collegeassistant.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.base.BaseActivity;
import com.linstick.collegeassistant.fragments.CollectedNotesSwipeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionsActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private CollectedNotesSwipeFragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        super.toolbar = toolbar;
        super.onCreate(savedInstanceState);

        mFragment = new CollectedNotesSwipeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mFragment).commit();
    }
}
