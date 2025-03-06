package com.icm.security_scorpion_api.services;

import com.icm.security_scorpion_api.exceptions.GroupNotActiveException;
import com.icm.security_scorpion_api.exceptions.InvalidCredentialsException;
import com.icm.security_scorpion_api.models.DeviceGroupModel;
import com.icm.security_scorpion_api.models.DevicesModel;
import com.icm.security_scorpion_api.repositories.DeviceGroupRepository;
import com.icm.security_scorpion_api.repositories.DevicesRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DevicesService {

    private final DevicesRepository devicesRepository;

    private final DeviceGroupRepository deviceGroupRepository;

    private DevicesModel getDeviceById(Long devicesId) {
        return devicesRepository.findById(devicesId)
                .orElseThrow(() -> new EntityNotFoundException("Device with id " + devicesId + " not found"));
    }

    public List<DevicesModel> findByDeviceGroupModelId(Long deviceGroupModelId) {
        return devicesRepository.findByDeviceGroupModelId(deviceGroupModelId);
    }

    public Optional<DevicesModel> findById(Long devicesId){
        return devicesRepository.findById(devicesId);
    }

    public List<DevicesModel> findAll(){
        return devicesRepository.findAll();
    }
    public Page<DevicesModel> findAll(Pageable pageable){
        return devicesRepository.findAll(pageable);
    }

    public DevicesModel save(@Valid DevicesModel devicesModel){
        return devicesRepository.save(devicesModel);
    }

    public DevicesModel update(Long devicesId, @Valid DevicesModel devicesModel) {
        DevicesModel existing = getDeviceById(devicesId);
        existing.setNameDevice(devicesModel.getNameDevice());
        existing.setIpLocal(devicesModel.getIpLocal());
        return devicesRepository.save(existing);
    }

    public DevicesModel updateIp(Long deviceId, String newIp) {
        DevicesModel device = getDeviceById(deviceId);
        device.setIpLocal(newIp);
        return devicesRepository.save(device);
    }

    public void deleteById(Long companyId){
        devicesRepository.deleteById(companyId);
    }
}
