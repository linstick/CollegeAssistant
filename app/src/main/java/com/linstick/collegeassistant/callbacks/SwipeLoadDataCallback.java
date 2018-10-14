package com.linstick.collegeassistant.callbacks;

import java.util.List;

public interface SwipeLoadDataCallback<T> {
    void onRefreshCallback(List<T> result);

    void onLoadMoreCallback(List<T> result);
}
