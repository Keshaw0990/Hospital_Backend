package com.hospital.hospital.repo;

import com.hospital.hospital.entity.TbUserDoctorMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDoctorMappingRepository extends JpaRepository<TbUserDoctorMapping, Long> {

    List<TbUserDoctorMapping> findByUser_PkUserId(Long userId);

    void deleteByUser_PkUserId(Long userId);
}
