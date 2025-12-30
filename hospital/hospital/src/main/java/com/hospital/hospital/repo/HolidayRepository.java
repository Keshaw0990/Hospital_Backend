package com.hospital.hospital.repo;

import com.hospital.hospital.entity.TbHoliday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository extends JpaRepository<TbHoliday, Long> {

    // ✅ NEW: Fetch holidays for a doctor within date range
    List<TbHoliday> findByDoctor_PkDoctorIdAndHolidayDateBetween(
            Long doctorId,
            LocalDate startDate,
            LocalDate endDate
    );
}
