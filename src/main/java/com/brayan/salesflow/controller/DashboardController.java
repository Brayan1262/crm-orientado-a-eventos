package com.brayan.salesflow.controller;

import com.brayan.salesflow.dto.DashboardResponse;
import com.brayan.salesflow.service.DashboardService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Dashboard", description = "Gestión del dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
     @Operation(summary = "Obtener dashboard CRM", description = "Devuelve indicadores de clientes, oportunidades, actividades y montos comerciales")
    public ResponseEntity<DashboardResponse> getDashboard() {
        return ResponseEntity.ok(dashboardService.getDashboard());
    }
}