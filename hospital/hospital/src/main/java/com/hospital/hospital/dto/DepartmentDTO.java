package com.hospital.hospital.dto;

import lombok.Data;

@Data
public class DepartmentDTO {
    private Long departmentId;
    private String name;
    private Long clientId;
    private String clientName;
}
