package com.icm.security_scorpion_api.services;

import com.icm.security_scorpion_api.dto.GroupCredentialsDTO;
import com.icm.security_scorpion_api.dto.WifiCredentialsDTO;
import com.icm.security_scorpion_api.exceptions.GroupNotActiveException;
import com.icm.security_scorpion_api.exceptions.InvalidCredentialsException;
import com.icm.security_scorpion_api.models.DeviceGroupModel;
import com.icm.security_scorpion_api.models.DevicesModel;
import com.icm.security_scorpion_api.repositories.DeviceGroupRepository;
import com.icm.security_scorpion_api.repositories.DevicesRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceGroupService {
    private final DeviceGroupRepository deviceGroupRepository;
    private final DevicesService devicesService;
    private final DevicesRepository devicesRepository;

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

    /* Basic Autenticacion Service */
    /* Temporal */
    public List<DevicesModel> findByDeviceGroupModelIdAuth(String username, String password) {
        Optional<DeviceGroupModel> dg = deviceGroupRepository.findByUsernameAndPassword(username, password);

        if (dg.isPresent()) {
            if (dg.get().isActive()) {
                return devicesRepository.findByDeviceGroupModelId(dg.get().getId());
            } else {
                throw new GroupNotActiveException("The device group is not active.");
            }
        } else {
            throw new InvalidCredentialsException("Invalid username or password.");
        }
    }

    public Map<String, Object> authWeb(GroupCredentialsDTO groupCredentialsDTO) {
        Optional<DeviceGroupModel> deviceGroup = deviceGroupRepository.findByUsernameAndPassword(groupCredentialsDTO.getUsername(), groupCredentialsDTO.getPassword());

        if (deviceGroup.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("username", deviceGroup.get().getUsername());
            response.put("id", deviceGroup.get().getId());
            return response;
        }

        return null; // Retorna null si no encuentra el usuario
    }
    /* --- */
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

    public DeviceGroupModel changeWifiCredentials(Long deviceGroupId, WifiCredentialsDTO wifiCredentialsDTO) {
        DeviceGroupModel existing = getDeviceGroupById(deviceGroupId);
        existing.setSsidWiFi(wifiCredentialsDTO.getSsid());
        existing.setPasswordWiFi(wifiCredentialsDTO.getPassword());
        return deviceGroupRepository.save(existing);
    }

    public DeviceGroupModel changeGroupCredentials(Long deviceGroupId, GroupCredentialsDTO groupCredentialsDTO) {
        DeviceGroupModel existing = getDeviceGroupById(deviceGroupId);
        existing.setUsername(groupCredentialsDTO.getUsername());
        existing.setPassword(groupCredentialsDTO.getPassword());
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
