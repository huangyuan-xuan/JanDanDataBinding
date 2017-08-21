package com.huangyuanlove.jandan.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.huangyuanlove.jandan.BuildConfig;
import com.huangyuanlove.jandan.R;
import com.huangyuanlove.jandan.app.EventMessage;
import com.huangyuanlove.jandan.databinding.ActivitySettingBinding;
import com.huangyuanlove.jandan.databinding.SetUserInfoBinding;

import org.greenrobot.eventbus.EventBus;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySettingBinding binding;
    private SetUserInfoBinding userInfoBinding;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        initView();
    }


    private void initView() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        binding.toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        binding.versionName.setText(BuildConfig.VERSION_NAME);
        binding.aboutJandan.setOnClickListener(this);
        binding.aboutVersion.setOnClickListener(this);
        binding.tourist.setOnClickListener(this);
        userInfoBinding = DataBindingUtil.inflate(LayoutInflater.from(this),R.layout.set_user_info,null,false);
        alertDialog = new AlertDialog.Builder(this).setView(userInfoBinding.getRoot()).create();
        userInfoBinding.cancel.setOnClickListener(this);
        userInfoBinding.confirm.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_jandan:
                startActivity(new Intent(this, AboutJanDanActivity.class));
                break;
            case R.id.about_version:
                break;
            case R.id.tourist:
                alertDialog.show();
                break;
            case R.id.cancel:
                alertDialog.dismiss();
                break;
            case R.id.confirm:
                String userName = userInfoBinding.username.getText().toString().trim();
                if(TextUtils.isEmpty(userName)){
                    userInfoBinding.usernameWrapper.setError("用户名不可为空");
                    break;
                }
                String email = userInfoBinding.email.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    userInfoBinding.emailWrapper.setError("邮箱不可为空");
                    break;
                }
                break;
        }
    }
}
