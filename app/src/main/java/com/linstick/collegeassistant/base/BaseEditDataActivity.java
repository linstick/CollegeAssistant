package com.linstick.collegeassistant.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;

import com.linstick.collegeassistant.R;

public abstract class BaseEditDataActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_data, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                showWarning();
                break;
            case R.id.menu_finish:
                checkAndCommitData();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        showWarning();
    }

    protected void showWarning() {
        new AlertDialog.Builder(BaseEditDataActivity.this)
                .setTitle("温馨提示")
                .setMessage("当前页面的数据还未提交保存，您确定要离开吗？")
                .setPositiveButton("离开", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BaseEditDataActivity.this.finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()
                .show();
    }


    public abstract void checkAndCommitData();
}
