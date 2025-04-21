package com.icm.security_scorpion_api.controllers;

import com.icm.security_scorpion_api.models.CameraModel;
import com.icm.security_scorpion_api.services.CameraService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cameras")
@RequiredArgsConstructor
public class CameraController {

    private final CameraService cameraService;

    @GetMapping
    public ResponseEntity<List<CameraModel>> findAll() {
        return ResponseEntity.ok(cameraService.findAll());
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<CameraModel>> findAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(cameraService.findAll(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CameraModel> findById(@PathVariable Long id) {
        CameraModel camera = cameraService.findById(id);
        return ResponseEntity.ok(camera);
    }

    @GetMapping("/by-group/{groupId}")
    public ResponseEntity<List<CameraModel>> findAllByGroupId(@PathVariable Long groupId) {
        return ResponseEntity.ok(cameraService.findByDeviceGroupModelId(groupId));
    }

    @GetMapping("/by-group-paged/{groupId}")
    public ResponseEntity<Page<CameraModel>> findByGroupId(
            @PathVariable Long groupId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(cameraService.findByDeviceGroupModelId(groupId, PageRequest.of(page, size)));
    }


    @PostMapping
    public ResponseEntity<CameraModel> save(@RequestBody CameraModel camera) {
        CameraModel savedCamera = cameraService.save(camera);
        return ResponseEntity.created(URI.create("/api/cameras/" + savedCamera.getId())).body(savedCamera);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CameraModel> update(@PathVariable Long id, @RequestBody CameraModel camera) {
        CameraModel updatedCamera = cameraService.update(id, camera);
        return ResponseEntity.ok(updatedCamera);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        cameraService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
