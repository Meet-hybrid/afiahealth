package com.afiahealth.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "hospitals")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    private String uuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private HospitalCategory category;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "short_description", length = 500)
    private String shortDescription;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "cover_image_url")
    private String coverImageUrl;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private List<String> gallery;

    private String email;
    private String phone;

    @Column(name = "phone_emergency")
    private String phoneEmergency;

    private String whatsapp;
    private String website;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Builder.Default
    private String country = "Nigeria";

    @Column(name = "postal_code")
    private String postalCode;

    @Column(nullable = false, precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(name = "google_place_id")
    private String googlePlaceId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private List<String> facilities;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private List<String> specialties;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private List<String> services;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "languages_spoken", columnDefinition = "JSON")
    private List<String> languagesSpoken;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "insurance_accepted", columnDefinition = "JSON")
    private List<String> insuranceAccepted;

    @Column(name = "bed_count")
    private Integer bedCount;

    @Column(name = "year_established")
    private Integer yearEstablished;

    @Column(name = "registration_number")
    private String registrationNumber;

    private String accreditation;

    @Enumerated(EnumType.STRING)
    @Column(name = "ownership_type")
    @Builder.Default
    private OwnershipType ownershipType = OwnershipType.PRIVATE;

    @Enumerated(EnumType.STRING)
    @Column(name = "hospital_tier")
    @Builder.Default
    private HospitalTier hospitalTier = HospitalTier.SECONDARY;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "opening_hours", columnDefinition = "JSON")
    private Map<String, String> openingHours;

    @Column(name = "is_open_24_7")
    @Builder.Default
    private Boolean isOpen247 = false;

    @Column(name = "has_emergency")
    @Builder.Default
    private Boolean hasEmergency = true;

    @Column(name = "has_ambulance")
    @Builder.Default
    private Boolean hasAmbulance = false;

    @Column(name = "has_telemedicine")
    @Builder.Default
    private Boolean hasTelemedicine = false;

    @Column(name = "has_pharmacy")
    @Builder.Default
    private Boolean hasPharmacy = false;

    @Column(name = "has_laboratory")
    @Builder.Default
    private Boolean hasLaboratory = false;

    @Column(name = "has_radiology")
    @Builder.Default
    private Boolean hasRadiology = false;

    @Column(name = "average_wait_time")
    private Integer averageWaitTime;

    @Column(name = "average_rating", precision = 3, scale = 2)
    @Builder.Default
    private BigDecimal averageRating = BigDecimal.ZERO;

    @Column(name = "review_count")
    @Builder.Default
    private Integer reviewCount = 0;

    @Column(name = "is_verified")
    @Builder.Default
    private Boolean isVerified = false;

    @Column(name = "is_featured")
    @Builder.Default
    private Boolean isFeatured = false;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "verification_date")
    private LocalDate verificationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_user_id")
    private User adminUser;

    @OneToMany(mappedBy = "hospital", fetch = FetchType.LAZY)
    private List<Doctor> doctors;

    @OneToMany(mappedBy = "hospital", fetch = FetchType.LAZY)
    private List<Review> reviews;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (uuid == null) uuid = java.util.UUID.randomUUID().toString();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum OwnershipType { PUBLIC, PRIVATE, FAITH_BASED, NGO }
    public enum HospitalTier { PRIMARY, SECONDARY, TERTIARY }
}
