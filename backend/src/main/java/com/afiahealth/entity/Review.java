package com.afiahealth.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(unique = true, length = 36) private String uuid;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "reviewer_id") private User reviewer;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "hospital_id") private Hospital hospital;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "doctor_id") private Doctor doctor;
    @Column(name = "overall_rating", nullable = false) private Integer overallRating;
    @Column(name = "cleanliness_rating") private Integer cleanlinessRating;
    @Column(name = "staff_rating") private Integer staffRating;
    @Column(name = "wait_time_rating") private Integer waitTimeRating;
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT") private String body;
    @Column(name = "visit_date") private LocalDate visitDate;
    @Column(name = "is_anonymous") @Builder.Default private Boolean isAnonymous = false;
    @Column(name = "is_approved") @Builder.Default private Boolean isApproved = false;
    @Column(name = "helpful_count") @Builder.Default private Integer helpfulCount = 0;
    @Column(name = "created_at") private LocalDateTime createdAt;
    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); if (uuid == null) uuid = java.util.UUID.randomUUID().toString(); }
}
