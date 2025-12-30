package com.hospital.hospital.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingDTO {

    private Long bookingId;

    private Long patientId;
    private String patientName;
    private String patientAddress;
    private String patientPhone;
    private Long stateId;
    private String stateName;
    private Long doctorId;
    private String doctorName;

    private LocalDate bookingDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long slotId;
    private String slotName;
    private String bookingNo;
    private String status;
}
