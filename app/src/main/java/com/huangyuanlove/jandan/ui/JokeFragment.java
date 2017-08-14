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
import com.huangyuanlove.jandan.bean.JokeVO;
import com.huangyuanlove.jandan.bean.RequestResultBean;
import com.huangyuanlove.jandan.databinding.JokeFragmentBinding;
import com.huangyuanlove.jandan.httpservice.JokeInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JokeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Activity context;
    private JokeFragmentBinding binding;
    private JokeInterface jokeService;
    private List<JokeVO> jokeVOs = new ArrayList<>();
    private JokesAdapter adapter;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.joke_fragment, container, false);
        initView();
        jokeService = MyApplication.retrofit.create(JokeInterface.class);
        initData(false);
        return binding.getRoot();
    }

    private void initData(final boolean isLoadMore) {
        if (isLoadMore) {
            pageNum += 1;
        } else {
            pageNum = 1;
        }
        Call<RequestResultBean<JokeVO>> call = jokeService.getJokes(pageNum);
        call.enqueue(new Callback<RequestResultBean<JokeVO>>() {
            @Override
            public void onResponse(@Nullable Call<RequestResultBean<JokeVO>> call, @Nullable Response<RequestResultBean<JokeVO>> response) {
                if (response != null && response.body() != null && "ok".equals(response.body().getStatus())) {
                    if (isLoadMore) {
                        jokeVOs.addAll(response.body().getComments());
                    } else {
                        totalCount = response.body().getTotal_comments();
                        jokeVOs = response.body().getComments();
                    }
                    adapter.setLists(jokeVOs);
                }
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<RequestResultBean<JokeVO>> call, Throwable t) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initView() {
        adapter = new JokesAdapter(context, jokeVOs);
        binding.listView.setAdapter(adapter);
        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.listView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
