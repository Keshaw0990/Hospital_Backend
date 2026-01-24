package com.hospital.hospital.repo;

import com.hospital.hospital.entity.TbPatient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<TbPatient, Long> {

    long countByClientId(Long clientId);

    List<TbPatient> findAllByPhoneAndClientId(String phone, Long clientId);

    long countByClientIdAndPhone(Long clientId, String phone);

}
