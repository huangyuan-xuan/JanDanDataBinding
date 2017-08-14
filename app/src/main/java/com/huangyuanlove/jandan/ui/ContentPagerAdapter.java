package com.huangyuanlove.jandan.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by HuangYuan on 2017/8/14.
 */

public class ContentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> tabNames;
    public ContentPagerAdapter(FragmentManager fm, List<Fragment> fragments,List<String> tabNames) {
        super(fm);
        this.fragments = fragments;
        this.tabNames = tabNames;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames == null ?"":tabNames.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments==null?0:fragments.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
