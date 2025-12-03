package com.hrm.hrm.service;

import com.hrm.hrm.model.*;
import com.hrm.hrm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.hrm.hrm.dto.ManagerDTO;

@Service
public class AdminService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AttendanceRepository attendanceRepository; // optional, for attendance feature

    // ✅ Add Department
    public Department addDepartment(Department dept) {
        return departmentRepository.save(dept);
    }

    // ✅ Add Manager under a department
    public Manager addManager(Manager manager) {
        if (manager.getDepartment() == null || manager.getDepartment().getId() == null) {
            throw new RuntimeException("Department is required for manager");
        }

        Long deptId = manager.getDepartment().getId();

        Department dept = departmentRepository.findById(deptId)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + deptId));

        manager.setDepartment(dept); // attach full entity

        return managerRepository.save(manager);
    }


    // ✅ Get all Employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // ✅ Get all Departments
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // ✅ Get all Managers
    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    // (Optional) Get Attendance by Employee
    public List<Attendance> getAttendanceByEmployee(Long employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId);
    }
    private ManagerDTO toManagerDTO(Manager manager) {
        Long deptId = manager.getDepartment() != null ? manager.getDepartment().getId() : null;
        String deptName = manager.getDepartment() != null ? manager.getDepartment().getName() : null;

        return new ManagerDTO(
                manager.getId(),
                manager.getName(),
                manager.getEmail(),
                deptId,
                deptName
        );
    }

    public ManagerDTO addManagerAndReturnDTO(Manager manager) {
        Manager saved = addManager(manager); // reuse your existing addManager logic
        return toManagerDTO(saved);
    }

    public List<ManagerDTO> getAllManagersDTO() {
        return managerRepository.findAll()
                .stream()
                .map(this::toManagerDTO)
                .toList();
    }
}
