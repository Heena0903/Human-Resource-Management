package com.hrm.hrm.controller;

import com.hrm.hrm.dto.ManagerDTO;
import com.hrm.hrm.model.Department;
import com.hrm.hrm.model.Manager;
import com.hrm.hrm.model.Employee;
import com.hrm.hrm.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Add new Department
    @PostMapping("/departments")
    public Department addDepartment(@RequestBody Department dept) {
        return adminService.addDepartment(dept);
    }

    // Add new Manager, return DTO
    @PostMapping("/managers")
    public ManagerDTO addManager(@RequestBody Manager manager) {
        return adminService.addManagerAndReturnDTO(manager);
    }

    // Get all Departments
    @GetMapping("/departments")
    public List<Department> getAllDepartments() {
        return adminService.getAllDepartments();
    }

    // Get all Managers as DTOs
    @GetMapping("/managers")
    public List<ManagerDTO> getAllManagers() {
        return adminService.getAllManagersDTO();
    }

    // View all Employees (for admin dashboard)
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return adminService.getAllEmployees();
    }
}
