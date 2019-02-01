package io.happybugs.happybugs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserReportList {
    @SerializedName("data")
    @Expose
    private List<UserReportList> data = null;

    public List<UserReportList> getData() {
        return data;
    }

    public void setData(List<UserReportList> data) {
        this.data = data;
    }
}
