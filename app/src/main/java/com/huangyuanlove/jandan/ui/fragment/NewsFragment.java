package com.huangyuanlove.jandan.ui.fragment;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.huangyuanlove.jandan.R;
import com.huangyuanlove.jandan.app.MyApplication;
import com.huangyuanlove.jandan.bean.NewsVO;
import com.huangyuanlove.jandan.bean.RequestResultBean;
import com.huangyuanlove.jandan.databinding.NewsFragmentBinding;
import com.huangyuanlove.jandan.httpservice.NewsInterface;
import com.huangyuanlove.jandan.ui.RecyclerViewScrollListener;
import com.huangyuanlove.jandan.ui.adapter.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HuangYuan on 2017/8/14.
 */

public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Activity context;
    private NewsFragmentBinding binding;
    private List<NewsVO> newsVOs = new ArrayList<>();
    private NewsAdapter adapter;
    private NewsInterface newsService;
    private int pageNum = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.news_fragment, container, false);
        newsService = MyApplication.retrofit.create(NewsInterface.class);
        initView();
        initData(false);
        return binding.getRoot();
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        binding.newsListView.setLayoutManager(linearLayoutManager);
        adapter = new NewsAdapter(context, newsVOs);
        binding.newsListView.setAdapter(adapter);
        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.newsListView.setAdapter(adapter);
        binding.newsListView.setItemAnimator(new DefaultItemAnimator());
        binding.newsListView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        binding.newsListView.addOnScrollListener(new RecyclerViewScrollListener(linearLayoutManager) {
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

        Call<RequestResultBean<NewsVO>> call = newsService.getNews(pageNum);
        call.enqueue(new Callback<RequestResultBean<NewsVO>>() {
            @Override
            public void onResponse(@Nullable Call<RequestResultBean<NewsVO>> call,@Nullable Response<RequestResultBean<NewsVO>> response) {
                if(response.body()!=null &&"ok".equals(response.body().getStatus()) ){
                    if(isLoadMore){
                        newsVOs.addAll(response.body().getPosts());
                    }else{
                        newsVOs = response.body().getPosts();
                    }
                    adapter.setLists(newsVOs);
                    binding.swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<RequestResultBean<NewsVO>> call, Throwable t) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public void onRefresh() {
        initData(false);
    }


}
