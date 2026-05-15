package com.afiahealth.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 36) private String uuid;
    @Column(name = "first_name", nullable = false) private String firstName;
    @Column(name = "last_name", nullable = false) private String lastName;
    @Column(nullable = false, unique = true) private String email;
    private String phone;
    @Column(name = "password_hash", nullable = false) private String passwordHash;
    @Enumerated(EnumType.STRING)
    @Builder.Default private Role role = Role.PATIENT;
    @Column(name = "profile_photo_url") private String profilePhotoUrl;
    @Column(name = "date_of_birth") private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING) private Gender gender;
    @Column(name = "blood_group") private String bloodGroup;
    private String address;
    private String city;
    private String state;
    @Builder.Default private String country = "Nigeria";
    @Builder.Default private Boolean isVerified = false;
    @Builder.Default private Boolean isActive = true;
    @Column(name = "email_verified_at") private LocalDateTime emailVerifiedAt;
    @Column(name = "last_login_at") private LocalDateTime lastLoginAt;
    @Column(name = "language_preference") @Builder.Default private String languagePreference = "en";
    @Column(name = "created_at") private LocalDateTime createdAt;
    @Column(name = "updated_at") private LocalDateTime updatedAt;

    @PrePersist protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
        if (uuid == null) uuid = java.util.UUID.randomUUID().toString();
    }
    @PreUpdate protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    public enum Role { PATIENT, DOCTOR, HOSPITAL_ADMIN, ADMIN, SUPER_ADMIN }
    public enum Gender { MALE, FEMALE, OTHER, PREFER_NOT_TO_SAY }
}
