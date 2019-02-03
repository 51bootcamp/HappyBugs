package io.happybugs.happybugs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserReportList {
    @SerializedName("data")
    @Expose
    private List<UserReportItem> data = null;

    public List<UserReportItem> getData() {
        return data;
    }

    public void setData(List<UserReportItem> data) {
        this.data = data;
    }
}