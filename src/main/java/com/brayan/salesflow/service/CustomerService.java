package com.brayan.salesflow.service;

import com.brayan.salesflow.dto.CustomerRequest;
import com.brayan.salesflow.dto.CustomerResponse;
import com.brayan.salesflow.entity.Customer;
import com.brayan.salesflow.entity.CustomerStatus;
import com.brayan.salesflow.exception.BadRequestException;
import com.brayan.salesflow.exception.ResourceNotFoundException;
import com.brayan.salesflow.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponse create(CustomerRequest request) {
        if (request.getRuc() != null && !request.getRuc().isBlank() && customerRepository.existsByRuc(request.getRuc())) {
            throw new BadRequestException("El RUC ya se encuentra registrado");
        }

        Customer customer = Customer.builder()
                .companyName(request.getCompanyName())
                .ruc(request.getRuc())
                .industry(request.getIndustry())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .status(request.getStatus() != null ? request.getStatus() : CustomerStatus.PROSPECT)
                .build();

        Customer saved = customerRepository.save(customer);
        return mapToResponse(saved);
    }

    public List<CustomerResponse> findAll() {
        return customerRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public CustomerResponse findById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        return mapToResponse(customer);
    }

    public List<CustomerResponse> searchByCompanyName(String companyName) {
        return customerRepository.findByCompanyNameContainingIgnoreCase(companyName).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<CustomerResponse> findByStatus(CustomerStatus status) {
        return customerRepository.findByStatus(status).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public CustomerResponse update(Long id, CustomerRequest request) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        if (request.getRuc() != null && !request.getRuc().isBlank() && customerRepository.existsByRuc(request.getRuc()) && !request.getRuc().equals(customer.getRuc())) {
            throw new BadRequestException("El RUC ya se encuentra registrado");
        }

        customer.setCompanyName(request.getCompanyName());
        customer.setRuc(request.getRuc());
        customer.setIndustry(request.getIndustry());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        customer.setStatus(request.getStatus() != null ? request.getStatus() : customer.getStatus());

        Customer saved = customerRepository.save(customer);
        return mapToResponse(saved);
    }

    public void delete(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        customerRepository.delete(customer);
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .companyName(customer.getCompanyName())
                .ruc(customer.getRuc())
                .industry(customer.getIndustry())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .status(customer.getStatus())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }

}
