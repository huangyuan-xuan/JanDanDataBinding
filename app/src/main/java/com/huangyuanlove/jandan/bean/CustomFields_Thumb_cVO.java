package com.huangyuanlove.jandan.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by HuangYuan on 2017/8/15.
 */

public class CustomFields_Thumb_cVO implements Parcelable {


    private List<String> thumb_c;

    public List<String> getThumb_c() {
        return thumb_c;
    }

    public void setThumb_c(List<String> thumb_c) {
        this.thumb_c = thumb_c;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.thumb_c);
    }

    public CustomFields_Thumb_cVO() {
    }

    protected CustomFields_Thumb_cVO(Parcel in) {
        this.thumb_c = in.createStringArrayList();
    }

    public static final Parcelable.Creator<CustomFields_Thumb_cVO> CREATOR = new Parcelable.Creator<CustomFields_Thumb_cVO>() {
        @Override
        public CustomFields_Thumb_cVO createFromParcel(Parcel source) {
            return new CustomFields_Thumb_cVO(source);
        }

        @Override
        public CustomFields_Thumb_cVO[] newArray(int size) {
            return new CustomFields_Thumb_cVO[size];
        }
    };
}
