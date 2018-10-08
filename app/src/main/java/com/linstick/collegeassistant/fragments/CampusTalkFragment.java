package com.linstick.collegeassistant.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.base.BaseFragment;

public class CampusTalkFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_campus_talk_note, null);
        return view;
    }

    @Override
    public String getTitle() {
        return "宣讲会";
    }
}
