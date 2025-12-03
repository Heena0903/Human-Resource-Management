package com.hrm.hrm.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerDTO {
    private Long id;
    private String name;
    private String email;
    private Long departmentId;
    private String departmentName;
}

