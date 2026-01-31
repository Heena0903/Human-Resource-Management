package com.hrm.hrm.controller;

import com.hrm.hrm.dto.DepartmentDTO;
import com.hrm.hrm.model.Department;
import com.hrm.hrm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "http://localhost:3000")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // Get all departments as DTO to avoid nested managers
    @GetMapping
    public List<DepartmentDTO> getAll() {
        return departmentService.getAllDepartments()
                .stream()
                .map(dept -> new DepartmentDTO(dept.getId(), dept.getName()))
                .collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public DepartmentDTO getById(@PathVariable Long id) {
        Department department = departmentService.getDepartmentById(id);
        return new DepartmentDTO(department.getId(), department.getName());
    }

    // Create department
    @PostMapping
    public DepartmentDTO create(@RequestBody Department department) {
        Department saved = departmentService.createDepartment(department);
        return new DepartmentDTO(saved.getId(), saved.getName());
    }

    // Update department
    @PutMapping("/{id}")
    public DepartmentDTO update(@PathVariable Long id, @RequestBody Department department) {
        Department updated = departmentService.updateDepartment(id, department);
        return new DepartmentDTO(updated.getId(), updated.getName());
    }

    // Delete department
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return "Department deleted successfully";
    }
}
