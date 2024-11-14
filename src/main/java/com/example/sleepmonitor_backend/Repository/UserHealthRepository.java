package com.example.sleepmonitor_backend.Repository;

import com.example.sleepmonitor_backend.Model.UserHealth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHealthRepository extends JpaRepository<UserHealth, Long> {
    // 根据userId获取用户的健康信息
    UserHealth findByUser_UserId(Long userId);
}
