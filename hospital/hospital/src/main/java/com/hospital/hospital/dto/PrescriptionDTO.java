package com.hospital.hospital.dto;

import lombok.Data;

@Data
public class PrescriptionDTO {

    private Long prescriptionId;

    private Long medicineDoctorId;
    private Long doctorId;
    private String doctorName;

    private Long medicineId;
    private String medicineName;

    private Long patientId;
    private String patientName;

    private String dose;
    private Integer quantity;
    private String instruction;

    private Boolean morning;
    private Boolean afternoon;
    private Boolean evening;
    private Boolean night;
}
