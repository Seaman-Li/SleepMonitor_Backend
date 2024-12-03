package com.example.sleepmonitor_backend.Service;

import com.example.sleepmonitor_backend.Model.User;
import com.example.sleepmonitor_backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //保存用户，用于用户创建
    public User createUser(User user) {
        //查重
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already in use");
        }
        if(userRepository.existsByPhone(user.getPhone())){
            throw new IllegalArgumentException("Phone already in use");
        }
        // 加密用户的密码
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);  // 设置加密后的密码

        return userRepository.save(user);
    }

    public User validateLogin(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        // Check if the user exists, the password matches, and the user is valid (isValid is true)
        if (optionalUser.isPresent() && optionalUser.get().getIsValid() && passwordEncoder.matches(password, optionalUser.get().getPassword())) {
            return optionalUser.get();
        }
        return null;
    }


    //通过userID获取未被删除的用户
    public User getUserById_IsValidTrue(Long userId) {
        return userRepository.findByUserId_IsValidTrue(userId)
                .orElseThrow(() -> new RuntimeException("User not found or not valid"));
    }

    //通过userID获取用户（不管是否被删除）
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    //获取所有用户（不管是否被删除）
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //软删除将is_valid设置为false
    public void deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsValid(false);  // 将is_valid设置为false进行软删除
            userRepository.save(user);  // 保存更改
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    //更新用户的一般信息
    public User updateUser(Long userId, User user) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // 更新密码时需要加密
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // 更新其他信息
        Optional.ofNullable(user.getUsername()).ifPresent(existingUser::setUsername);
        Optional.ofNullable(user.getEmail()).ifPresent(existingUser::setEmail);
        Optional.ofNullable(user.getPhone()).ifPresent(existingUser::setPhone);
        Optional.ofNullable(user.getAddress()).ifPresent(existingUser::setAddress);

        return userRepository.save(existingUser);
    }


    //更新is_Valid,把已删除状态变成可用状态
    public void updateUserValidity(Long userId, boolean isValid) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        user.setIsValid(isValid);
        userRepository.save(user);
    }
}
