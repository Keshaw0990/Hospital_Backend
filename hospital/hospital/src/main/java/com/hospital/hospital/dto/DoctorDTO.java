package com.hospital.hospital.dto;

import lombok.Data;

@Data
public class DoctorDTO {

    private Long doctorId;
    private String fullName;
    private String phone;
    private String specialty;
    private Integer consultationDuration;
    private Long departmentId;
    private String departmentName;

    private Long clientId;
    private String orgName;

    private Integer dayCount;
}
