package com.hospital.hospital.repo;

import com.hospital.hospital.entity.TbPatient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<TbPatient, Long> {

    boolean existsByPhone(String phone);

    Optional<TbPatient> findByPhone(String phone);

}
