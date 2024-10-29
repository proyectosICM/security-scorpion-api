package com.icm.security_scorpion_api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "devices_group")
public class DeviceGroupModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9-]+$", message = "Name must be alphanumeric and can include hyphens, but not spaces")
    private String nameGroup;

    @Column(nullable = false, length = 50, unique = true)
    @NotBlank(message = "User is required")
    @Size(max = 50, message = "User must not exceed 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "User must be alphanumeric and unique, without spaces or special characters")
    private String username;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters long")
    private String password;

    @Column(nullable = false)
    private boolean isActive = true;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;
}
