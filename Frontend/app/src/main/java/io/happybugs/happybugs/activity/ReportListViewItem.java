package io.happybugs.happybugs.activity;

public class ReportListViewItem {
    private String reportTitle;
    private String reportContent;
    private int reportId;
    private int reportUserCount;
    private String createdAt;

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public void setReportContent(String content) {
        reportContent = content;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public String getReportContent() {
        return this.reportContent;
    }

    public int getReportId() {
        return reportId;
    }

    public int getReportUserCount() {
        return reportUserCount;
    }

    public void setReportUserCount(int reportUserCount) {
        this.reportUserCount = reportUserCount;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}