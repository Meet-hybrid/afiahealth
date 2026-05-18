package com.afiahealth.controller;

import com.afiahealth.dto.*;
import com.afiahealth.service.EmergencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/emergency")
@RequiredArgsConstructor
@Tag(name = "Emergency Services", description = "Emergency requests, ambulance dispatch, and monitoring")
public class EmergencyController {

    private final EmergencyService emergencyService;

    @PostMapping("/request")
    @Operation(summary = "Submit an emergency request (ambulance dispatch)")
    public ResponseEntity<ApiResponse<EmergencyRequestDto>> createEmergencyRequest(
        @Valid @RequestBody EmergencyRequestCreateDto request
    ) {
        return ResponseEntity.status(201).body(ApiResponse.success(
            emergencyService.createEmergencyRequest(request),
            "Emergency request submitted. Help is on the way!"
        ));
    }

    @GetMapping("/request/{referenceNumber}")
    @Operation(summary = "Track an emergency request by reference number")
    public ResponseEntity<ApiResponse<EmergencyRequestDto>> trackRequest(
        @PathVariable String referenceNumber
    ) {
        return ResponseEntity.ok(ApiResponse.success(emergencyService.trackRequest(referenceNumber)));
    }

    @GetMapping("/ambulances/available")
    @Operation(summary = "Find available ambulances near a location")
    public ResponseEntity<ApiResponse<List<AmbulanceDto>>> findAvailableAmbulances(
        @RequestParam Double lat,
        @RequestParam Double lng,
        @RequestParam(defaultValue = "15") Double radiusKm
    ) {
        return ResponseEntity.ok(ApiResponse.success(emergencyService.findAvailableAmbulances(lat, lng, radiusKm)));
    }

    @GetMapping("/hospitals/nearest")
    @Operation(summary = "Find nearest emergency hospitals")
    public ResponseEntity<ApiResponse<List<HospitalSummaryDto>>> findNearestEmergencyHospitals(
        @RequestParam Double lat,
        @RequestParam Double lng
    ) {
        return ResponseEntity.ok(ApiResponse.success(emergencyService.findNearestEmergencyHospitals(lat, lng)));
    }

    // Admin endpoints
    @GetMapping("/requests")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @Operation(summary = "List all emergency requests (admin)")
    public ResponseEntity<ApiResponse<List<EmergencyRequestDto>>> getAllRequests(
        @RequestParam(required = false) String status
    ) {
        return ResponseEntity.ok(ApiResponse.success(emergencyService.getAllRequests(status)));
    }

    @PatchMapping("/request/{id}/dispatch")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @Operation(summary = "Dispatch an ambulance to an emergency request")
    public ResponseEntity<ApiResponse<EmergencyRequestDto>> dispatchAmbulance(
        @PathVariable Long id,
        @RequestParam Long ambulanceId
    ) {
        return ResponseEntity.ok(ApiResponse.success(emergencyService.dispatchAmbulance(id, ambulanceId)));
    }

    @PatchMapping("/request/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @Operation(summary = "Update emergency request status")
    public ResponseEntity<ApiResponse<EmergencyRequestDto>> updateStatus(
        @PathVariable Long id,
        @RequestParam String status
    ) {
        return ResponseEntity.ok(ApiResponse.success(emergencyService.updateStatus(id, status)));
    }
}
