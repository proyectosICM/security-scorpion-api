package com.icm.security_scorpion_api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
    @Table(name = "devices-group")
public class DeviceGroupModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "Name must be alphanumeric and can include spaces and hyphens")
    @Column(nullable = false, length = 100)
    private String nameGroup;

    @NotBlank(message = "User is required")
    @Size(max = 50, message = "User must not exceed 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "User must be alphanumeric")
    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isActive = true;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;
}
