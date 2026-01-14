package com.hospital.hospital.service;

import com.hospital.hospital.dto.*;
import com.hospital.hospital.entity.*;
import com.hospital.hospital.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepo;
    private final PatientRepository patientRepo;
    private final DoctorRepository doctorRepo;
    private final StateRepository stateRepo;
    private final SlotRepository slotRepo;

    // =========================
    // ENTITY → DTO (UNCHANGED)
    // =========================
    private BookingDTO toDTO(TbBooking b) {

        BookingDTO dto = new BookingDTO();

        dto.setBookingId(b.getBookingId());
        dto.setBookingDate(b.getBookingDate());
        dto.setStartTime(b.getStartTime());
        dto.setEndTime(b.getEndTime());

        if (b.getSlot() != null) {
            dto.setSlotId(b.getSlot().getPkSlotId());
            dto.setSlotName(b.getSlot().getSlotName());
        }

        dto.setBookingNo(b.getBookingNo());
        dto.setStatus(b.getStatus().name());

        TbPatient p = b.getPatient();
        dto.setPatientId(p.getPatientId());
        dto.setPatientName(p.getFullName());
        dto.setPatientAddress(p.getAddress());
        dto.setPatientPhone(p.getPhone());
        dto.setStateId(p.getStateId());

        if (p.getStateId() != null) {
            stateRepo.findById(p.getStateId())
                    .ifPresent(state ->
                            dto.setStateName(state.getStateName()));
        }

        dto.setDoctorId(b.getDoctor().getPkDoctorId());
        dto.setDoctorName(b.getDoctor().getFullName());

        // ✅ CLIENT ID (ONLY ADDITION)
        if (b.getDoctor().getDepartment() != null &&
                b.getDoctor().getDepartment().getClient() != null) {

            dto.setClientId(
                    b.getDoctor()
                            .getDepartment()
                            .getClient()
                            .getPkClientId()
            );
        }

        return dto;
    }


    // =========================
    // ADD BOOKING (UNCHANGED)
    // =========================
    public BookingDTO addBooking(BookingDTO dto) {

        TbPatient patient = patientRepo.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        TbDoctor doctor = doctorRepo.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        TbSlot slot = slotRepo.findById(dto.getSlotId())
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        long bookedCount =
                bookingRepo.countBySlot_PkSlotIdAndBookingDate(
                        slot.getPkSlotId(),
                        dto.getBookingDate()
                );

        if (bookedCount >= slot.getCapacity()) {
            throw new RuntimeException(
                    "Slot is fully booked for " + dto.getBookingDate()
            );
        }

        TbBooking booking = TbBooking.builder()
                .patient(patient)
                .doctor(doctor)
                .slot(slot)
                .bookingDate(dto.getBookingDate())
                .startTime(slot.getStartTime())
                .endTime(slot.getEndTime())
                .bookingNo("BK-" + UUID.randomUUID().toString().substring(0, 8))
                .status(BookingStatus.BOOKED)
                .build();

        return toDTO(bookingRepo.save(booking));
    }

    // =========================
    // UPDATE BOOKING (UNCHANGED)
    // =========================
    public BookingDTO updateBooking(Long id, BookingDTO dto) {

        TbBooking booking = bookingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(BookingStatus.valueOf(dto.getStatus()));
        return toDTO(bookingRepo.save(booking));
    }

    // =========================
    // GET ALL BOOKINGS (UNCHANGED)
    // =========================
    public List<BookingDTO> getAllBookings() {
        return bookingRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // =====================================================
    // ✅ SLOT-WISE SUMMARY
    // =====================================================
    public List<BookingSlotSummaryDTO> getSlotWiseSummary(
            LocalDate startDate,
            LocalDate endDate
    ) {
        return bookingRepo.getSlotWiseBookingSummary(startDate, endDate);
    }

    // =====================================================
    // ✅ DOCTOR / DEPARTMENT WISE SUMMARY
    // =====================================================
    public List<BookingSlotSummaryDTO> getDoctorWiseSummary(
            LocalDate startDate,
            LocalDate endDate,
            Long departmentId,
            Long doctorId
    ) {
        return bookingRepo.getDoctorWiseBookingSummary(
                startDate,
                endDate,
                departmentId,
                doctorId
        );
    }


    // =====================================================
// ✅ BOOKING DETAILS (ON COUNT CLICK)
// =====================================================
    public List<BookingDTO> getBookingDetails(
            LocalDate bookingDate,
            Long slotId,
            Long departmentId,
            Long doctorId
    ) {
        return bookingRepo.findBookingDetails(
                bookingDate,
                slotId,
                departmentId,
                doctorId
        ).stream().map(this::toDTO).collect(Collectors.toList());
    }

    // =====================================================
// ✅ UPDATE BOOKING STATUS BY ID
// =====================================================
    public BookingDTO updateBookingStatus(Long bookingId, BookingStatus status) {

        TbBooking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(status);

        return toDTO(bookingRepo.save(booking));
    }


}
