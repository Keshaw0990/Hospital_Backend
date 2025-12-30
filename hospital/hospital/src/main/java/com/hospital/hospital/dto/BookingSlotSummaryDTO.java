package com.hospital.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BookingSlotSummaryDTO {

    private Long slotId;
    private String slotName;
    private LocalDate bookingDate;
    private Long totalBookings;

    private Long departmentId;
    private Long doctorId;

}
