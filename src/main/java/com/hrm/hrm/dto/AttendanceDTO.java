package com.hrm.hrm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {
    private Long id;
    private LocalDateTime time;
    private boolean present;
    private String remarks;
    private Long employeeId;
    private String employeeName;
}
