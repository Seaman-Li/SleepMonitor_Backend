package com.example.sleepmonitor_backend.Service;

import com.example.sleepmonitor_backend.Model.MeasurementData;
import com.example.sleepmonitor_backend.Repository.MeasurementDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementDataService {

    @Autowired
    private MeasurementDataRepository measurementDataRepository;

    public MeasurementData saveMeasurementData(MeasurementData measurementData) {
        return measurementDataRepository.save(measurementData);
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
