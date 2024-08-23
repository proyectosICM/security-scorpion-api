package com.icm.security_scorpion_api.controller;

import com.icm.security_scorpion_api.exceptions.GroupNotActiveException;
import com.icm.security_scorpion_api.exceptions.InvalidCredentialsException;
import com.icm.security_scorpion_api.models.DevicesModel;
import com.icm.security_scorpion_api.services.DevicesService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/devices")
public class DevicesController {
    @Autowired
    private DevicesService devicesService;


    @GetMapping("/auth")
    public ResponseEntity<?> findByDeviceGroupModelIdAuth(@RequestParam String username,
                                                          @RequestParam String password) {
        try {
            List<DevicesModel> devices = devicesService.findByDeviceGroupModelIdAuth(username, password);
            return new ResponseEntity<>(devices, HttpStatus.OK);
        } catch (InvalidCredentialsException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (GroupNotActiveException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }


    @GetMapping("/group/{deviceGroupModelId}")
    public ResponseEntity<List<DevicesModel>> findByDeviceGroupModelId(@PathVariable @NotNull Long deviceGroupModelId) {
        List<DevicesModel> devices = devicesService.findByDeviceGroupModelId(deviceGroupModelId);
        if (devices.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @GetMapping("/{devicesId}")
    public ResponseEntity<DevicesModel> findById(@PathVariable @NotNull Long devicesId) {
        return devicesService.findById(devicesId )
                .map(company -> new ResponseEntity<>(company, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<DevicesModel> findAll() {
        return devicesService.findAll();
    }

    @GetMapping("/page")
    public Page<DevicesModel> findAll(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return devicesService.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<DevicesModel> save(@RequestBody @Valid DevicesModel devicesModel){
        DevicesModel dataModel = devicesService.save(devicesModel);
        return new ResponseEntity<>(dataModel, HttpStatus.CREATED);
    }

    @PutMapping("/{devicesId}")
    public ResponseEntity<?> update(@PathVariable @NotNull Long devicesId,@RequestBody @Valid DevicesModel devicesModel){
        try {
            DevicesModel dataModel = devicesService.update(devicesId, devicesModel);
            return new ResponseEntity<>(dataModel, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{devicesId}")
    public ResponseEntity<?> delete(@PathVariable @NotNull Long devicesId){
        devicesService.deleteById(devicesId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
