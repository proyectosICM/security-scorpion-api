package com.icm.security_scorpion_api.services;

import com.icm.security_scorpion_api.models.DeviceGroupModel;
import com.icm.security_scorpion_api.repositories.DeviceGroupRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceGroupService {
    @Autowired
    private DeviceGroupRepository deviceGroupRepository;

    // Helper method to retrieve a DeviceGroupModel by ID, throwing an exception if not found
    private DeviceGroupModel getDeviceGroupById(Long deviceGroupId) {
        return deviceGroupRepository.findById(deviceGroupId)
                .orElseThrow(() -> new EntityNotFoundException("Device group with id " + deviceGroupId + " not found"));
    }

    // Find a DeviceGroupModel by ID
    public Optional<DeviceGroupModel> findById(Long deviceGroupId) {
        return deviceGroupRepository.findById(deviceGroupId);
    }

    // Find all DeviceGroupModels
    public List<DeviceGroupModel> findAll() {
        return deviceGroupRepository.findAll();
    }

    // Find all DeviceGroupModels with pagination
    public Page<DeviceGroupModel> findAll(Pageable pageable) {
        return deviceGroupRepository.findAll(pageable);
    }

    // Save a new DeviceGroupModel
    public DeviceGroupModel save(@Valid DeviceGroupModel deviceGroupModel) {
        return deviceGroupRepository.save(deviceGroupModel);
    }

    // Update an existing DeviceGroupModel
    public DeviceGroupModel update(Long deviceGroupId, @Valid DeviceGroupModel deviceGroupModel) {
        DeviceGroupModel existing = getDeviceGroupById(deviceGroupId);
        existing.setNameGroup(deviceGroupModel.getNameGroup());
        existing.setUsername(deviceGroupModel.getUsername());
        existing.setPassword(deviceGroupModel.getPassword());
        existing.setActive(deviceGroupModel.isActive());
        return deviceGroupRepository.save(existing);
    }

    // Delete a DeviceGroupModel by ID
    public void deleteById(Long deviceGroupId) {
        deviceGroupRepository.deleteById(deviceGroupId);
    }
}
