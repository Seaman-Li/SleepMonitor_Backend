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
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;
    @Transient  // 表明 userId 不是数据库字段
    private Long userId;

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

    @Column(name = "ecg_samples", columnDefinition = "json")
    private String ecgSamples;

    @Column(name = "ppg_samples", columnDefinition = "json")
    private String ppgSamples;

    @Column(name = "ecg_filtered_samples", columnDefinition = "json")
    private String ecgFilteredSamples;

    @Column(name = "ppg_filtered_samples", columnDefinition = "json")
    private String ppgFilteredSamples;

    @Column(name = "hr_samples", columnDefinition = "json")
    private String hrSamples;

    @Column(name = "ptt_samples", columnDefinition = "json")
    private String pttSamples;

    @Column(name = "is_valid", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean isValid = true;

    public Long getMeasurementId() {
        return measurementId;
    }
    public void setMeasurementId(Long measurementId) {
        this.measurementId = measurementId;
    }


    public Long getUserId() {
        if (user != null) {
            return user.getUserId();
        }
        return userId;
    }
    public void setUserId(Long userId) {
        if (this.user == null) {
            this.user = new User();  // 在这里初始化 user 对象
        }
        this.user.setUserId(userId);
        this.userId = userId;
    }
    public void setUser(User user) {this.user = user;}

    public Date getSessionTime() {
        return sessionTime;
    }
    public void setSessionTime(Date sessionTime) {
        this.sessionTime = sessionTime;
    }

    public String getEcgSamples() {return ecgSamples;}
    public void setEcgSamples(String ecgSamples) {
        this.ecgSamples = ecgSamples;
    }

    public String getPpgSamples() {return ppgSamples;}
    public void setPpgSamples(String ppgSamples) {
        this.ppgSamples = ppgSamples;
    }

    public String getEcgFilteredSamples() {
        return ecgFilteredSamples;
    }
    public void setEcgFilteredSamples(String ecgFilteredSamples) {
        this.ecgFilteredSamples = ecgFilteredSamples;
    }

    public String getPpgFilteredSamples() {
        return ppgFilteredSamples;
    }
    public void setPpgFilteredSamples(String ppgFilteredSamples) {
        this.ppgFilteredSamples = ppgFilteredSamples;
    }

    public String getHrSamples() {return hrSamples;}
    public void setHrSamples(String hrSamples) {
        this.hrSamples = hrSamples;
    }

    public String getPttSamples() {return pttSamples;}
    public void setPttSamples(String pttSamples) {
        this.pttSamples = pttSamples;
    }

    public Boolean getIsValid() {
        return isValid;
    }
    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
}