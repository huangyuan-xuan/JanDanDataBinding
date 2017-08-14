package com.huangyuanlove.jandan.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.huangyuanlove.jandan.R;
import com.huangyuanlove.jandan.bean.JokeVO;
import com.huangyuanlove.jandan.databinding.ItemJokesBinding;

import java.util.List;

/**
 * Created by huangyuan on 2017/8/13.
 */

public class JokesAdapter extends BaseAdapter {

    private Context context;
    private List<JokeVO> lists;
    private LayoutInflater inflater;

    public JokesAdapter(Context context, List<JokeVO> lists) {
        this.context = context;
        this.lists = lists;
        this.inflater = LayoutInflater.from(context);
    }

    public void setLists(List<JokeVO> lists){
        this.lists = lists;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return lists==null?0:lists.size();
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
        ItemJokesBinding binding;
        if(convertView == null){
            binding= DataBindingUtil.inflate(inflater, R.layout.item_jokes,parent,false);
        }else{
            binding = DataBindingUtil.getBinding(convertView);
        }
        binding.setJoke(lists.get(position));
        return binding.getRoot();
    }
}
