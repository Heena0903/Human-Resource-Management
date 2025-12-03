package com.hrm.hrm.service;

import com.hrm.hrm.model.Salary;
import com.hrm.hrm.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    public Salary addSalary(Salary salary) {
        salary.setDate(LocalDate.now());
        return salaryRepository.save(salary);
    }

    public List<Salary> getSalariesByEmployee(Long employeeId) {
        return salaryRepository.findByEmployeeId(employeeId);
    }
}
