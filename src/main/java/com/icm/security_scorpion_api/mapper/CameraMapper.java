package com.icm.security_scorpion_api.mapper;

import com.icm.security_scorpion_api.dto.CameraDTO;
import com.icm.security_scorpion_api.models.CameraModel;
import com.icm.security_scorpion_api.models.DeviceGroupModel;
import com.icm.security_scorpion_api.repositories.CamerasRepository;
import com.icm.security_scorpion_api.repositories.DeviceGroupRepository;
import com.icm.security_scorpion_api.repositories.DevicesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class   CameraMapper {
    private final DeviceGroupRepository deviceGroupRepository;
    public CameraDTO mapToDTO(CameraModel model) {
        if (model == null) {
            return null;
        }

        CameraDTO dto = new CameraDTO();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setLocalUrl(model.getLocalUrl());
        dto.setPublicUrl(model.getPublicUrl());
        dto.setActive(model.getActive());
        dto.setUsernameEncrypted(model.getUsernameEncrypted());
        dto.setPasswordEncrypted(model.getPasswordEncrypted());

        // Mapeo del deviceGroupId desde la relación
        if (model.getDeviceGroupModel() != null) {
            dto.setDeviceGroupId(model.getDeviceGroupModel().getId());
        } else {
            dto.setDeviceGroupId(null);
        }

        return dto;
    }

    public CameraModel maptoEntity(CameraDTO dto) {
        if (dto == null) {
            return null;
        }


        CameraModel model = new CameraModel();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setLocalUrl(dto.getLocalUrl());
        model.setPublicUrl(dto.getPublicUrl());
        model.setActive(dto.getActive());
        model.setUsernameEncrypted(dto.getUsernameEncrypted());
        model.setPasswordEncrypted(dto.getPasswordEncrypted());

     if (dto.getDeviceGroupId() != null) {
         DeviceGroupModel group = deviceGroupRepository.findById(dto.getDeviceGroupId()).orElseThrow();
         model.setDeviceGroupModel(group);
     }

            return model;
        }
}
