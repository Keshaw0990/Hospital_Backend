package com.hospital.hospital.service;

import com.hospital.hospital.dto.HolidayDTO;
import com.hospital.hospital.entity.TbDoctor;
import com.hospital.hospital.entity.TbHoliday;
import com.hospital.hospital.repo.DoctorRepository;
import com.hospital.hospital.repo.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HolidayService {

    private final HolidayRepository holidayRepo;
    private final DoctorRepository doctorRepo;

    // =====================================================
    // ENTITY → DTO
    // =====================================================
    private HolidayDTO toDTO(TbHoliday h) {
        HolidayDTO dto = new HolidayDTO();
        dto.setHolidayId(h.getHolidayId());
        dto.setHolidayDate(h.getHolidayDate());
        dto.setReason(h.getReason());
        dto.setStatus(h.getStatus());

        if (h.getDoctor() != null) {
            dto.setDoctorId(h.getDoctor().getPkDoctorId());
            dto.setDoctorName(h.getDoctor().getFullName());
        }
        return dto;
    }

    // =====================================================
    // ADD HOLIDAY
    // =====================================================
    public HolidayDTO addHoliday(HolidayDTO dto) {

        if (dto.getHolidayDate() == null)
            throw new IllegalArgumentException("holidayDate cannot be null");

        if (dto.getReason() == null || dto.getReason().trim().isEmpty())
            throw new IllegalArgumentException("reason cannot be empty");

        if (dto.getDoctorId() == null)
            throw new IllegalArgumentException("doctorId is required");

        TbDoctor doctor = doctorRepo.findById(dto.getDoctorId())
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found with ID: " + dto.getDoctorId()));

        TbHoliday holiday = TbHoliday.builder()
                .holidayDate(dto.getHolidayDate())
                .reason(dto.getReason())
                .status(dto.getStatus() == null ? Boolean.TRUE : dto.getStatus())
                .doctor(doctor)
                .build();

        return toDTO(holidayRepo.save(holiday));
    }

    // =====================================================
    // UPDATE HOLIDAY
    // =====================================================
    public HolidayDTO updateHoliday(Long id, HolidayDTO dto) {

        TbHoliday holiday = holidayRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Holiday not found with ID: " + id));

        if (dto.getHolidayDate() == null)
            throw new IllegalArgumentException("holidayDate cannot be null");

        if (dto.getReason() == null || dto.getReason().trim().isEmpty())
            throw new IllegalArgumentException("reason cannot be empty");

        TbDoctor doctor = doctorRepo.findById(dto.getDoctorId())
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found with ID: " + dto.getDoctorId()));

        holiday.setHolidayDate(dto.getHolidayDate());
        holiday.setReason(dto.getReason());
        holiday.setStatus(dto.getStatus());
        holiday.setDoctor(doctor);

        return toDTO(holidayRepo.save(holiday));
    }

    // =====================================================
    // GET ALL HOLIDAYS
    // =====================================================
    public List<HolidayDTO> getAll() {
        return holidayRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // =====================================================
    // DELETE HOLIDAY
    // =====================================================
    public void deleteHoliday(Long id) {
        holidayRepo.deleteById(id);
    }

    // =====================================================
    // ✅ AVAILABLE DATES FOR NEXT 10 DAYS (EXCLUDING HOLIDAYS)
    // =====================================================
    public List<LocalDate> getAvailableDatesForDoctor(Long doctorId) {

        LocalDate startDate = LocalDate.now();          // today
        LocalDate endDate = startDate.plusDays(9);     // next 10 days

        // 1️⃣ Fetch holidays for doctor in next 10 days
        List<TbHoliday> holidays =
                holidayRepo.findByDoctor_PkDoctorIdAndHolidayDateBetween(
                        doctorId, startDate, endDate
                );

        // 2️⃣ Convert holiday dates to Set for fast lookup
        Set<LocalDate> holidayDates = holidays.stream()
                .map(TbHoliday::getHolidayDate)
                .collect(Collectors.toSet());

        // 3️⃣ Generate available dates
        List<LocalDate> availableDates = new ArrayList<>();
        LocalDate date = startDate;

        while (!date.isAfter(endDate)) {
            if (!holidayDates.contains(date)) {
                availableDates.add(date);
            }
            date = date.plusDays(1);
        }

        return availableDates;
    }
}
