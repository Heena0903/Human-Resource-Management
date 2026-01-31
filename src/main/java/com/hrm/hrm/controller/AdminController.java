package com.hrm.hrm.controller;

import com.hrm.hrm.dto.ManagerDTO;
import com.hrm.hrm.model.Department;
import com.hrm.hrm.model.Employee;
import com.hrm.hrm.model.Manager;
import com.hrm.hrm.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ---------------- DEPARTMENTS ----------------

    // Add new Department
    @PostMapping("/departments")
    public Department addDepartment(@RequestBody Department dept) {
        return adminService.addDepartment(dept);
    }

    // Get all Departments
    @GetMapping("/departments")
    public List<Department> getAllDepartments() {
        return adminService.getAllDepartments();
    }

    // ---------------- MANAGERS ----------------

    // Add new Manager (password generated, emailed) and return DTO
    @PostMapping("/managers")
    public ManagerDTO addManager(@RequestBody Manager manager) {
        return adminService.addManagerAndReturnDTO(manager);
    }

    // Get all Managers as DTOs
    @GetMapping("/managers")
    public List<ManagerDTO> getAllManagers() {
        return adminService.getAllManagersDTO();
    }

    // ---------------- EMPLOYEES ----------------

    // View all Employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return adminService.getAllEmployees();
    }
}

