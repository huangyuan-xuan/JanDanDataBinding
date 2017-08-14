package com.huangyuanlove.jandan.ui;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.huangyuanlove.jandan.R;
import com.huangyuanlove.jandan.databinding.MainActivityBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;
    private List<String> tabNames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.main_activity);
        tabNames.add("新鲜事");
        tabNames.add("无聊图");
        tabNames.add("段子");
        initView();
    }

    private void initView() {
        setSupportActionBar(binding.toolbar);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new NewsFragment());
        fragmentList.add(new PicsFragment());
        fragmentList.add(new JokeFragment());
        binding.includeMain.viewPager.setOffscreenPageLimit(2);
        binding.includeMain.viewPager.setAdapter(new ContentPagerAdapter(getSupportFragmentManager(),fragmentList,tabNames));
        binding.tabLayout.setupWithViewPager(binding.includeMain.viewPager);
    }
}
