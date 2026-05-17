package com.afiahealth.controller;

import com.afiahealth.dto.*;
import com.afiahealth.service.HospitalService;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/hospitals")
@RequiredArgsConstructor
@Tag(name = "Hospitals", description = "Hospital search, listing, and management APIs")
public class HospitalController {

    private final HospitalService hospitalService;

    @GetMapping
    @Operation(summary = "Search and list hospitals with filters")
    public ResponseEntity<ApiResponse<Page<HospitalSummaryDto>>> searchHospitals(
        @RequestParam(required = false) String query,
        @RequestParam(required = false) String city,
        @RequestParam(required = false) String state,
        @RequestParam(required = false) String specialty,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) Boolean hasEmergency,
        @RequestParam(required = false) Boolean hasTelemedicine,
        @RequestParam(required = false) Boolean isOpen247,
        @RequestParam(required = false) String insurance,
        @RequestParam(required = false) Double lat,
        @RequestParam(required = false) Double lng,
        @RequestParam(required = false, defaultValue = "10") Double radiusKm,
        @RequestParam(required = false) String sortBy,
        @PageableDefault(size = 20) Pageable pageable
    ) {
        HospitalSearchRequest request = HospitalSearchRequest.builder()
            .query(query).city(city).state(state)
            .specialty(specialty).category(category)
            .hasEmergency(hasEmergency).hasTelemedicine(hasTelemedicine)
            .isOpen247(isOpen247).insurance(insurance)
            .lat(lat).lng(lng).radiusKm(radiusKm)
            .sortBy(sortBy).build();

        return ResponseEntity.ok(ApiResponse.success(hospitalService.searchHospitals(request, pageable)));
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get hospital details by UUID")
    public ResponseEntity<ApiResponse<HospitalDetailDto>> getHospital(@PathVariable String uuid) {
        return ResponseEntity.ok(ApiResponse.success(hospitalService.getHospitalByUuid(uuid)));
    }

    @GetMapping("/featured")
    @Operation(summary = "Get featured hospitals")
    public ResponseEntity<ApiResponse<List<HospitalSummaryDto>>> getFeaturedHospitals(
        @RequestParam(required = false) String state
    ) {
        return ResponseEntity.ok(ApiResponse.success(hospitalService.getFeaturedHospitals(state)));
    }

    @GetMapping("/nearby")
    @Operation(summary = "Find nearby hospitals")
    public ResponseEntity<ApiResponse<List<HospitalSummaryDto>>> getNearbyHospitals(
        @RequestParam Double lat,
        @RequestParam Double lng,
        @RequestParam(defaultValue = "10") Double radiusKm,
        @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(ApiResponse.success(hospitalService.findNearby(lat, lng, radiusKm, limit)));
    }

    @GetMapping("/emergency")
    @Operation(summary = "Find nearest emergency hospitals")
    public ResponseEntity<ApiResponse<List<HospitalSummaryDto>>> getEmergencyHospitals(
        @RequestParam Double lat,
        @RequestParam Double lng,
        @RequestParam(defaultValue = "20") Double radiusKm
    ) {
        return ResponseEntity.ok(ApiResponse.success(hospitalService.findEmergencyHospitals(lat, lng, radiusKm)));
    }

    @GetMapping("/{uuid}/doctors")
    @Operation(summary = "Get doctors at a hospital")
    public ResponseEntity<ApiResponse<Page<DoctorSummaryDto>>> getHospitalDoctors(
        @PathVariable String uuid,
        @RequestParam(required = false) String specialty,
        @PageableDefault(size = 12) Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(hospitalService.getHospitalDoctors(uuid, specialty, pageable)));
    }

    @GetMapping("/{uuid}/reviews")
    @Operation(summary = "Get reviews for a hospital")
    public ResponseEntity<ApiResponse<Page<ReviewDto>>> getHospitalReviews(
        @PathVariable String uuid,
        @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(hospitalService.getHospitalReviews(uuid, pageable)));
    }

    @PostMapping("/{uuid}/reviews")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Submit a review for a hospital")
    public ResponseEntity<ApiResponse<ReviewDto>> submitReview(
        @PathVariable String uuid,
        @Valid @RequestBody ReviewRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(hospitalService.submitReview(uuid, request)));
    }

    @GetMapping("/{uuid}/waiting-time")
    @Operation(summary = "Get current estimated waiting time")
    public ResponseEntity<ApiResponse<WaitingTimeDto>> getWaitingTime(@PathVariable String uuid) {
        return ResponseEntity.ok(ApiResponse.success(hospitalService.getWaitingTime(uuid)));
    }

    @PostMapping("/{uuid}/waiting-time")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Report waiting time")
    public ResponseEntity<ApiResponse<Void>> reportWaitingTime(
        @PathVariable String uuid,
        @RequestBody WaitingTimeRequest request
    ) {
        hospitalService.reportWaitingTime(uuid, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    // ---- Admin endpoints ----

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @Operation(summary = "Create a new hospital listing")
    public ResponseEntity<ApiResponse<HospitalDetailDto>> createHospital(
        @Valid @RequestBody HospitalCreateRequest request
    ) {
        return ResponseEntity.status(201).body(ApiResponse.success(hospitalService.createHospital(request)));
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasRole('HOSPITAL_ADMIN') or hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @Operation(summary = "Update hospital details")
    public ResponseEntity<ApiResponse<HospitalDetailDto>> updateHospital(
        @PathVariable String uuid,
        @Valid @RequestBody HospitalUpdateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(hospitalService.updateHospital(uuid, request)));
    }

    @PatchMapping("/{uuid}/verify")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @Operation(summary = "Verify a hospital listing")
    public ResponseEntity<ApiResponse<Void>> verifyHospital(@PathVariable String uuid) {
        hospitalService.verifyHospital(uuid);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/states")
    @Operation(summary = "Get all states with hospital counts")
    public ResponseEntity<ApiResponse<List<StateStatsDto>>> getStateStats() {
        return ResponseEntity.ok(ApiResponse.success(hospitalService.getStateStats()));
    }
}
