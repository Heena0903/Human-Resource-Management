package com.hrm.hrm.service;

import com.hrm.hrm.dto.ManagerDTO;
import com.hrm.hrm.model.Department;
import com.hrm.hrm.model.Employee;
import com.hrm.hrm.model.Manager;
import com.hrm.hrm.repository.DepartmentRepository;
import com.hrm.hrm.repository.EmployeeRepository;
import com.hrm.hrm.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmailService emailService;

    // ---------- DEPARTMENTS ----------

    public Department addDepartment(Department dept) {
        return departmentRepository.save(dept);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // ---------- MANAGERS ----------

    public ManagerDTO addManagerAndReturnDTO(Manager manager) {

        // 1️Attach Department entity
        if (manager.getDepartment() != null && manager.getDepartment().getId() != null) {
            Department dept = departmentRepository.findById(manager.getDepartment().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Department not found with id: " + manager.getDepartment().getId()));
            manager.setDepartment(dept);
        }

        // 2Generate secure one-time token
        String token = UUID.randomUUID().toString();
        manager.setResetToken(token);
        manager.setTokenExpiry(LocalDateTime.now().plusHours(24));

        // Password NOT set yet
        manager.setPassword(null);

        //Save manager
        Manager saved = managerRepository.save(manager);

        //Send email with SET PASSWORD LINK
        try {
            String subject = "Set your ConnectHR Manager Password";

            String body =
                    "Hello " + saved.getName() + ",\n\n" +
                            "You have been added as a Manager on ConnectHR.\n\n" +
                            "Please click the link below to set your password:\n\n" +
                            "http://localhost:3000/set-password?token=" + token + "\n\n" +
                            "This link is valid for 24 hours.\n\n" +
                            "If you did not expect this email, you can safely ignore it.\n\n" +
                            "Regards,\n" +
                            "ConnectHR Team";

            emailService.sendSimpleMail(saved.getEmail(), subject, body);
        } catch (Exception e) {
            e.printStackTrace();
            // Manager is still created even if email fails
        }

        //Return DTO (no password!)
        Long deptId = saved.getDepartment() != null ? saved.getDepartment().getId() : null;
        String deptName = saved.getDepartment() != null ? saved.getDepartment().getName() : null;

        return new ManagerDTO(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                deptId,
                deptName
        );
    }

    public List<ManagerDTO> getAllManagersDTO() {
        return managerRepository.findAll()
                .stream()
                .map(m -> new ManagerDTO(
                        m.getId(),
                        m.getName(),
                        m.getEmail(),
                        m.getDepartment() != null ? m.getDepartment().getId() : null,
                        m.getDepartment() != null ? m.getDepartment().getName() : null
                ))
                .collect(Collectors.toList());
    }

    // ---------- EMPLOYEES ----------

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
