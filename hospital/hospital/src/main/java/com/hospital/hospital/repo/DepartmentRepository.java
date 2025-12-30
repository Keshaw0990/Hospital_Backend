package com.hospital.hospital.repo;

import com.hospital.hospital.entity.TbDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<TbDepartment, Long> {

    List<TbDepartment> findByClient_PkClientId(Long clientId);
}
