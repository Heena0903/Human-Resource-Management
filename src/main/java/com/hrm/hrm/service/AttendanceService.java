package com.hrm.hrm.service;

import com.hrm.hrm.model.Attendance;
import com.hrm.hrm.model.Employee;
import com.hrm.hrm.repository.AttendanceRepository;
import com.hrm.hrm.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Mark attendance (Check-in)
    public Attendance markAttendance(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setTime(LocalDateTime.now());
        attendance.setPresent(true);
        attendance.setRemarks("Checked In");

        return attendanceRepository.save(attendance);
    }

    // Mark checkout (update existing attendance)
    public Attendance markCheckout(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        attendance.setTime(LocalDateTime.now());
        attendance.setRemarks("Checked Out");
        attendance.setPresent(false); // Optional: depending on logic

        return attendanceRepository.save(attendance);
    }

    // Get all attendance records for an employee
    public List<Attendance> getAttendanceByEmployee(Long employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId);
    }

    // Get all attendance records for employees under a manager
    public List<Attendance> getAttendanceByManager(Long managerId) {
        return attendanceRepository.findAll()
                .stream()
                .filter(a -> a.getEmployee().getManager() != null &&
                        a.getEmployee().getManager().getId().equals(managerId))
                .collect(Collectors.toList());
    }
}
