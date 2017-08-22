package com.huangyuanlove.jandan.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsVO implements Parcelable {
    private int id;
    private String title;
    private String excerpt;
    private AuthorVO author;
    private CustomFields_Thumb_cVO custom_fields;
    private int comment_count;
    private String  date;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public AuthorVO getAuthor() {
        return author;
    }

    public void setAuthorVO(AuthorVO author) {
        this.author = author;
    }

    public CustomFields_Thumb_cVO getCustom_fields() {
        return custom_fields;
    }

    public void setCustom_fields(CustomFields_Thumb_cVO custom_fields) {
        this.custom_fields = custom_fields;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.excerpt);
        dest.writeParcelable(this.author, flags);
        dest.writeParcelable(this.custom_fields, flags);
        dest.writeInt(this.comment_count);
        dest.writeString(this.date);
    }

    public NewsVO() {
    }

    protected NewsVO(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.excerpt = in.readString();
        this.author = in.readParcelable(AuthorVO.class.getClassLoader());
        this.custom_fields = in.readParcelable(CustomFields_Thumb_cVO.class.getClassLoader());
        this.comment_count = in.readInt();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<NewsVO> CREATOR = new Parcelable.Creator<NewsVO>() {
        @Override
        public NewsVO createFromParcel(Parcel source) {
            return new NewsVO(source);
        }

        @Override
        public NewsVO[] newArray(int size) {
            return new NewsVO[size];
        }
    };
}
