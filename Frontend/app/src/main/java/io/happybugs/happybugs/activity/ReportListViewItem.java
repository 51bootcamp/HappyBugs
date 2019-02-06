package io.happybugs.happybugs.activity;

public class ReportListViewItem {
    private String reportTitle;
    private String reportContent;
    private int reportId;

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

}