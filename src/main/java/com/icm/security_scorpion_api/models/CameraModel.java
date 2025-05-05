package com.icm.security_scorpion_api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cameras")
public class CameraModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String name;

    private String localUrl;

    private String publicUrl;

    private Boolean active;

    @Column(name = "username_encrypted")
    private String usernameEncrypted;

    @Column(name = "password_encrypted")
    private String passwordEncrypted;

    @ManyToOne
    @JoinColumn(name = "device_group", referencedColumnName = "id")
    private DeviceGroupModel deviceGroupModel;

    @ManyToOne
    @JoinColumn(name = "device", referencedColumnName = "id")
    private DevicesModel devicesModel;
}
