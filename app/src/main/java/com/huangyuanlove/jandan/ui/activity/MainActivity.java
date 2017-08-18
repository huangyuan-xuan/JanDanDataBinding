package com.huangyuanlove.jandan.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.huangyuanlove.jandan.R;
import com.huangyuanlove.jandan.app.EventMessage;
import com.huangyuanlove.jandan.databinding.MainActivityBinding;
import com.huangyuanlove.jandan.ui.fragment.GirlsFragment;
import com.huangyuanlove.jandan.ui.fragment.JokeFragment;
import com.huangyuanlove.jandan.ui.fragment.NewsFragment;
import com.huangyuanlove.jandan.ui.adapter.ContentPagerAdapter;
import com.huangyuanlove.jandan.ui.fragment.PicsFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Fragment> fragmentList = new ArrayList<>();
    private MainActivityBinding binding;
    private List<String> tabNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        tabNames.add("新鲜事");
        tabNames.add("无聊图");
        tabNames.add("妹子图");
        tabNames.add("段子");
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        fragmentList.add(new NewsFragment());
        fragmentList.add(new PicsFragment());
        fragmentList.add(new GirlsFragment());
        fragmentList.add(new JokeFragment());
        binding.viewPager.setOffscreenPageLimit(2);
        binding.viewPager.setAdapter(new ContentPagerAdapter(getSupportFragmentManager(), fragmentList, tabNames));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventMessage event) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
