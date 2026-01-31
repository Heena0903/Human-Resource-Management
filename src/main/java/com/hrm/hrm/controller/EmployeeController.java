package com.hrm.hrm.controller;

import com.hrm.hrm.dto.SalaryDTO;
import com.hrm.hrm.dto.EmployeeDTO;
import com.hrm.hrm.model.Employee;
import com.hrm.hrm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "http://localhost:3000")

public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Add a new employee
    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    // Get all employees
    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }


    // Update employee details
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        return employeeService.updateEmployee(id, updatedEmployee);
    }

    // Delete employee
    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "Employee with ID " + id + " deleted successfully.";
    }

    // Get salary records for a specific employee (DTO)
    @GetMapping("/{id}/salary")
    public List<SalaryDTO> getSalaryByEmployee(@PathVariable Long id) {
        return employeeService.getSalaryByEmployee(id);
    }
}
