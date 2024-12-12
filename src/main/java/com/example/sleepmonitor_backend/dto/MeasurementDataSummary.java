package com.example.sleepmonitor_backend.dto;

import java.util.Date;

public class MeasurementDataSummary {
    private Long measurementId;
    private Long userId;
    private Date sessionTime;

    public MeasurementDataSummary(Long measurementId, Long userId, Date sessionTime) {
        this.measurementId = measurementId;
        this.userId = userId;
        this.sessionTime = sessionTime;
    }

    // Getters and Setters
    public Long getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(Long measurementId) {
        this.measurementId = measurementId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(Date sessionTime) {
        this.sessionTime = sessionTime;
    }
}
