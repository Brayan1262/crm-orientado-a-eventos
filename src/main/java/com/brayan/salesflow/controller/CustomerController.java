package com.brayan.salesflow.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.brayan.salesflow.dto.CustomerRequest;
import com.brayan.salesflow.dto.CustomerResponse;
import com.brayan.salesflow.entity.CustomerStatus;
import com.brayan.salesflow.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Clientes", description = "Gestión de clientes y empresas dentro del CRM")
public class CustomerController {

    private final CustomerService customerService;
    @PostMapping
    @Operation(summary = "Crear cliente", description = "Registra un nuevo cliente o empresa en el CRM")
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<CustomerResponse> findAll() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public CustomerResponse findById(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @GetMapping("/search")
    public List<CustomerResponse> searchByCompanyName(@RequestParam("companyName") String companyName) {
        return customerService.searchByCompanyName(companyName);
    }

    @GetMapping("/status/{status}")
    public List<CustomerResponse> findByStatus(@PathVariable CustomerStatus status) {
        return customerService.findByStatus(status);
    }

    @PutMapping("/{id}")
    public CustomerResponse update(@PathVariable Long id, @Valid @RequestBody CustomerRequest request) {
        return customerService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
