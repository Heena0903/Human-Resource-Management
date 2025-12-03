package com.hrm.hrm.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private String month;       // "2025-12" or "Dec-2025"
    private LocalDate date;     // date when salary record created

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}

