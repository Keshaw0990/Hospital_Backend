package com.hospital.hospital.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hospital.hospital.entity.TbState;

@Repository
public interface StateRepository extends JpaRepository<TbState, Long> {
}
