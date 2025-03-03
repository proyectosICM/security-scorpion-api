package com.icm.security_scorpion_api.services;

import com.icm.security_scorpion_api.models.DeviceGroupModel;
import com.icm.security_scorpion_api.models.DevicesModel;
import com.icm.security_scorpion_api.repositories.DeviceGroupRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceGroupService {
    private final DeviceGroupRepository deviceGroupRepository;
    private final DevicesService devicesService;

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

    public DeviceGroupModel changeWifiCredentials(Long deviceGroupId, String ssid, String password) {
        DeviceGroupModel existing = getDeviceGroupById(deviceGroupId);
        existing.setSsidWiFi(ssid);
        existing.setPasswordWiFi(password);
        return deviceGroupRepository.save(existing);
    }


    // Delete a DeviceGroupModel by ID
    @Transactional
    public void deleteById(Long deviceGroupId) {
        DeviceGroupModel deviceGroup = getDeviceGroupById(deviceGroupId);

        // Obtener todos los dispositivos asociados a este grupo
        List<DevicesModel> devices = devicesService.findByDeviceGroupModelId(deviceGroupId);

        // Desvincular cada dispositivo de su grupo
        for (DevicesModel device : devices) {
            device.setDeviceGroupModel(null);
            devicesService.save(device);
        }

        // Ahora eliminar el grupo
        deviceGroupRepository.deleteById(deviceGroupId);
    }
}
