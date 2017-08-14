package com.huangyuanlove.jandan.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.huangyuanlove.jandan.R;
import com.huangyuanlove.jandan.bean.NewsVO;
import com.huangyuanlove.jandan.databinding.ItemNewsBinding;

import java.util.List;

/**
 * Created by huangyuan on 2017/8/14.
 */

public class NewsAdapter extends BaseAdapter {
    private Context context;
    private List<NewsVO> lists;
    private LayoutInflater inflater;

    public NewsAdapter(Context context, List<NewsVO> lists) {
        this.context = context;
        this.lists = lists;
        this.inflater = LayoutInflater.from(context);
    }

    public void setLists(List<NewsVO> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lists == null?0:lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemNewsBinding binding;
        if(convertView == null){
            binding = DataBindingUtil.inflate(inflater, R.layout.item_news,parent,false);
        }else{
            binding = DataBindingUtil.getBinding(convertView);
        }
        binding.setNewsVo(lists.get(position));
        return binding.getRoot();
    }
}
