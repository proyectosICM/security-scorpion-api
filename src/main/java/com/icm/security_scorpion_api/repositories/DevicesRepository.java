package com.icm.security_scorpion_api.repositories;

import com.icm.security_scorpion_api.models.DevicesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevicesRepository extends JpaRepository<DevicesModel, Long> {
}
