package com.huangyuanlove.jandan.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.huangyuanlove.jandan.R;
import com.huangyuanlove.jandan.databinding.AboutJanDanActivityBinding;

/**
 * Created by HuangYuan on 2017/8/18.
 */

public class AboutJanDanActivity extends AppCompatActivity implements View.OnClickListener {
    private AboutJanDanActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.about_jan_dan_activity);
        initView();
    }



    private void initView() {
        binding.developer.setOnClickListener(this);
        binding.licence.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.developer:
                Uri uri = Uri.parse("http://www.huangyuanlove.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.licence:
                startActivity(new Intent(this, LicenceActivity.class));
                break;
        }
    }
}
