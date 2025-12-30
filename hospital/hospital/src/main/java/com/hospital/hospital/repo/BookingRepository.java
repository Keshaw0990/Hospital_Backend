package com.hospital.hospital.repo;

import com.hospital.hospital.dto.BookingSlotSummaryDTO;
import com.hospital.hospital.entity.TbBooking;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<TbBooking, Long> {

    // =====================================================
    // SLOT CAPACITY CHECK (UNCHANGED)
    // =====================================================
    long countBySlot_PkSlotIdAndBookingDate(Long slotId, LocalDate bookingDate);


    // =====================================================
    // ✅ SLOT-WISE SUMMARY (DASHBOARD TABLE)
    // =====================================================
    @Query("""
        SELECT new com.hospital.hospital.dto.BookingSlotSummaryDTO(
            s.pkSlotId,
            s.slotName,
            b.bookingDate,
            COUNT(b),
            NULL,
            NULL
        )
        FROM TbBooking b
        JOIN b.slot s
        JOIN b.doctor d
        WHERE b.bookingDate BETWEEN :startDate AND :endDate
        GROUP BY
            s.pkSlotId,
            s.slotName,
            b.bookingDate
        ORDER BY b.bookingDate, s.pkSlotId
    """)
    List<BookingSlotSummaryDTO> getSlotWiseBookingSummary(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );


    // =====================================================
    // ✅ DOCTOR / DEPARTMENT WISE SUMMARY
    // =====================================================
    @Query("""
        SELECT new com.hospital.hospital.dto.BookingSlotSummaryDTO(
            s.pkSlotId,
            s.slotName,
            b.bookingDate,
            COUNT(b),
            d.department.pkDepartmentId,
            d.pkDoctorId
        )
        FROM TbBooking b
        JOIN b.slot s
        JOIN b.doctor d
        WHERE b.bookingDate BETWEEN :startDate AND :endDate
          AND (:departmentId IS NULL OR d.department.pkDepartmentId = :departmentId)
          AND (:doctorId IS NULL OR d.pkDoctorId = :doctorId)
        GROUP BY
            s.pkSlotId,
            s.slotName,
            b.bookingDate,
            d.department.pkDepartmentId,
            d.pkDoctorId
        ORDER BY b.bookingDate, s.pkSlotId
    """)
    List<BookingSlotSummaryDTO> getDoctorWiseBookingSummary(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("departmentId") Long departmentId,
            @Param("doctorId") Long doctorId
    );


    // =====================================================
    // ✅ BOOKING DETAILS (ON COUNT CLICK)
    // =====================================================
    @Query("""
        SELECT b
        FROM TbBooking b
        JOIN b.slot s
        JOIN b.doctor d
        WHERE b.bookingDate = :bookingDate
          AND (:slotId IS NULL OR s.pkSlotId = :slotId)
          AND (:departmentId IS NULL OR d.department.pkDepartmentId = :departmentId)
          AND (:doctorId IS NULL OR d.pkDoctorId = :doctorId)
        ORDER BY b.startTime
    """)
    List<TbBooking> findBookingDetails(
            @Param("bookingDate") LocalDate bookingDate,
            @Param("slotId") Long slotId,
            @Param("departmentId") Long departmentId,
            @Param("doctorId") Long doctorId
    );
}
