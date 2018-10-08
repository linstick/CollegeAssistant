package com.linstick.collegeassistant.events;

public enum LoadDataEvent {
    REFRESH_SUCCESS,    // 更新请求成功，并且有数据
    REFRESH_SUCCESS_EMPTY, // 更新请求成功，但无数据
    REFRESH_FAIL,       // 更新请求失败
    LOAD_MORE_SUCCESS,  // 加载更多请求成功，并且有数据
    LOAD_MORE_SUCCESS_EMPTY, // 加载更多请求成功，但无数据
    LOAD_MORE_FAIL,     // 加载更多请求失败
}
