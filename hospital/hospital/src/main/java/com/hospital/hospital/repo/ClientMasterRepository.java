package com.hospital.hospital.repo;

import com.hospital.hospital.entity.TbClientMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientMasterRepository extends JpaRepository<TbClientMaster, Long> {

    Optional<TbClientMaster> findByEmailIdAndPassword(String emailId, String password);

    TbClientMaster findByEmailId(String emailId);

    Optional<TbClientMaster> findByMobileNo(String mobileNo);
}
