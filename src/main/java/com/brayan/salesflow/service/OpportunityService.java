package com.brayan.salesflow.service;

import com.brayan.salesflow.dto.OpportunityRequest;
import com.brayan.salesflow.dto.OpportunityResponse;
import com.brayan.salesflow.entity.Customer;
import com.brayan.salesflow.entity.Opportunity;
import com.brayan.salesflow.entity.OpportunityStatus;
import com.brayan.salesflow.exception.ResourceNotFoundException;
import com.brayan.salesflow.repository.CustomerRepository;
import com.brayan.salesflow.repository.OpportunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpportunityService {

    private final OpportunityRepository opportunityRepository;
    private final CustomerRepository customerRepository;

    public OpportunityResponse create(OpportunityRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        Opportunity opportunity = Opportunity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .estimatedAmount(request.getEstimatedAmount())
                .status(request.getStatus() != null ? request.getStatus() : OpportunityStatus.NEW)
                .expectedCloseDate(request.getExpectedCloseDate())
                .customer(customer)
                .build();

        Opportunity savedOpportunity = opportunityRepository.save(opportunity);

        return mapToResponse(savedOpportunity);
    }

    public List<OpportunityResponse> findAll() {
        return opportunityRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public OpportunityResponse findById(Long id) {
        Opportunity opportunity = opportunityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Oportunidad no encontrada"));

        return mapToResponse(opportunity);
    }

    public List<OpportunityResponse> findByCustomerId(Long customerId) {
        boolean customerExists = customerRepository.existsById(customerId);

        if (!customerExists) {
            throw new ResourceNotFoundException("Cliente no encontrado");
        }

        return opportunityRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<OpportunityResponse> findByStatus(OpportunityStatus status) {
        return opportunityRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public OpportunityResponse update(Long id, OpportunityRequest request) {
        Opportunity opportunity = opportunityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Oportunidad no encontrada"));

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        opportunity.setTitle(request.getTitle());
        opportunity.setDescription(request.getDescription());
        opportunity.setEstimatedAmount(request.getEstimatedAmount());
        opportunity.setExpectedCloseDate(request.getExpectedCloseDate());
        opportunity.setCustomer(customer);

        if (request.getStatus() != null) {
            opportunity.setStatus(request.getStatus());
        }

        Opportunity updatedOpportunity = opportunityRepository.save(opportunity);

        return mapToResponse(updatedOpportunity);
    }

    public void delete(Long id) {
        Opportunity opportunity = opportunityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Oportunidad no encontrada"));

        opportunityRepository.delete(opportunity);
    }

    private OpportunityResponse mapToResponse(Opportunity opportunity) {
        return OpportunityResponse.builder()
                .id(opportunity.getId())
                .title(opportunity.getTitle())
                .description(opportunity.getDescription())
                .estimatedAmount(opportunity.getEstimatedAmount())
                .status(opportunity.getStatus())
                .expectedCloseDate(opportunity.getExpectedCloseDate())
                .customerId(opportunity.getCustomer().getId())
                .customerName(opportunity.getCustomer().getCompanyName())
                .createdAt(opportunity.getCreatedAt())
                .updatedAt(opportunity.getUpdatedAt())
                .build();
    }
}