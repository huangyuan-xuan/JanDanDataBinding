package com.huangyuanlove.jandan.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;

import com.huangyuanlove.jandan.R;
import com.huangyuanlove.jandan.app.MyApplication;
import com.huangyuanlove.jandan.bean.JokeVO;
import com.huangyuanlove.jandan.bean.RequestResultBean;
import com.huangyuanlove.jandan.databinding.DemoActivtyBinding;
import com.huangyuanlove.jandan.httpservice.JokeInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DemoActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private DemoActivtyBinding binding;
    private List<JokeVO> jokeVOs = new ArrayList<>();
    private JokesAdapter adapter;
    private JokeInterface joke;
    private int pageNum = 1;
    private boolean isBottom;
    private int totalCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.demo_activty);
        joke = MyApplication.retrofit.create(JokeInterface.class);
        initView();
        initData(false);
    }

    private void initData(final boolean isLoadMore) {
        if (isLoadMore) {
            pageNum += 1;
        } else {
            pageNum = 1;
        }
        Call<RequestResultBean<JokeVO>> call = joke.getJokes(pageNum);
        call.enqueue(new Callback<RequestResultBean<JokeVO>>() {
            @Override
            public void onResponse(@Nullable Call<RequestResultBean<JokeVO>> call,@Nullable Response<RequestResultBean<JokeVO>> response) {
                if (response!=null && response.body() != null && "ok".equals(response.body().getStatus())) {
                    if (isLoadMore) {
                        jokeVOs.addAll(response.body().getComments());
                    } else {
                        totalCount = response.body().getTotal_comments();
                        jokeVOs = response.body().getComments();
                    }
                    adapter.setLists(jokeVOs);
                }
                binding.includeMain. swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<RequestResultBean<JokeVO>> call, Throwable t) {
                binding.includeMain.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initView() {
        setSupportActionBar(binding.toolbar);
        adapter = new JokesAdapter(this, jokeVOs);
        binding.includeMain.listView.setAdapter(adapter);
        binding.includeMain.swipeRefreshLayout.setOnRefreshListener(this);
        binding.includeMain.listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE && isBottom && jokeVOs.size() < totalCount) {
                    initData(true);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isBottom = firstVisibleItem + visibleItemCount == totalItemCount;
            }
        });
    }


    @Override
    public void onRefresh() {
        initData(false);
    }
}
