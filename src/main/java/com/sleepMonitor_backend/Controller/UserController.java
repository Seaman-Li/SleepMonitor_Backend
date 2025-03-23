package com.sleepMonitor_backend.Controller;

import com.sleepMonitor_backend.Model.User;
import com.sleepMonitor_backend.Service.UserService;
import com.sleepMonitor_backend.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/currentUser")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                User user = userService.getCurrentUser(token);
                return ResponseEntity.ok(user);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token: " + e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No Authorization token found");
    }

    // Endpoint to validate JWT
    @GetMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                userService.isTokenValid(authHeader.substring(7));
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired JWT token");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bearer token not found");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        try {
            String token = userService.loginAndGenerateToken(loginUser.getUsername(), loginUser.getPassword());
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "Login Successful");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failure: " + e.getMessage());
        }
    }


    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        // 清除可能存储在服务端的任何会话信息
        request.getSession().removeAttribute("user");
        return ResponseEntity.ok("Logout Successful");
    }


    @GetMapping("/admin/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/{id}")
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

    @DeleteMapping("users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();  // 返回 204 No Content 响应
        } catch (RuntimeException ex) {
            // 处理用户找不到的情况，返回404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("users/{id}")
    public ResponseEntity<User> updateUserPatch(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("users/{id}/validity")
    public ResponseEntity<Void> updateUserValidity(@PathVariable Long id, @RequestParam boolean isValid) {
        userService.updateUserValidity(id, isValid);
        return ResponseEntity.ok().build();
    }
}


