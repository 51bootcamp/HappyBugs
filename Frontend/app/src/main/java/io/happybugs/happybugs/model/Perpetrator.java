package io.happybugs.happybugs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Perpetrator {

    @SerializedName("facebook_url")
    @Expose
    private String facebookUrl;
    @SerializedName("reporting_user_count")
    @Expose
    private Integer reportingUserCount;

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public Integer getReportingUserCount() {
        return reportingUserCount;
    }

    public void setReportingUserCount(Integer reportingUserCount) {
        this.reportingUserCount = reportingUserCount;
    }

}