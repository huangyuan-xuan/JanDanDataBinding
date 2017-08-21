package com.huangyuanlove.jandan.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.huangyuanlove.jandan.R;
import com.huangyuanlove.jandan.databinding.LicenceActivityBinding;

/**
 * Created by HuangYuan on 2017/8/18.
 */

public class LicenceActivity extends AppCompatActivity {
    LicenceActivityBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.licence_activity);
        initView();
    }

    private void initView() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
        binding.toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        binding.toolbar.setTitle("开放源代码许可");
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
}
