package com.huangyuanlove.jandan.ui.fragment;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.huangyuanlove.jandan.R;
import com.huangyuanlove.jandan.app.MyApplication;
import com.huangyuanlove.jandan.bean.JokeVO;
import com.huangyuanlove.jandan.bean.RequestResultBean;
import com.huangyuanlove.jandan.databinding.JokeFragmentBinding;
import com.huangyuanlove.jandan.httpservice.JokeInterface;
import com.huangyuanlove.jandan.ui.RecyclerViewScrollListener;
import com.huangyuanlove.jandan.ui.adapter.JokesAdapter;

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(adapter);
        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.recyclerView.addOnScrollListener(new RecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                initData(true);
            }
        });

    }

    @Override
    public void onRefresh() {
        initData(false);
    }
}
