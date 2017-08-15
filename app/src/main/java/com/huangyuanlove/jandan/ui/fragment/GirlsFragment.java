package com.huangyuanlove.jandan.ui.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huangyuanlove.jandan.R;
import com.huangyuanlove.jandan.app.MyApplication;
import com.huangyuanlove.jandan.bean.GirlPicsVO;
import com.huangyuanlove.jandan.bean.RequestResultBean;
import com.huangyuanlove.jandan.databinding.GirlsFragmentBinding;
import com.huangyuanlove.jandan.httpservice.GirlPicsInterface;
import com.huangyuanlove.jandan.ui.RecyclerViewScrollListener;
import com.huangyuanlove.jandan.ui.adapter.GirlPicsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huangyuan on 2017/8/15.
 */

public class GirlsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Activity context;
    private GirlsFragmentBinding binding;
    private GirlPicsInterface girlPicsService;
    private List<GirlPicsVO> girlPicsVOs = new ArrayList<>();
    private GirlPicsAdapter adapter;
    private int pageNum = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.girls_fragment, container, false);
        girlPicsService = MyApplication.retrofit.create(GirlPicsInterface.class);
        initView();
        initData(false);
        return binding.getRoot();
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new GirlPicsAdapter(context, girlPicsVOs);
        binding.recyclerView.setAdapter(adapter);
        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.recyclerView.addOnScrollListener(new RecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                initData(true);
            }
        });
    }

    private void initData(final boolean isLoadMore) {
        if (isLoadMore) {
            pageNum += 1;
        } else {
            pageNum = 1;
        }
        Call<RequestResultBean<GirlPicsVO>> call = girlPicsService.getGirlPics(pageNum);
        call.enqueue(new Callback<RequestResultBean<GirlPicsVO>>() {
            @Override
            public void onResponse(@Nullable Call<RequestResultBean<GirlPicsVO>> call, @Nullable Response<RequestResultBean<GirlPicsVO>> response) {
                if (response.body() != null && "ok".equals(response.body().getStatus())) {
                    if (isLoadMore) {
                        girlPicsVOs.addAll(response.body().getComments());
                    } else {
                        girlPicsVOs = response.body().getComments();
                    }
                    adapter.setLists(girlPicsVOs);
                    binding.swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(@Nullable Call<RequestResultBean<GirlPicsVO>> call, @Nullable Throwable t) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public void onRefresh() {
        initData(false);
    }
}
