package com.hospital.hospital.repo;

import com.hospital.hospital.entity.TbPatient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<TbPatient, Long> {

    // ✅ Correct uniqueness check
    boolean existsByPhoneAndClientId(String phone, Long clientId);

    // ✅ Correct phone verification
    Optional<TbPatient> findByPhoneAndClientId(String phone, Long clientId);
}
