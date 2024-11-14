package com.example.sleepmonitor_backend.Model;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "user_health")
public class UserHealth {

    public enum Gender {
        MALE, FEMALE, OTHER;
    }
    public enum Blood_Type{
        A, B, AB, O, OTHER;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long healthId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private Gender gender;  // Gender could be an enum with values MALE, FEMALE, OTHER

    @Enumerated(EnumType.STRING)
    private Blood_Type bloodType;

    private Integer age;

    @Column(precision = 5, scale = 2)
    private BigDecimal height;

    @Column(precision = 5, scale = 2)
    private BigDecimal  weight;

    @Column(name = "is_valid", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean isValid = true;

    @Column(name = "created_at ", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    //初始化model的createdAt，这样model在被创建时会填入创建时间
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = new Date();
        }
    }

    public Long getHealthId() {
        return healthId;
    }
    public void setHealthId(Long healthId) {
        this.healthId = healthId;
    }

    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Blood_Type getBloodType() {
        return bloodType;
    }
    public void setBloodType(Blood_Type bloodType) {
        this.bloodType = bloodType;
    }

    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getHeight() {
        return height;
    }
    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Boolean getIsValid() {
        return isValid;
    }
    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

}