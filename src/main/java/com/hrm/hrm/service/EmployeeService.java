package com.hrm.hrm.service;

import com.hrm.hrm.dto.SalaryDTO;
import com.hrm.hrm.dto.EmployeeDTO;
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

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(emp -> new EmployeeDTO(
                        emp.getId(),
                        emp.getName(),
                        emp.getEmail(),
                        emp.getDesignation()
                ))
                .toList();
    }


    public EmployeeDTO getEmployeeById(Long id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        return new EmployeeDTO(
                emp.getId(),
                emp.getName(),
                emp.getEmail(),
                emp.getDesignation()
        );
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setDesignation(updatedEmployee.getDesignation());
        existingEmployee.setManager(updatedEmployee.getManager());

        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

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

