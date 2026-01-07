package com.hospital.hospital.repo;

import com.hospital.hospital.entity.TbUserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserMasterRepository extends JpaRepository<TbUserMaster, Long> {

    Optional<TbUserMaster> findByEmailIdAndPassword(String emailId, String password);

    Optional<TbUserMaster> findByEmailId(String emailId);

    // ✅ ADD THIS
    long countByClient_PkClientId(Long clientId);
}
