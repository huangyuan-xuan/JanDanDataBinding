package com.huangyuanlove.jandan.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import com.huangyuanlove.jandan.BuildConfig;
import com.huangyuanlove.jandan.R;
import com.huangyuanlove.jandan.app.EventMessage;
import com.huangyuanlove.jandan.databinding.ActivitySettingBinding;

import org.greenrobot.eventbus.EventBus;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySettingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_setting);
        initView();
    }

    private void initView() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
        binding.toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        binding.versionName.setText(BuildConfig.VERSION_NAME);
        binding.aboutJandan.setOnClickListener(this);
        binding.aboutVersion.setOnClickListener(this);
       binding.tourist.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.about_jandan:
                startActivity(new Intent(this,AboutJanDanActivity.class));
                break;
            case R.id.about_version:
                break;
            case R.id.tourist:
                break;
        }
    }
}
