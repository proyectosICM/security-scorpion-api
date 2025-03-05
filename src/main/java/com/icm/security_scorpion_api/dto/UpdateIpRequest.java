package com.icm.security_scorpion_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateIpRequest {
    @NotBlank(message = "IP is required")
    @Pattern(regexp = "^(\\d{1,3}\\.){3}\\d{1,3}$", message = "Invalid IP address format")
    private String ipLocal;
}
