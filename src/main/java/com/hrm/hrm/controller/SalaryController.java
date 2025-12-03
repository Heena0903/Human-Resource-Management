package com.hrm.hrm.controller;

import com.hrm.hrm.model.Salary;
import com.hrm.hrm.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/salary")
@CrossOrigin(origins = "http://localhost:5173")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @PostMapping("/add")
    public Salary addSalary(@RequestBody Salary salary) {
        return salaryService.addSalary(salary);
    }

    @GetMapping("/employee/{employeeId}")
    public List<Salary> getSalariesByEmployee(@PathVariable Long employeeId) {
        return salaryService.getSalariesByEmployee(employeeId);
    }
}
