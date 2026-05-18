package com.afiahealth.controller;

import com.afiahealth.dto.*;
import com.afiahealth.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointments", description = "Book, manage and track appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Book a new appointment")
    public ResponseEntity<ApiResponse<AppointmentDto>> bookAppointment(
        @Valid @RequestBody AppointmentBookRequest request
    ) {
        return ResponseEntity.status(201).body(ApiResponse.success(
            appointmentService.bookAppointment(request),
            "Appointment booked successfully!"
        ));
    }

    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get current user's appointments")
    public ResponseEntity<ApiResponse<Page<AppointmentDto>>> getMyAppointments(
        @RequestParam(required = false) String status,
        @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(appointmentService.getMyAppointments(status, pageable)));
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get appointment details")
    public ResponseEntity<ApiResponse<AppointmentDto>> getAppointment(@PathVariable String uuid) {
        return ResponseEntity.ok(ApiResponse.success(appointmentService.getAppointment(uuid)));
    }

    @PatchMapping("/{uuid}/cancel")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Cancel an appointment")
    public ResponseEntity<ApiResponse<AppointmentDto>> cancelAppointment(
        @PathVariable String uuid,
        @RequestBody CancelRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(appointmentService.cancelAppointment(uuid, request.getReason())));
    }

    @PatchMapping("/{uuid}/reschedule")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Reschedule an appointment")
    public ResponseEntity<ApiResponse<AppointmentDto>> rescheduleAppointment(
        @PathVariable String uuid,
        @Valid @RequestBody RescheduleRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(appointmentService.rescheduleAppointment(uuid, request)));
    }

    @GetMapping("/available-slots")
    @Operation(summary = "Get available appointment slots for a doctor/hospital")
    public ResponseEntity<ApiResponse<AvailableSlotsDto>> getAvailableSlots(
        @RequestParam(required = false) Long doctorId,
        @RequestParam Long hospitalId,
        @RequestParam String date
    ) {
        return ResponseEntity.ok(ApiResponse.success(
            appointmentService.getAvailableSlots(doctorId, hospitalId, date)
        ));
    }

    // Hospital admin endpoints
    @GetMapping("/hospital/{hospitalId}")
    @PreAuthorize("hasRole('HOSPITAL_ADMIN') or hasRole('ADMIN')")
    @Operation(summary = "Get all appointments for a hospital")
    public ResponseEntity<ApiResponse<Page<AppointmentDto>>> getHospitalAppointments(
        @PathVariable Long hospitalId,
        @RequestParam(required = false) String status,
        @RequestParam(required = false) String date,
        @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(
            appointmentService.getHospitalAppointments(hospitalId, status, date, pageable)
        ));
    }

    @PatchMapping("/{uuid}/confirm")
    @PreAuthorize("hasRole('HOSPITAL_ADMIN') or hasRole('ADMIN')")
    @Operation(summary = "Confirm a pending appointment")
    public ResponseEntity<ApiResponse<AppointmentDto>> confirmAppointment(@PathVariable String uuid) {
        return ResponseEntity.ok(ApiResponse.success(appointmentService.confirmAppointment(uuid)));
    }
}
