package com.afiahealth.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "doctors")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Doctor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(unique = true, length = 36) private String uuid;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id") private User user;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "hospital_id") private Hospital hospital;
    @Column(name = "medical_license_number", nullable = false) private String medicalLicenseNumber;
    private String bio;
    private String qualification;
    @Column(name = "years_of_experience") @Builder.Default private Integer yearsOfExperience = 0;
    @Column(name = "consultation_fee", precision = 10, scale = 2) private BigDecimal consultationFee;
    @Column(name = "average_rating", precision = 3, scale = 2) @Builder.Default private BigDecimal averageRating = BigDecimal.ZERO;
    @Column(name = "review_count") @Builder.Default private Integer reviewCount = 0;
    @Column(name = "telemedicine_available") @Builder.Default private Boolean telemedicineAvailable = false;
    @Column(name = "is_verified") @Builder.Default private Boolean isVerified = false;
    @Column(name = "is_active") @Builder.Default private Boolean isActive = true;
    @Column(name = "created_at") private LocalDateTime createdAt;
    @Column(name = "updated_at") private LocalDateTime updatedAt;
    @PrePersist protected void onCreate() { createdAt = updatedAt = LocalDateTime.now(); if (uuid == null) uuid = java.util.UUID.randomUUID().toString(); }
    @PreUpdate protected void onUpdate() { updatedAt = LocalDateTime.now(); }
}
