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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.huangyuanlove.jandan.R;
import com.huangyuanlove.jandan.app.MyApplication;
import com.huangyuanlove.jandan.bean.PicsVO;
import com.huangyuanlove.jandan.bean.RequestResultBean;
import com.huangyuanlove.jandan.databinding.PicsFragmentBinding;
import com.huangyuanlove.jandan.httpservice.PicsInterface;
import com.huangyuanlove.jandan.ui.RecyclerViewScrollListener;
import com.huangyuanlove.jandan.ui.adapter.PicsAdapter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PicsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, PicsAdapter.OnItemClick {

    private Activity context;
    private PicsFragmentBinding binding;
    private int pageNum = 1;
    private PicsInterface picService;
    private List<PicsVO> picsVOs = new ArrayList<>();
    private PicsAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.pics_fragment,container,false);
        initView();
        picService = MyApplication.retrofit.create(PicsInterface.class);
        initData(false);
        return binding.getRoot();
    }

    private void initView() {
        binding.swipeRefreshLayout.setOnRefreshListener(this);
        adapter = new PicsAdapter(context,picsVOs);
        adapter.setOnItemClick(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        binding.picsListView.setLayoutManager(linearLayoutManager);
        binding.picsListView.setAdapter(adapter);
        binding.picsListView.addOnScrollListener(new RecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                initData(true);
            }
        });

    }
    private void initData(final boolean isLoadMore) {
        if(isLoadMore){
            pageNum += 1;
        }else{
            pageNum = 1;
        }
        Call<RequestResultBean<PicsVO>> call = picService.getPics(pageNum);
        call.enqueue(new Callback<RequestResultBean<PicsVO>>() {
            @Override
            public void onResponse( @Nullable Call<RequestResultBean<PicsVO>> call, @Nullable Response<RequestResultBean<PicsVO>> response) {
                if(response.body()!=null && "ok".equals(response.body().getStatus())){
                    if(isLoadMore){
                        picsVOs.addAll(response.body().getComments());
                    }else{
                        picsVOs = response.body().getComments();
                    }
                    adapter.setLists(picsVOs);
                    binding.swipeRefreshLayout.setRefreshing(false);

                }
            }
            @Override
            public void onFailure(Call<RequestResultBean<PicsVO>> call, Throwable t) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        initData(false);
    }

    @Override
    public void onClick(View v, int position) {
        switch (v.getId()){
            case R.id.oo:
                picService.votePic(1,picsVOs.get(position).getComment_ID()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                break;
            case R.id.xx:
                picService.votePic(0,picsVOs.get(position).getComment_ID()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                break;
        }
    }
}
