package com.icm.security_scorpion_api.services;

import com.icm.security_scorpion_api.config.MyWebSocketHandler;
import com.icm.security_scorpion_api.exceptions.GroupNotActiveException;
import com.icm.security_scorpion_api.exceptions.InvalidCredentialsException;
import com.icm.security_scorpion_api.models.DeviceGroupModel;
import com.icm.security_scorpion_api.models.DeviceType;
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
    private final MyWebSocketHandler webSocketHandler;

    private DevicesModel getDeviceById(Long devicesId) {
        return devicesRepository.findById(devicesId)
                .orElseThrow(() -> new EntityNotFoundException("Device with id " + devicesId + " not found"));
    }

    public List<DevicesModel> findByDeviceGroupModelId(Long deviceGroupModelId) {
        return devicesRepository.findByDeviceGroupModelId(deviceGroupModelId);
    }

    public List<DevicesModel> findGroupByDeviceId(Long deviceId) {
        DevicesModel existing = getDeviceById(deviceId);
        return devicesRepository.findByDeviceGroupModelIdAndTypeNot(deviceId, DeviceType.ALARM);
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

        DevicesModel updatedDevice = devicesRepository.save(existing);

        // 📢 Enviar mensaje WebSocket con el ID del grupo
        if (existing.getDeviceGroupModel() != null) {
            String groupId = existing.getDeviceGroupModel().getId().toString();
            webSocketHandler.sendMessageToAll("actlist:" + groupId);
        }

        return updatedDevice;
    }

    public DevicesModel updateIp(Long deviceId, String newIp) {
        DevicesModel device = getDeviceById(deviceId);
        device.setIpLocal(newIp);
        DevicesModel updatedDevice = devicesRepository.save(device);

        // 📢 Enviar mensaje WebSocket con el ID del grupo
        if (device.getDeviceGroupModel() != null) {
            String groupId = device.getDeviceGroupModel().getId().toString();
            webSocketHandler.sendMessageToAll("actlist:" + groupId);
        }

        return updatedDevice;
    }

    public void deleteById(Long companyId){
        devicesRepository.deleteById(companyId);
    }
}
