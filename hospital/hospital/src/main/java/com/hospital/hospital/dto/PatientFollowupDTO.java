package com.hospital.hospital.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientFollowupDTO {

    private Long patientId;

    private String fullName;
    private String phone;
    private String gender;
    private LocalDate dob;
    private String address;

    private LocalDate followUpDate;
    private LocalDate nextFollowUpDate;
    private Long clientId; // hospital id
}
