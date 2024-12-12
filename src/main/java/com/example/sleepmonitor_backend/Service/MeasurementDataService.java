package com.example.sleepmonitor_backend.Service;

import com.example.sleepmonitor_backend.Model.MeasurementData;
import com.example.sleepmonitor_backend.Model.User;
import com.example.sleepmonitor_backend.Repository.MeasurementDataRepository;
import com.example.sleepmonitor_backend.Repository.UserRepository;
import com.example.sleepmonitor_backend.dto.MeasurementDataSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementDataService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MeasurementDataRepository measurementDataRepository;


    public MeasurementData createAndSaveMeasurement(MeasurementData measurementData) {
        Long userId = measurementData.getUserId(); // 从 MeasurementData 提取 userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId)); // 查找 User
        measurementData.setUser(user); // 设置 User
        return measurementDataRepository.save(measurementData); // 保存 MeasurementData
    }

    public List<MeasurementDataSummary> getValidMeasurementDataByUserId(Long userId) {
        return measurementDataRepository.findAllValidMeasurementsByUserId(userId);
    }

//    public List<MeasurementData> getValidMeasurementDataByUserId(Long userId) {
//        return measurementDataRepository.findByUserIdAndIsValid(userId, true);
//    }
//
//    public List<MeasurementData> getAllMeasurementDataByUserId(Long userId) {
//        return measurementDataRepository.findByUserId(userId);
//    }

    public List<MeasurementData> getAllMeasurementData() {
        return measurementDataRepository.findAll();
    }


    public void deleteMeasurementData(Long measurementId) {
        measurementDataRepository.deleteById(measurementId);
    }
}
