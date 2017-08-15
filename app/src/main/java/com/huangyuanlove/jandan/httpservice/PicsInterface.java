package com.huangyuanlove.jandan.httpservice;

import com.huangyuanlove.jandan.bean.PicsVO;
import com.huangyuanlove.jandan.bean.RequestResultBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by HuangYuan on 2017/8/15.
 */

public interface PicsInterface {
    @GET("?oxwlxojflwblxbsapi=jandan.get_pic_comments")
    Call<RequestResultBean<PicsVO>> getPics(@Query("page") int page);
}
