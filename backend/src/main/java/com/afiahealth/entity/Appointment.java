package com.afiahealth.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Appointment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(unique = true, length = 36) private String uuid;
    @Column(name = "reference_number", unique = true, nullable = false) private String referenceNumber;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "patient_id") private User patient;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "doctor_id") private Doctor doctor;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "hospital_id") private Hospital hospital;
    @Column(name = "appointment_date", nullable = false) private LocalDate appointmentDate;
    @Column(name = "appointment_time", nullable = false) private LocalTime appointmentTime;
    @Enumerated(EnumType.STRING) @Column(name = "appointment_type") @Builder.Default private AppointmentType appointmentType = AppointmentType.IN_PERSON;
    @Column(name = "reason_for_visit", columnDefinition = "TEXT") private String reasonForVisit;
    @Enumerated(EnumType.STRING) @Builder.Default private Status status = Status.PENDING;
    @Column(name = "consultation_fee", precision = 10, scale = 2) private BigDecimal consultationFee;
    @Enumerated(EnumType.STRING) @Column(name = "payment_status") @Builder.Default private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    @Column(name = "telemedicine_link") private String telemedicineLink;
    @Column(name = "created_at") private LocalDateTime createdAt;
    @Column(name = "updated_at") private LocalDateTime updatedAt;
    @PrePersist protected void onCreate() { createdAt = updatedAt = LocalDateTime.now(); if (uuid == null) uuid = java.util.UUID.randomUUID().toString(); }
    @PreUpdate protected void onUpdate() { updatedAt = LocalDateTime.now(); }
    public enum AppointmentType { IN_PERSON, TELEMEDICINE, HOME_VISIT }
    public enum Status { PENDING, CONFIRMED, CANCELLED, COMPLETED, NO_SHOW, RESCHEDULED }
    public enum PaymentStatus { PENDING, PAID, REFUNDED, WAIVED }
}
