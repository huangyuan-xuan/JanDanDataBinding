package com.huangyuanlove.jandan.httpservice;


import com.huangyuanlove.jandan.bean.JokeVO;
import com.huangyuanlove.jandan.bean.RequestResultBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface JokeInterface {
    @GET("?oxwlxojflwblxbsapi=jandan.get_duan_comments")
    Call<RequestResultBean<JokeVO>> getJokes(@Query("page") int pageNumber);

}
