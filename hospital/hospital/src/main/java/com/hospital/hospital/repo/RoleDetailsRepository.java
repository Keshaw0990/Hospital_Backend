package com.hospital.hospital.repo;

import com.hospital.hospital.entity.TbRoleDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleDetailsRepository extends JpaRepository<TbRoleDetails, Long> {

    List<TbRoleDetails> findByRole_PkRoleId(Long roleId);

    @Query("SELECT rd FROM TbRoleDetails rd WHERE rd.role.pkRoleId = :roleId ORDER BY rd.seqNo ASC")
    List<TbRoleDetails> findMappingByRoleId(@Param("roleId") Long roleId);

    boolean existsByRole_PkRoleIdAndForm_PkFormIdAndClient_PkClientId(Long roleId, Long formId, Long clientId);
}
