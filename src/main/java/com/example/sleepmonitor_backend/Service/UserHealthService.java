package com.example.sleepmonitor_backend.Service;

import com.example.sleepmonitor_backend.Model.UserHealth;
import com.example.sleepmonitor_backend.Repository.UserHealthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserHealthService {

    @Autowired
    private UserHealthRepository userHealthRepository;

    public UserHealth saveUserHealth(UserHealth userHealth) {
        return userHealthRepository.save(userHealth);
    }

    public UserHealth getUserHealthById(Long healthId) {
        return userHealthRepository.findById(healthId).orElseThrow(() -> new RuntimeException("User health record not found"));
    }

    public List<UserHealth> getAllUserHealthRecords() {
        return userHealthRepository.findAll();
    }

    public void deleteUserHealth(Long healthId) {
        userHealthRepository.deleteById(healthId);
    }

    // 更新健康记录
    public UserHealth updateUserHealth(UserHealth userHealth) {
        UserHealth existingUserHealth = userHealthRepository.findById(userHealth.getHealthId())
                .orElseThrow(() -> new IllegalArgumentException("Health record not found with ID: " + userHealth.getHealthId()));

        // Update fields, but check for null to avoid overwriting with null values
        if (userHealth.getGender() != null) existingUserHealth.setGender(userHealth.getGender());
        if (userHealth.getAge() != null) existingUserHealth.setAge(userHealth.getAge());
        if (userHealth.getHeight() != null) existingUserHealth.setHeight(userHealth.getHeight());
        if (userHealth.getWeight() != null) existingUserHealth.setWeight(userHealth.getWeight());
        if (userHealth.getBloodType() != null) existingUserHealth.setBloodType(userHealth.getBloodType());

        return userHealthRepository.save(existingUserHealth);
    }
}
