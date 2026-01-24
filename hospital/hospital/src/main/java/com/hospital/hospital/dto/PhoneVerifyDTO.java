package com.hospital.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PhoneVerifyDTO {

    private boolean exists;
    private String message;
    private Long clientId;
    private Integer maxPatientCount;       // 👈 original / limit

    private List<PatientBasicDTO> patients;
}
