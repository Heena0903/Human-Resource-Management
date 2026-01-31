package com.hrm.hrm.controller;

import com.hrm.hrm.dto.AttendanceDTO;
import com.hrm.hrm.model.Attendance;
import com.hrm.hrm.model.Employee;
import com.hrm.hrm.repository.AttendanceRepository;
import com.hrm.hrm.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "http://localhost:3000")

public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private AttendanceDTO toDTO(Attendance att) {
        return new AttendanceDTO(
                att.getId(),
                att.getTime(),
                att.isPresent(),
                att.getRemarks(),
                att.getEmployee().getId(),
                att.getEmployee().getName()
        );
    }

    // Mark attendance
    @PostMapping("/{employeeId}")
    public AttendanceDTO markAttendance(
            @PathVariable Long employeeId,
            @RequestParam boolean present,
            @RequestParam(required = false) String remarks
    ) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        Attendance attendance = Attendance.builder()
                .time(LocalDateTime.now())
                .present(present)
                .remarks(remarks)
                .employee(employee)
                .build();

        Attendance saved = attendanceRepository.save(attendance);
        return toDTO(saved);
    }

    // Get all attendance
    @GetMapping
    public List<AttendanceDTO> getAllAttendance() {
        return attendanceRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Get attendance for one employee
    @GetMapping("/employee/{employeeId}")
    public List<AttendanceDTO> getAttendanceByEmployee(@PathVariable Long employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
