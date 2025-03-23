package com.sleepMonitor_backend.Controller;

import com.sleepMonitor_backend.dto.MeasurementDataSummary;
import com.sleepMonitor_backend.Service.MeasurementDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sleepMonitor_backend.Model.MeasurementData;

import java.util.List;


@RestController
@RequestMapping("/api/measurement")
public class MeasurementDataController {


    @Autowired
    private MeasurementDataService service; // Correctly injected


    @PostMapping
    public ResponseEntity<?> createMeasurement(@RequestBody MeasurementData measurementData) {
        MeasurementData savedData = service.createAndSaveMeasurement(measurementData);
        return ResponseEntity.ok(savedData);
    }



//    @GetMapping("/valid/{userId}")
//    public ResponseEntity<List<MeasurementData>> getValidMeasurementDataByUserId(@PathVariable Long userId) {
//        List<MeasurementData> data = service.getValidMeasurementDataByUserId(userId);
//        return ResponseEntity.ok(data);
//    }
//
//    @GetMapping("/all/{userId}")
//    public ResponseEntity<List<MeasurementData>> getAllMeasurementDataByUserId(@PathVariable Long userId) {
//        List<MeasurementData> data = service.getAllMeasurementDataByUserId(userId);
//        return ResponseEntity.ok(data);
//    }

    @GetMapping("/all")
    public ResponseEntity<List<MeasurementData>> getAllMeasurementData() {
        List<MeasurementData> data = service.getAllMeasurementData();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<MeasurementDataSummary>> getValidMeasurementsByUserId(@PathVariable Long userId) {
        List<MeasurementDataSummary> measurements = service.getValidMeasurementDataByUserId(userId);
        if (measurements.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(measurements);
    }

}
