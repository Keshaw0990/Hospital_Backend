package com.hospital.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatientBasicDTO {
    private Long patientId;
    private String patientName;
}
