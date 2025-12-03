package com.hrm.hrm.controller;
import com.hrm.hrm.dto.EmployeeDTO;

import com.hrm.hrm.model.Employee;
import com.hrm.hrm.model.Salary;
import com.hrm.hrm.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.hrm.hrm.dto.EmployeeDTO;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
@CrossOrigin(origins = "http://localhost:5173")
public class ManagerController {

    @Autowired
    private ManagerService managerService;


// ...

    @PostMapping("/employees")
    public EmployeeDTO addEmployee(@RequestBody Employee emp) {
        return managerService.addEmployeeAndReturnDTO(emp);
    }


    // Get all employees under a manager (DTO)
    @GetMapping("/employees/{managerId}")
    public List<EmployeeDTO> getEmployeesByManager(@PathVariable Long managerId) {
        return managerService.getEmployeesByManager(managerId);
    }

    // Add or update salary info for employee
    @PostMapping("/salary")
    public Salary addSalary(@RequestBody Salary salary) {
        return managerService.addSalary(salary);
    }

    // View all salary details in manager’s department
    @GetMapping("/salary/{managerId}")
    public List<Salary> getSalariesByManager(@PathVariable Long managerId) {
        return managerService.getSalariesByManager(managerId);
    }
}

