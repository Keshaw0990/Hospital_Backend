package com.hospital.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhoneVerifyDTO {

    private boolean exists;
    private String message;
    private Long patientId;
    private Long clientId;
}
