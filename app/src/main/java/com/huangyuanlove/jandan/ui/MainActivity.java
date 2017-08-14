package com.huangyuanlove.jandan.ui;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.huangyuanlove.jandan.R;
import com.huangyuanlove.jandan.app.MyApplication;
import com.huangyuanlove.jandan.databinding.ActivityMainBinding;
import com.huangyuanlove.jandan.bean.JokeVO;
import com.huangyuanlove.jandan.bean.RequestResultBean;
import com.huangyuanlove.jandan.httpservice.JokeInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {

    private ActivityMainBinding binding;
    private List<JokeVO> jokeVOs = new ArrayList<>();
    private JokesAdapter adapter;
    private JokeInterface joke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        joke = MyApplication.retrofit.create(JokeInterface.class);
        initView();
        initData();
    }

    private void initData() {
        Call<RequestResultBean<JokeVO>> call = joke.getJokes(1);
        call.enqueue(new Callback<RequestResultBean<JokeVO>>() {
            @Override
            public void onResponse(Call<RequestResultBean<JokeVO>> call, Response<RequestResultBean<JokeVO>> response) {
                if (response.body() != null && "ok".equals(response.body().getStatus())) {
                    adapter.setLists(response.body().getComments());
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
        binding.includeTitleBar.titleTitle.setText("段子");
        adapter = new JokesAdapter(this, jokeVOs);
        binding.listView.setAdapter(adapter);
        binding.swipeRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    public void onRefresh() {
        initData();
    }
}
