package io.happybugs.happybugs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportDataList {
    @SerializedName("reportList")
    @Expose
    private List<ReportData> reportList;

    public List<ReportData> getReportList() {
        if (reportList != null) {
            return reportList;
        } else {
            return null;
        }
    }
}
