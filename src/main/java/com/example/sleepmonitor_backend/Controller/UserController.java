package com.example.sleepmonitor_backend.Controller;

import com.example.sleepmonitor_backend.Model.User;
import com.example.sleepmonitor_backend.Service.UserService;
import com.example.sleepmonitor_backend.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody User loginUser) {
//        User user = userService.validateLogin(loginUser.getUsername(), loginUser.getPassword());
//        if (user != null) {
//            user.setPassword(null); // 确保不返回密码
//            String token = jwtUtil.generateToken(user.getUserId().toString());
//            Map<String, Object> response = new HashMap<>();
//            response.put("token", token);
//            response.put("user", user);  // 返回用户的非敏感信息
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failure: username or password incorrect");
//        }
//    }

    @GetMapping("/currentUser")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        // Detailed logging
        System.out.println("Authorization Header: " + authorizationHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            try {
                // Additional logging for token
                System.out.println("Extracted Token: " + token);

                String userId = jwtUtil.validateTokenAndGetUserId(token);

                System.out.println("Validated User ID: " + userId);

                User user = userService.getUserById(Long.parseLong(userId));
                if (user != null) {
                    user.setPassword(null);  // Remove password for security
                    return ResponseEntity.ok(user);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
                }
            } catch (Exception e) {
                // More detailed error logging
                System.err.println("Token Validation Error: " + e.getMessage());
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token: " + e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No Authorization token found");
    }

//    @GetMapping("/currentUser")
//    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String authorizationHeader) {
//        try {
//            // 直接使用Authorization头部提供的JWT（假设客户端已正确发送）
//            String token = authorizationHeader;  // 直接使用提供的token
//            String userId = jwtUtil.validateTokenAndGetUserId(token);
//
//            // 使用userId获取用户信息
//            User user = userService.getUserById(Long.parseLong(userId));
//            if (user != null) {
//                user.setPassword(null); // 不返回密码
//                return ResponseEntity.ok(user);
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
//        }
//    }

    // Endpoint to validate JWT
    @GetMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                jwtUtil.validateTokenAndGetUserId(token);  // This will throw if invalid
                return ResponseEntity.ok().build();  // Token is valid
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired JWT token");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bearer token not found");
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        User user = userService.validateLogin(loginUser.getUsername(), loginUser.getPassword());
        if (user != null) {
            String token = jwtUtil.generateToken(user.getUserId().toString());
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);  // 将 JWT 放入响应中
            response.put("message", "Login Successful");  // 可选地添加更多的响应信息
            return ResponseEntity.ok(response);  // 返回 JSON 对象
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failure: username or password incorrect");
        }
    }


//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody User loginUser) {
//        User user = userService.validateLogin(loginUser.getUsername(), loginUser.getPassword());
//        if (user != null) {
//            user.setPassword(null);  // 确保不返回密码
//            return ResponseEntity.ok(user);  // 返回整个 User 对象
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failure: username or password incorrect");
//        }
//    }


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


