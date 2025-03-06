package com.icm.security_scorpion_api.controllers;

import com.icm.security_scorpion_api.exceptions.GroupNotActiveException;
import com.icm.security_scorpion_api.exceptions.InvalidCredentialsException;
import com.icm.security_scorpion_api.models.DeviceGroupModel;
import com.icm.security_scorpion_api.models.DevicesModel;
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

    @GetMapping("/auth")
    public ResponseEntity<?> findByDeviceGroupModelIdAuth(@RequestParam String username,
                                                          @RequestParam String password) {
        try {
            List<DevicesModel> devices = deviceGroupService.findByDeviceGroupModelIdAuth(username, password);
            return new ResponseEntity<>(devices, HttpStatus.OK);
        } catch (InvalidCredentialsException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (GroupNotActiveException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
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

    @PutMapping("/wifi/{deviceGroupId}")
    public ResponseEntity<?> changeWifiCredentials(@PathVariable @NotNull Long deviceGroupId,
                                                   @RequestParam @NotNull String ssid,
                                                   @RequestParam @NotNull String password) {
        try {
            DeviceGroupModel updatedModel = deviceGroupService.changeWifiCredentials(deviceGroupId, ssid, password);
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
