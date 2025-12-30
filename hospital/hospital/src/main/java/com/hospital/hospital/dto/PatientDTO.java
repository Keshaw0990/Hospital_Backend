package com.hospital.hospital.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;
@Data
public class PatientDTO {

    private String fullName;
    private String phone;
    private String gender;

    // ✅ MATCH YOUR INPUT: 10-09-1998
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDate dob;


    private String address;
    private Boolean status;
    private Long stateId;
}
