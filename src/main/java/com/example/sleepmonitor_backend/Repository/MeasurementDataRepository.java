package com.example.sleepmonitor_backend.Repository;

import com.example.sleepmonitor_backend.Model.MeasurementData;
import com.example.sleepmonitor_backend.Model.User;
import com.example.sleepmonitor_backend.dto.MeasurementDataSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface MeasurementDataRepository extends JpaRepository<MeasurementData, Long> {
//    // 根据userId获取有效数据
//    List<MeasurementData> findByUserIdAndIsValid(Long userId, Boolean isValid);
//    // 根据userId获取所有测量数据
//    List<MeasurementData> findByUserId(Long userId);
    //获取所有数据
    List<MeasurementData> findAll();

    @Repository
    public interface UserRepository extends JpaRepository<User, Long> {
    }

    @Query("SELECT new com.example.sleepmonitor_backend.dto.MeasurementDataSummary(md.measurementId, md.user.userId, md.sessionTime) FROM MeasurementData md WHERE md.user.userId = :userId AND md.isValid = true")
    List<MeasurementDataSummary> findAllValidMeasurementsByUserId(@Param("userId") Long userId);

}
