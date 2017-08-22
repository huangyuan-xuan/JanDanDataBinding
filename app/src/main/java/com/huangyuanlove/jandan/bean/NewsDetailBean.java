package com.huangyuanlove.jandan.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HuangYuan on 2017/8/22.
 */

public class NewsDetailBean implements Parcelable {
    private String status;
    private NewsDetailVO post;
    private String previous_url;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NewsDetailVO getPost() {
        return post;
    }

    public void setPost(NewsDetailVO post) {
        this.post = post;
    }

    public String getPrevious_url() {
        return previous_url;
    }

    public void setPrevious_url(String previous_url) {
        this.previous_url = previous_url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeParcelable(this.post, flags);
        dest.writeString(this.previous_url);
    }

    public NewsDetailBean() {
    }

    protected NewsDetailBean(Parcel in) {
        this.status = in.readString();
        this.post = in.readParcelable(NewsDetailVO.class.getClassLoader());
        this.previous_url = in.readString();
    }

    public static final Parcelable.Creator<NewsDetailBean> CREATOR = new Parcelable.Creator<NewsDetailBean>() {
        @Override
        public NewsDetailBean createFromParcel(Parcel source) {
            return new NewsDetailBean(source);
        }

        @Override
        public NewsDetailBean[] newArray(int size) {
            return new NewsDetailBean[size];
        }
    };
}
