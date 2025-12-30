package com.hospital.hospital.repo;

import com.hospital.hospital.entity.TbDoctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<TbDoctor, Long> {

    List<TbDoctor> findByDepartment_PkDepartmentId(Long departmentId);
}
