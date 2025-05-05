package com.icm.security_scorpion_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CameraDTO {
    private Long id;

    private String name;

    private String localUrl;

    private String publicUrl;

    private Boolean active;

    private String usernameEncrypted;

    private String passwordEncrypted;

    private Long deviceGroupId;

    private Long deviceId;

    private String deviceIp;

}
