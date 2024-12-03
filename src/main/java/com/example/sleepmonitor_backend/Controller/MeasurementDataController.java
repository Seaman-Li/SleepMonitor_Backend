package com.example.sleepmonitor_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.sleepmonitor_backend.Model.MeasurementData;
import com.example.sleepmonitor_backend.Service.MeasurementDataService;
import java.util.List;


@RestController
@RequestMapping("/api/measurement")
public class MeasurementDataController {

    @Autowired
    private MeasurementDataService service; // Correctly injected

    @PostMapping
    public ResponseEntity<MeasurementData> createMeasurement(@RequestBody MeasurementData data) {
        MeasurementData savedData = service.saveMeasurementData(data);
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

}
