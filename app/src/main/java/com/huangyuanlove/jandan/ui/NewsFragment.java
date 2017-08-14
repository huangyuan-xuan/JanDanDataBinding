package com.huangyuanlove.jandan.ui;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
    private boolean isBottom;
    private int totalCount;

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
        adapter = new NewsAdapter(context, newsVOs);
        binding.newsListView.setAdapter(adapter);
        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.newsListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE && isBottom && newsVOs.size() < totalCount) {
                    initData(true);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isBottom = firstVisibleItem + visibleItemCount==totalItemCount;
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
                        totalCount= response.body().getPage_count();
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
