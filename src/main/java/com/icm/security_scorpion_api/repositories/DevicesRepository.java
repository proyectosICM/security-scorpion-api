package com.icm.security_scorpion_api.repositories;

import com.icm.security_scorpion_api.models.DeviceType;
import com.icm.security_scorpion_api.models.DevicesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevicesRepository extends JpaRepository<DevicesModel, Long> {
    public List<DevicesModel> findByDeviceGroupModelId(Long deviceGroupModelId);
    public List<DevicesModel> findByDeviceGroupModelIdAndTypeNot(Long deviceGroupId, DeviceType type);
}
