package com.icm.security_scorpion_api.services;

import com.icm.security_scorpion_api.models.CameraModel;
import com.icm.security_scorpion_api.repositories.CamerasRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CameraService {
    private final CamerasRepository camerasRepository;
    public List<CameraModel> findAll() {
        return camerasRepository.findAll();
    }

    public Page<CameraModel> findAll(Pageable pageable) {
        return camerasRepository.findAll(pageable);

    }

    public CameraModel findById(Long cameraId) {
        return camerasRepository.findById(cameraId)
                .orElseThrow(() -> new EntityNotFoundException("Camera with id " + cameraId + " not found"));
    }

    public List<CameraModel> findByDeviceGroupModelId(Long deviceGroupModelId) {
        return camerasRepository.findByDeviceGroupModelId(deviceGroupModelId);
    }

    public Page<CameraModel> findByDeviceGroupModelId(Long deviceGroupId, Pageable pageable) {
        return camerasRepository.findByDeviceGroupModelId(deviceGroupId, pageable);
    }

    public CameraModel save(CameraModel camera) {
        return camerasRepository.save(camera);
    }

    public CameraModel update(Long cameraId, CameraModel camera) {
        if (!camerasRepository.existsById(cameraId)) {
            throw new EntityNotFoundException("Camera with id " + cameraId + " not found");
        }
        camera.setId(cameraId);
        return camerasRepository.save(camera);
    }

    public void deleteById(Long cameraId) {
        camerasRepository.deleteById(cameraId);
    }
}
