package com.hrm.hrm.controller;

import com.hrm.hrm.model.Admin;
import com.hrm.hrm.repository.AdminRepository;
import com.hrm.hrm.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")

public class AdminAuthController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);
        return Map.of("message", "Admin registered successfully");
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Admin admin) {
        Admin existingAdmin = adminRepository.findByUsername(admin.getUsername());
        if (existingAdmin == null) {
            throw new RuntimeException("Admin not found");
        }

        if (!passwordEncoder.matches(admin.getPassword(), existingAdmin.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // ✅ Generate JWT token
        String token = jwtUtil.generateToken(existingAdmin.getUsername());

        // ✅ Return token to frontend/Postman
        return Map.of(
                "message", "Login successful",
                "token", token
        );
    }
}
