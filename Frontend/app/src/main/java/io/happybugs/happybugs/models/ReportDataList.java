package io.happybugs.happybugs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportDataList {
    @SerializedName("reportList")
    @Expose
    private List<String> reportList;
    //private ReportData rd;

    public List<String> getReportList() {
        //rd = new ReportData();
        //reportList.add(rd.getReportId());
        return reportList;
    }
}
