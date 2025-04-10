package com.example.smart_waste_management;

public class WasteReport {
    private String reportId;
    private String reportDetails;
    private int collected;
    private int recycled;
    private String collectionDate;

    public WasteReport(String reportId, String reportDetails, int collected, int recycled, String collectionDate) {
        this.reportId = reportId;
        this.reportDetails = reportDetails;
        this.collected = collected;
        this.recycled = recycled;
        this.collectionDate = collectionDate;
    }

    public String getReportId() {
        return reportId;
    }

    public String getReportDetails() {
        return reportDetails;
    }

    public int getCollected() {
        return collected;
    }

    public int getRecycled() {
        return recycled;
    }

    public String getCollectionDate() {
        return collectionDate;
    }
}