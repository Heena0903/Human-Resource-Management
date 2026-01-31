package com.hrm.hrm.controller;

import com.hrm.hrm.model.Manager;
import com.hrm.hrm.model.Employee;
import com.hrm.hrm.repository.ManagerRepository;
import com.hrm.hrm.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:3000")
public class ManagerEmployeeAuthController {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ================= MANAGER LOGIN =================

    @PostMapping("/manager")
    public ResponseEntity<?> managerLogin(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        Manager manager = managerRepository.findByEmail(email);

        if (manager == null || manager.getPassword() == null ||
                !passwordEncoder.matches(password, manager.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password"));
        }

        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "role", "MANAGER",
                "managerId", manager.getId(),
                "name", manager.getName()
        ));
    }

    // ================= SET PASSWORD (NEW) =================

    @PostMapping("/set-password")
    public ResponseEntity<?> setPassword(@RequestBody Map<String, String> body) {

        String token = body.get("token");
        String newPassword = body.get("newPassword");

        if (token == null || newPassword == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Token and password are required"));
        }

        Manager manager = managerRepository.findByResetToken(token);

        if (manager == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid or expired token"));
        }

        if (manager.getTokenExpiry() == null ||
                manager.getTokenExpiry().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Token has expired"));
        }


        manager.setPassword(passwordEncoder.encode(newPassword));

        manager.setResetToken(null);
        manager.setTokenExpiry(null);

        managerRepository.save(manager);

        return ResponseEntity.ok(
                Map.of("message", "Password set successfully. You can now log in.")
        );
    }

    // ================= EMPLOYEE LOGIN =================

    @PostMapping("/employee")
    public ResponseEntity<?> employeeLogin(@RequestBody Map<String, String> body) {

        Long employeeId;
        try {
            employeeId = Long.parseLong(body.get("employeeId"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Invalid employeeId"));
        }

        String password = body.get("password");

        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        if (employee == null || employee.getPassword() == null ||
                !passwordEncoder.matches(password, employee.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid employeeId or password"));
        }

        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "role", "EMPLOYEE",
                "employeeId", employee.getId(),
                "name", employee.getName()
        ));
    }
}
