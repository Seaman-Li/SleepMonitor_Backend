package com.example.sleepmonitor_backend.Repository;

import com.example.sleepmonitor_backend.Model.UserHealth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserHealthRepository extends JpaRepository<UserHealth, Long> {
    // 根据userId获取用户的健康信息
    @Query("SELECT uh FROM UserHealth uh WHERE uh.user.userId = :userId")
    Optional<UserHealth> findByUserId(Long userId);

    // Custom method to fetch all active user health records
    List<UserHealth> findByIsValidTrue();
}
