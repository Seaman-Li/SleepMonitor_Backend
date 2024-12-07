package com.example.sleepmonitor_backend.Repository;

import com.example.sleepmonitor_backend.Model.MeasurementData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasurementDataRepository extends JpaRepository<MeasurementData, Long> {
//    // 根据userId获取有效数据
//    List<MeasurementData> findByUserIdAndIsValid(Long userId, Boolean isValid);
//    // 根据userId获取所有测量数据
//    List<MeasurementData> findByUserId(Long userId);
    //获取所有数据
    List<MeasurementData> findAll();
}
