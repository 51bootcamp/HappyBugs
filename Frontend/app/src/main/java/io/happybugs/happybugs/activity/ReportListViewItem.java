package io.happybugs.happybugs.activity;

public class ReportListViewItem {
    private String reportContent;
    private int reportId;

    public void setReportContent(String content) {
        reportContent = content;
    }


    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getReportContent() {
        return this.reportContent;
    }

    public int getReportId() {
        return reportId;
    }

}