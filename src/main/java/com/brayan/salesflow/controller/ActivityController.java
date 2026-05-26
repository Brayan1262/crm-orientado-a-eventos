package com.brayan.salesflow.controller;

import com.brayan.salesflow.dto.ActivityRequest;
import com.brayan.salesflow.dto.ActivityResponse;
import com.brayan.salesflow.service.ActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponse> create(@Valid @RequestBody ActivityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(activityService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> findAll() {
        return ResponseEntity.ok(activityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(activityService.findById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ActivityResponse>> findByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(activityService.findByCustomerId(customerId));
    }

    @GetMapping("/opportunity/{opportunityId}")
    public ResponseEntity<List<ActivityResponse>> findByOpportunityId(@PathVariable Long opportunityId) {
        return ResponseEntity.ok(activityService.findByOpportunityId(opportunityId));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<ActivityResponse>> findPendingActivities() {
        return ResponseEntity.ok(activityService.findPendingActivities());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ActivityRequest request
    ) {
        return ResponseEntity.ok(activityService.update(id, request));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<ActivityResponse> complete(@PathVariable Long id) {
        return ResponseEntity.ok(activityService.complete(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        activityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}