package com.icm.security_scorpion_api.controllers;

import com.icm.security_scorpion_api.models.DeviceGroupModel;
import com.icm.security_scorpion_api.services.DeviceGroupService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/device-groups")
@RequiredArgsConstructor
public class DeviceGroupController {

    private final DeviceGroupService deviceGroupService;

    @GetMapping("/{deviceGroupId}")
    public ResponseEntity<DeviceGroupModel> findById(@PathVariable @NotNull Long deviceGroupId) {
        return deviceGroupService.findById(deviceGroupId)
                .map(group -> new ResponseEntity<>(group, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<DeviceGroupModel> findAll() {
        return deviceGroupService.findAll();
    }

    @GetMapping("/page")
    public Page<DeviceGroupModel> findAll(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return deviceGroupService.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<DeviceGroupModel> save(@RequestBody @Valid DeviceGroupModel deviceGroupModel) {
        DeviceGroupModel savedModel = deviceGroupService.save(deviceGroupModel);
        return new ResponseEntity<>(savedModel, HttpStatus.CREATED);
    }

    @PutMapping("/{deviceGroupId}")
    public ResponseEntity<?> update(@PathVariable @NotNull Long deviceGroupId,
                                    @RequestBody @Valid DeviceGroupModel deviceGroupModel) {
        try {
            DeviceGroupModel updatedModel = deviceGroupService.update(deviceGroupId, deviceGroupModel);
            return new ResponseEntity<>(updatedModel, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{deviceGroupId}")
    public ResponseEntity<?> delete(@PathVariable @NotNull Long deviceGroupId) {
        deviceGroupService.deleteById(deviceGroupId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
