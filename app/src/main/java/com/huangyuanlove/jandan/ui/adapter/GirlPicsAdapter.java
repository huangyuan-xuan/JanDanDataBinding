package com.huangyuanlove.jandan.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huangyuanlove.jandan.R;
import com.huangyuanlove.jandan.bean.GirlPicsVO;
import com.huangyuanlove.jandan.databinding.ItemGirlPicsBinding;

import java.util.List;

/**
 * Created by huangyuan on 2017/8/15.
 */

public class GirlPicsAdapter extends RecyclerView.Adapter<GirlPicsAdapter.ViewHolder> {
    private List<GirlPicsVO> lists;
    private LayoutInflater inflater;

    public GirlPicsAdapter(Context context, List<GirlPicsVO> lists) {
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    public void setLists(List<GirlPicsVO> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemGirlPicsBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_girl_pics,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            holder.getBinding().setGirlPicsVO(lists.get(position));
            holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return lists==null?0:lists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ItemGirlPicsBinding binding;
        public ViewHolder(ItemGirlPicsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemGirlPicsBinding getBinding() {
            return binding;
        }

        public void setBinding(ItemGirlPicsBinding binding) {
            this.binding = binding;
        }
    }

}
