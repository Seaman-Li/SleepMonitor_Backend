package com.example.sleepmonitor_backend.Repository;

import com.example.sleepmonitor_backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 查询考虑is_valid标记的方法
    @Query("SELECT u FROM User u WHERE u.username = ?1 AND u.isValid = true")
    User findByUsername_IsValidTrue(String username);

    @Query("SELECT u FROM User u WHERE u.userId = ?1 AND u.isValid = true")
    User findByUserId_IsValidTrue(Long userId);


    // 查询已删除用户
    @Query("SELECT u FROM User u WHERE u.userId = ?1 AND u.isValid = false")
    Optional<User> findDeletedById(Long userId);

    // 根据username查找用户
    User findByUsername(String username);

    User findByUserId(Long userId);

    // 检查用户名是否已存在
    boolean existsByUsername(String username);
    // 检查电子邮件是否已存在
    boolean existsByEmail(String email);
    //检查手机号是否已存在
    boolean existsByPhone(String phone);
}
