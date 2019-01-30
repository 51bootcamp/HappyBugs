package io.happybugs.happybugs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportData {
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("what")
    @Expose
    private String what;
    @SerializedName("reportId")
    @Expose
    private String reportId;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
}
