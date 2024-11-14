package com.example.sleepmonitor_backend.Controller;

import com.example.sleepmonitor_backend.Model.User;
import com.example.sleepmonitor_backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable Long id) {
//        User user = userService.getUserById(id);
//        return ResponseEntity.ok(user);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserByIdValid(@PathVariable Long id) {
        try {
            User user = userService.getUserById_IsValidTrue(id);
            return ResponseEntity.ok(user);//200
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();//404
        }
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);//200
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();//404
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();  // 返回 204 No Content 响应
        } catch (RuntimeException ex) {
            // 处理用户找不到的情况，返回404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{id}/validity")
    public ResponseEntity<Void> updateUserValidity(@PathVariable Long id, @RequestParam boolean isValid) {
        userService.updateUserValidity(id, isValid);
        return ResponseEntity.ok().build();
    }
}


