package com.huangyuanlove.jandan.httpservice;

import com.huangyuanlove.jandan.bean.NewsDetailBean;
import com.huangyuanlove.jandan.bean.NewsVO;
import com.huangyuanlove.jandan.bean.RequestResultBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsInterface {
    @GET("?oxwlxojflwblxbsapi=get_recent_posts&include=url,date,tags,author,title,excerpt,comment_count,comment_status,custom_fields&custom_fields=thumb_c,views&dev=1")
    Call<RequestResultBean<NewsVO>> getNews(@Query("page") int pageNumber);

    @GET("?oxwlxojflwblxbsapi=get_post&include=content,date,modified")
    Call<NewsDetailBean> getNewsDetail(@Query("id") int id);
}
