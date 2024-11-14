package com.example.sleepmonitor_backend.Model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "measurement_data")
public class MeasurementData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long measurementId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "session_time", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sessionTime;
    //初始化model的sessionTime，这样model在被创建时会填入测量时间
    @PrePersist
    protected void onCreate() {
        if (sessionTime == null) {
            sessionTime = new Date();
        }
    }

    @Lob
    private byte[] ecgSamples;

    @Lob
    private byte[] ppgSamples;

    @Column(name = "heart_rate")
    private Integer heartRate;

    @Column(name = "blood_pressure")
    private Integer bloodPressure;

    @Column(name = "is_valid")
    private Boolean isValid;

    public Long getMeasurementId() {
        return measurementId;
    }
//    public void setMeasurementId(Long measurementId) {
//        this.measurementId = measurementId;
//    }

    public Date getSessionTime() {
        return sessionTime;
    }
//    public void setSessionTime(Date sessionTime) {
//        this.sessionTime = sessionTime;
//    }

    public byte[] getEcgSamples() {
        return ecgSamples;
    }
//    public void setEcgSamples(byte[] ecgSamples) {
//        this.ecgSamples = ecgSamples;
//    }

    public byte[] getPpgSamples() {
        return ppgSamples;
    }
//    public void setPpgSamples(byte[] ppgSamples) {
//        this.ppgSamples = ppgSamples;
//    }

    public Integer getHeartRate() {
        return heartRate;
    }
//    public void setHeartRate(Integer heartRate) {
//        this.heartRate = heartRate;
//    }

    public Integer getBloodPressure() {
        return bloodPressure;
    }
//    public void setBloodPressure(Integer bloodPressure) {
//        this.bloodPressure = bloodPressure;
//    }

}