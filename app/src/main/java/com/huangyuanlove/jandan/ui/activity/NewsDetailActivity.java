package com.huangyuanlove.jandan.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bumptech.glide.Glide;
import com.huangyuanlove.jandan.R;
import com.huangyuanlove.jandan.app.MyApplication;
import com.huangyuanlove.jandan.bean.NewsDetailBean;
import com.huangyuanlove.jandan.bean.NewsVO;
import com.huangyuanlove.jandan.databinding.NewsDetailActivityBinding;
import com.huangyuanlove.jandan.httpservice.NewsInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HuangYuan on 2017/8/22.
 */

public class NewsDetailActivity extends Activity implements View.OnClickListener {
    NewsDetailActivityBinding binding;
    private NewsInterface newsService;
    private NewsVO newsVO;
private int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.news_detail_activity);

        WebSettings webSettings = binding.htmlContent.getSettings();
        webSettings.setJavaScriptEnabled(true);
        binding.htmlContent.setWebChromeClient(new WebClient());
        newsVO = getIntent().getParcelableExtra("newsVO");
        newsService = MyApplication.retrofit.create(NewsInterface.class);
        initView();
        initData();
    }

    private void initView() {
        if(newsVO!=null){
            id = newsVO.getId();
            binding.author.setText(newsVO.getAuthor().getName());
            binding.time.setText(newsVO.getDate());
            binding.summary.setText(newsVO.getExcerpt());
            binding.newsTitle.setText(newsVO.getTitle());
            String imgUrl="" ;
            if(newsVO.getCustom_fields()!=null && newsVO.getCustom_fields().getThumb_c()!= null&& newsVO.getCustom_fields().getThumb_c().size()>0)
            {
                imgUrl = newsVO.getCustom_fields().getThumb_c().get(0);
            }
            Glide.with(this)

                    .load(imgUrl)
                    .placeholder(R.drawable.image_placeholder)
                    .animate(android.R.anim.slide_in_left)
                    .thumbnail(0.1f)
                    .error(R.drawable.image_placeholder)
                    .into(binding.newsImg);
        }

        binding.back.setOnClickListener(this);

    }

    private void initData() {

        Call<NewsDetailBean> call = newsService.getNewsDetail(id);
        call.enqueue(new Callback<NewsDetailBean>() {
            @Override
            public void onResponse(@Nullable Call<NewsDetailBean> call, @Nullable Response<NewsDetailBean> response) {
                if (response.body() != null && "ok".equals(response.body().getStatus())) {
                    binding.htmlContent.loadDataWithBaseURL(null,response.body().getPost().getContent(),"text/html","utf-8",null);
                }
            }

            @Override
            public void onFailure(@Nullable Call<NewsDetailBean> call, @Nullable Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(binding.htmlContent.canGoBack()){
            binding.htmlContent.goBack();
        }else{
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.comment:
                Snackbar.make(v,"评论",Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.share:
                Snackbar.make(v,"分享",Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

    private class WebClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if(newProgress == 100){
                view.loadUrl("javascript:(function(){" +
                        "var objs = document.getElementsByTagName('img'); " +
                        "for(var i=0;i<objs.length;i++)  " +
                        "{"
                        + "var img = objs[i];   " +
                        "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                        "}" +
                        "})()");
            }
        }
    }


    private  class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.loadUrl("javascript:(function(){" +
                    "var objs = document.getElementsByTagName('img'); " +
                    "for(var i=0;i<objs.length;i++)  " +
                    "{"
                    + "var img = objs[i];   " +
                    "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                    "}" +
                    "})()");
        }
    }


}
