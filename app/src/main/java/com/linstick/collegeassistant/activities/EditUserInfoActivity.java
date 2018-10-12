package com.linstick.collegeassistant.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.linstick.collegeassistant.App;
import com.linstick.collegeassistant.R;
import com.linstick.collegeassistant.base.BaseEditDataActivity;
import com.linstick.collegeassistant.beans.User;
import com.linstick.collegeassistant.utils.TimeFactoryUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditUserInfoActivity extends BaseEditDataActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_user_icon)
    ImageView userIconIv;
    @BindView(R.id.et_nickname_input)
    EditText nicknameInputEt;
    @BindView(R.id.et_read_name_input)
    EditText realNameInputEt;
    @BindView(R.id.rg_gender)
    RadioGroup genderRg;
    @BindView(R.id.rb_male)
    RadioButton maleRb;
    @BindView(R.id.rb_female)
    RadioButton femaleRb;
    @BindView(R.id.et_age_input)
    EditText ageInputEt;
    @BindView(R.id.et_cell_number_input)
    EditText cellNumberInputEt;
    @BindView(R.id.et_address_input)
    EditText addressInputEt;
    @BindView(R.id.et_university_input)
    EditText universityInputEt;
    @BindView(R.id.et_department_input)
    EditText departmaneInputEt;
    @BindView(R.id.et_major_input)
    EditText majorInputEt;
    @BindView(R.id.et_klazz_input)
    EditText klazzInputEt;
    @BindView(R.id.et_description_input)
    EditText descriptionInputEt;
    @BindView(R.id.tv_register_time)
    TextView registerTimeTv;
    private User mUser;

    @OnClick({R.id.iv_user_icon})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_icon:
                Toast.makeText(EditUserInfoActivity.this, "目前暂不支持更换头像功能", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_user_info);
        ButterKnife.bind(this);

        super.toolbar = toolbar;
        super.onCreate(savedInstanceState);


        initData();
    }

    private void initData() {
        mUser = App.getUser();
        if (mUser == null) {
            return;
        }
        userIconIv.setImageResource(R.drawable.bg_setting_header);
        nicknameInputEt.setText(mUser.getNickName());
        realNameInputEt.setText(mUser.getRealName());
        if (mUser.isMale()) {
            maleRb.setChecked(true);
        } else {
            femaleRb.setChecked(true);
        }
        ageInputEt.setText(mUser.getAge() + "");
        cellNumberInputEt.setText(mUser.getCellNumber());
        addressInputEt.setText(mUser.getAddress());
        universityInputEt.setText(mUser.getUniversity());
        departmaneInputEt.setText(mUser.getDepartment());
        majorInputEt.setText(mUser.getMajor());
        descriptionInputEt.setText(mUser.getDescription());
        registerTimeTv.setText(TimeFactoryUtil.dateToStringFormat((mUser.getRegisterTime())));
        majorInputEt.setText(mUser.getMajor());
    }

    @Override
    public void checkAndCommitData() {
        User newUser = new User();

        newUser.setNickName(nicknameInputEt.getText().toString().trim());
        newUser.setRealName(realNameInputEt.getText().toString().trim());
        newUser.setMale(maleRb.isChecked());
        newUser.setCellNumber(cellNumberInputEt.getText().toString().trim());
        newUser.setAddress(addressInputEt.getText().toString().trim());
        newUser.setUniversity(universityInputEt.getText().toString().trim());
        newUser.setDepartment(departmaneInputEt.getText().toString().trim());
        newUser.setMajor(majorInputEt.getText().toString().trim());
        newUser.setKlazz(klazzInputEt.getText().toString().trim());
        newUser.setDescription(descriptionInputEt.getText().toString().trim());
        newUser.setRegisterTime(mUser.getRegisterTime());
        // 除了昵称和年龄不能为空之外，其他信息都可以为空
        if (TextUtils.isEmpty(newUser.getNickName())) {
            Toast.makeText(EditUserInfoActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String ageStr = ageInputEt.getText().toString().trim();
        if (TextUtils.isEmpty(ageStr)) {
            Toast.makeText(EditUserInfoActivity.this, "年龄不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        newUser.setAge(Integer.parseInt(ageStr));


        // 修改操作

        App.setUser(newUser);
        Toast.makeText(EditUserInfoActivity.this, "修改资料成功", Toast.LENGTH_SHORT).show();
        EditUserInfoActivity.this.finish();
    }
}
