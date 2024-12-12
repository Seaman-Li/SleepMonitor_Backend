package com.example.sleepmonitor_backend.Controller;

import com.example.sleepmonitor_backend.Model.UserHealth;
import com.example.sleepmonitor_backend.Service.UserHealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/userHealth")
public class UserHealthController {
    @Autowired
    private UserHealthService userHealthService;

    @PostMapping
    public ResponseEntity<UserHealth> createUserHealth(@RequestBody UserHealth userHealth) {
        return ResponseEntity.ok(userHealthService.createUserHealth(userHealth));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserHealth> updateUserHealth(@PathVariable Long userId, @RequestBody UserHealth userHealth) {
        return ResponseEntity.ok(userHealthService.updateUserHealth(userId, userHealth));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Optional<UserHealth>> getUserHealthByUserId(@PathVariable Long userId) {
        Optional<UserHealth> userHealth = userHealthService.getUserHealthByUserId(userId);
        if (userHealth.isPresent()) {
            return ResponseEntity.ok(userHealth);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserHealth>> getAllUserHealth() {
        List<UserHealth> userHealths = userHealthService.getAllUserHealth();
        return ResponseEntity.ok(userHealths);
    }

    @GetMapping("/allActive")
    public ResponseEntity<List<UserHealth>> getActiveUserHealth() {
        List<UserHealth> userHealths = userHealthService.getActiveUserHealth();
        return ResponseEntity.ok(userHealths);
    }


}
