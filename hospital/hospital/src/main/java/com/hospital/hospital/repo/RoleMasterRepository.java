package com.hospital.hospital.repo;

import com.hospital.hospital.entity.TbRoleDetails;
import com.hospital.hospital.entity.TbRoleMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleMasterRepository extends JpaRepository<TbRoleMaster, Long> {

}
