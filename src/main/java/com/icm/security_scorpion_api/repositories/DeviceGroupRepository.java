package com.icm.security_scorpion_api.repositories;

import com.icm.security_scorpion_api.models.DeviceGroupModel;
import com.icm.security_scorpion_api.models.DevicesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceGroupRepository extends JpaRepository<DeviceGroupModel, Long> {
    /* Temporal */
    Optional<DeviceGroupModel> findByUsernameAndPassword(String username, String password);
    /* --- */
}
