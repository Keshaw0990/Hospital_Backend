package com.hospital.hospital.dto;

import lombok.Data;

@Data
public class MedicineDTO {

    private Long medicineId;
    private String medicineName;
    private Long doctorId;
    private String unit;

}
