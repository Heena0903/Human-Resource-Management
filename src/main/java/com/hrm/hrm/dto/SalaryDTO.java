package com.hrm.hrm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDTO {
    private Long id;
    private Double amount;
    private String month;
    private LocalDate date;
    private Long employeeId;
    private String employeeName;
}

