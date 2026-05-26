package com.brayan.salesflow.controller;

import com.brayan.salesflow.dto.OpportunityRequest;
import com.brayan.salesflow.dto.OpportunityResponse;
import com.brayan.salesflow.entity.OpportunityStatus;
import com.brayan.salesflow.service.OpportunityService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/api/opportunities")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Oportunidades", description = "Gestión de oportunidades de venta")
public class OpportunityController {

    private final OpportunityService opportunityService;

    @PostMapping
    @Operation(summary = "Crear cliente", description = "Registra un nuevo cliente o empresa en el CRM")
    public ResponseEntity<OpportunityResponse> create(@Valid @RequestBody OpportunityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(opportunityService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<OpportunityResponse>> findAll() {
        return ResponseEntity.ok(opportunityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OpportunityResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(opportunityService.findById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OpportunityResponse>> findByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(opportunityService.findByCustomerId(customerId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OpportunityResponse>> findByStatus(@PathVariable OpportunityStatus status) {
        return ResponseEntity.ok(opportunityService.findByStatus(status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OpportunityResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody OpportunityRequest request
    ) {
        return ResponseEntity.ok(opportunityService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        opportunityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}