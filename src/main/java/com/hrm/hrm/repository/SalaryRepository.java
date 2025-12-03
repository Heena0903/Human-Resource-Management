package com.hrm.hrm.repository;

import com.hrm.hrm.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
    List<Salary> findByEmployeeId(Long employeeId);
}
