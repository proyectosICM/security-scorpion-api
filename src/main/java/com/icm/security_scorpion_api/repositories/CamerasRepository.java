package com.icm.security_scorpion_api.repositories;

import com.icm.security_scorpion_api.dto.CameraDTO;
import com.icm.security_scorpion_api.enums.DeviceType;
import com.icm.security_scorpion_api.models.CameraModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CamerasRepository extends JpaRepository<CameraModel, Long> {
    public List<CameraModel> findByDeviceGroupModelId(Long deviceGroupModelId);
    public Page<CameraModel> findByDeviceGroupModelId(Long deviceGroupId, Pageable pageable);
}
