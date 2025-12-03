package com.hrm.hrm.repository;

import com.hrm.hrm.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
    List<Attendance> findByEmployeeId(Long employeeId);


}