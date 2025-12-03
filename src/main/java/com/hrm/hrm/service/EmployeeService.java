package com.hrm.hrm.service;

import com.hrm.hrm.dto.SalaryDTO;
import com.hrm.hrm.model.Employee;
import com.hrm.hrm.model.Salary;
import com.hrm.hrm.repository.EmployeeRepository;
import com.hrm.hrm.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SalaryRepository salaryRepository;


    // Add employee
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Get employee by ID
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    // Update employee
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmployee = getEmployeeById(id);
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setDesignation(updatedEmployee.getDesignation());
        existingEmployee.setManager(updatedEmployee.getManager());
        return employeeRepository.save(existingEmployee);
    }

    // Delete employee
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    // Get salary records by employee (DTO output)
    public List<SalaryDTO> getSalaryByEmployee(Long employeeId) {
        return salaryRepository.findAll()
                .stream()
                .filter(s -> s.getEmployee().getId().equals(employeeId))
                .map(s -> new SalaryDTO(
                        s.getId(),
                        s.getAmount(),
                        s.getMonth(),
                        s.getDate(),
                        s.getEmployee().getId(),
                        s.getEmployee().getName()
                ))
                .toList();
    }
}
