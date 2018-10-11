package com.linstick.collegeassistant.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.base.BaseActivity;
import com.linstick.collegeassistant.beans.Module;
import com.linstick.collegeassistant.beans.Note;
import com.linstick.collegeassistant.utils.TimeFactoryUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditNoteActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "EditNoteActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.ll_title_layout)
    LinearLayout titleLayout;
    @BindView(R.id.ll_start_time_layout)
    LinearLayout startTimeLayout;
    @BindView(R.id.ll_keep_time_layout)
    LinearLayout keepTimeLayout;
    @BindView(R.id.ll_address_layout)
    LinearLayout addressLayout;
    @BindView(R.id.ll_remarks_layout)
    LinearLayout remarksLayout;

    @BindView(R.id.spinner_belong_module)
    Spinner belongModuleSpinner;
    @BindView(R.id.et_title_input)
    EditText titleInputEt;
    @BindView(R.id.et_content_input)
    EditText contentInputEt;
    @BindView(R.id.et_start_time_input)
    EditText startTimeInputEt;
    @BindView(R.id.et_keep_time_input)
    EditText keepTimeInputEt;
    @BindView(R.id.et_address_input)
    EditText addressInputEt;
    @BindView(R.id.et_remarks_input)
    EditText remarksInputEt;
    private String[] mItemList;

    @OnClick({R.id.iv_send})
    public void onClick(View view) {
        int count = belongModuleSpinner.getCount();
        int position = belongModuleSpinner.getSelectedItemPosition();
        String title = titleInputEt.getText().toString().trim();
        String content = contentInputEt.getText().toString().trim();
        String startTime = startTimeInputEt.getText().toString().trim();
        String keepTime = keepTimeInputEt.getText().toString().trim();
        String address = addressInputEt.getText().toString().trim();
        String remarks = remarksInputEt.getText().toString().trim();
        if (position + 2 < count) {
            // 不是校园生活或其他活动,检查标题
            if (TextUtils.isEmpty(title)) {
                Toast.makeText(EditNoteActivity.this, "标题不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        // 检查内容
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(EditNoteActivity.this, "内容描述不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (position + 2 < count) {
            // 不是校园生活或其他活动,检查开始时间，持续时间和地点
            if (TextUtils.isEmpty(startTime)) {
                Toast.makeText(EditNoteActivity.this, "开始时间不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!TimeFactoryUtil.isVaildStringToDate(startTime)) {
                Toast.makeText(EditNoteActivity.this, "时间格式不正确", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(keepTime)) {
                Toast.makeText(EditNoteActivity.this, "持续时间不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(address)) {
                Toast.makeText(EditNoteActivity.this, "详细地址不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        // 检查通过
        Module module = new Module();
        module.setId(position + 1);
        module.setName(mItemList[position]);

        Note note = new Note();
        note.setBelongModule(module);
        note.setTitle(title);
        note.setContent(content);
        if (position + 2 < count) {
            note.setStartTime(TimeFactoryUtil.stringToDateFormat(startTime));
            note.setKeepTime(keepTime);
            note.setAddress(address);
            note.setRemarks(remarks);
        }
        Log.d(TAG, note.toString());
        Toast.makeText(EditNoteActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_note);
        ButterKnife.bind(this);

        super.toolbar = toolbar;
        super.onCreate(savedInstanceState);


        // 建立数据源
        mItemList = getResources().getStringArray(R.array.belong_module);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItemList);
        belongModuleSpinner.setAdapter(adapter);
        belongModuleSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int count = adapterView.getCount();
        if (i + 2 >= count) {
            if (i + 2 == count) {
                // 校园活动
                titleLayout.setVisibility(View.GONE);
            }
            startTimeLayout.setVisibility(View.GONE);
            keepTimeLayout.setVisibility(View.GONE);
            addressLayout.setVisibility(View.GONE);
            remarksLayout.setVisibility(View.GONE);
        }
        titleInputEt.setText("");
        contentInputEt.setText("");
        startTimeInputEt.setText("");
        keepTimeInputEt.setText("");
        addressInputEt.setText("");
        remarksInputEt.setText("");
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // do nothing
    }
}
