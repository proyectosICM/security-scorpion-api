package com.icm.security_scorpion_api.mapper;

import com.icm.security_scorpion_api.dto.CameraDTO;
import com.icm.security_scorpion_api.models.CameraModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CameraMapper {
    CameraDTO toDto(CameraModel model);

    @Mapping(target = "deviceGroupModel", ignore = true) // Se maneja aparte
    CameraModel toEntity(CameraDTO dto);
}
