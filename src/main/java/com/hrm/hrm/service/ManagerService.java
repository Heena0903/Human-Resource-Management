package com.hrm.hrm.service;

import com.hrm.hrm.dto.EmployeeDTO;
import com.hrm.hrm.model.Employee;
import com.hrm.hrm.model.Manager;
import com.hrm.hrm.model.Salary;
import com.hrm.hrm.repository.EmployeeRepository;
import com.hrm.hrm.repository.ManagerRepository;
import com.hrm.hrm.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private SalaryRepository salaryRepository;


    // Helper method to convert Employee entity -> DTO
    private EmployeeDTO toEmployeeDTO(Employee emp) {
        return new EmployeeDTO(
                emp.getId(),
                emp.getName(),
                emp.getEmail(),
                emp.getDesignation()
        );
    }

    // Add employee under manager (entity save)
    public Employee addEmployee(Employee employee) {

        // Load correct Manager entity from DB using ID
        if (employee.getManager() != null && employee.getManager().getId() != null) {
            Long managerId = employee.getManager().getId();
            Manager manager = managerRepository.findById(managerId)
                    .orElseThrow(() -> new RuntimeException("Manager not found with id: " + managerId));

            employee.setManager(manager); // attach full manager object
        }

        return employeeRepository.save(employee);
    }

    // Add employee and return DTO version
    public EmployeeDTO addEmployeeAndReturnDTO(Employee employee) {
        Employee saved = addEmployee(employee);
        return toEmployeeDTO(saved);
    }

    // Get employees under a manager (DTO output)
    public List<EmployeeDTO> getEmployeesByManager(Long managerId) {
        return employeeRepository.findAll()
                .stream()
                .filter(emp -> emp.getManager() != null && emp.getManager().getId().equals(managerId))
                .map(this::toEmployeeDTO)
                .collect(Collectors.toList());
    }

    // Add or update salary info for employee
    public Salary addSalary(Salary salary) {
        salary.setDate(LocalDate.now());
        return salaryRepository.save(salary);
    }

    // Get all salary records for employees under a manager
    public List<Salary> getSalariesByManager(Long managerId) {
        return salaryRepository.findAll()
                .stream()
                .filter(sal -> sal.getEmployee().getManager() != null &&
                        sal.getEmployee().getManager().getId().equals(managerId))
                .collect(Collectors.toList());
    }
}
