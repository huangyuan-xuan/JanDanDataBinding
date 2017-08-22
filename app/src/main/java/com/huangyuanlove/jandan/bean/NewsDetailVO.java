package com.huangyuanlove.jandan.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HuangYuan on 2017/8/22.
 */

public class NewsDetailVO implements Parcelable {
    private int id;
    private String content;
    private String date;
    private String modified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.content);
        dest.writeString(this.date);
        dest.writeString(this.modified);
    }

    public NewsDetailVO() {
    }

    protected NewsDetailVO(Parcel in) {
        this.id = in.readInt();
        this.content = in.readString();
        this.date = in.readString();
        this.modified = in.readString();
    }

    public static final Parcelable.Creator<NewsDetailVO> CREATOR = new Parcelable.Creator<NewsDetailVO>() {
        @Override
        public NewsDetailVO createFromParcel(Parcel source) {
            return new NewsDetailVO(source);
        }

        @Override
        public NewsDetailVO[] newArray(int size) {
            return new NewsDetailVO[size];
        }
    };
}
