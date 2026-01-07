package com.hospital.hospital.controller;

import com.hospital.hospital.dto.BookingDTO;
import com.hospital.hospital.dto.BookingSlotSummaryDTO;
import com.hospital.hospital.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService service;

    // =========================
    // EXISTING APIs (UNCHANGED)
    // =========================
    @PostMapping("/add")
    public BookingDTO addBooking(@RequestBody BookingDTO dto) {
        return service.addBooking(dto);
    }

    @PutMapping("/update/{id}")
    public BookingDTO updateBooking(@PathVariable Long id, @RequestBody BookingDTO dto) {
        return service.updateBooking(id, dto);
    }

    @GetMapping("/all")
    public List<BookingDTO> getAllBookings() {
        return service.getAllBookings();
    }

    // =====================================================
    // ✅ SLOT-WISE SUMMARY (DASHBOARD)
    // =====================================================
    @GetMapping("/summary")
    public List<BookingSlotSummaryDTO> getBookingSummary(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate
    ) {
        return service.getSlotWiseSummary(startDate, endDate);
    }

    // =====================================================
    // ✅ DOCTOR / DEPARTMENT WISE SUMMARY
    // =====================================================
    @GetMapping("/doctor-summary")
    public List<BookingSlotSummaryDTO> getDoctorWiseSummary(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate,
            @RequestParam(value = "departmentId", required = false) Long departmentId,
            @RequestParam(value = "doctorId", required = false) Long doctorId
    ) {
        return service.getDoctorWiseSummary(
                startDate,
                endDate,
                departmentId,
                doctorId
        );
    }

    // =====================================================
// ✅ BOOKING DETAILS API (ON SUMMARY COUNT CLICK)
// =====================================================
    @GetMapping("/details")
    public List<BookingDTO> getBookingDetails(
            @RequestParam("bookingDate") LocalDate bookingDate,
            @RequestParam(value = "slotId", required = false) Long slotId,
            @RequestParam(value = "departmentId", required = false) Long departmentId,
            @RequestParam(value = "doctorId", required = false) Long doctorId
    ) {
        return service.getBookingDetails(
                bookingDate,
                slotId,
                departmentId,
                doctorId
        );
    }

}
