package com.hospital.hospital.dto;

import lombok.Data;

@Data
public class UserDoctorResponseDTO {

    private Long mappingId;
    private Long userId;
    private Long doctorId;

    private String doctorName;
    private String phone;
    private String specialty;

    private Boolean status;   // <-- ADD THIS
}

