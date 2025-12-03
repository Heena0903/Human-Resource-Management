package com.hrm.hrm.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Timestamp of when attendance was marked
    private LocalDateTime time;

    // ✅ Indicates presence (true = present, false = absent)
    private boolean present;

    // ✅ Optional: store remarks or attendance type (e.g., "Late", "Half Day")
    private String remarks;

    // ✅ Establish relation to Employee
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
