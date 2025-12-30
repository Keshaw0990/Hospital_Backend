package com.hospital.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BookingCountDTO {
    private LocalDate bookingDate;
    private Long totalBookings;
}
