package com.linstick.collegeassistant.callbacks;

import java.util.List;

public interface LoadDataCallBack<T> {

    void onSuccess(List<T> list);

    void onSuccessEmpty();

    void onFail(String error);
}
