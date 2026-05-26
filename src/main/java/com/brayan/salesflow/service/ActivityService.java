package com.brayan.salesflow.service;

import com.brayan.salesflow.dto.ActivityRequest;
import com.brayan.salesflow.dto.ActivityResponse;
import com.brayan.salesflow.entity.Activity;
import com.brayan.salesflow.entity.Customer;
import com.brayan.salesflow.entity.Opportunity;
import com.brayan.salesflow.exception.ResourceNotFoundException;
import com.brayan.salesflow.repository.ActivityRepository;
import com.brayan.salesflow.repository.CustomerRepository;
import com.brayan.salesflow.repository.OpportunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final CustomerRepository customerRepository;
    private final OpportunityRepository opportunityRepository;

    public ActivityResponse create(ActivityRequest request) {
        Customer customer = null;
        Opportunity opportunity = null;

        if (request.getCustomerId() != null) {
            customer = customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        }

        if (request.getOpportunityId() != null) {
            opportunity = opportunityRepository.findById(request.getOpportunityId())
                    .orElseThrow(() -> new ResourceNotFoundException("Oportunidad no encontrada"));
        }

        Activity activity = Activity.builder()
                .type(request.getType())
                .subject(request.getSubject())
                .description(request.getDescription())
                .activityDate(request.getActivityDate())
                .completed(request.getCompleted() != null ? request.getCompleted() : false)
                .customer(customer)
                .opportunity(opportunity)
                .build();

        Activity savedActivity = activityRepository.save(activity);

        return mapToResponse(savedActivity);
    }

    public List<ActivityResponse> findAll() {
        return activityRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ActivityResponse findById(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actividad no encontrada"));

        return mapToResponse(activity);
    }

    public List<ActivityResponse> findByCustomerId(Long customerId) {
        boolean customerExists = customerRepository.existsById(customerId);

        if (!customerExists) {
            throw new ResourceNotFoundException("Cliente no encontrado");
        }

        return activityRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<ActivityResponse> findByOpportunityId(Long opportunityId) {
        boolean opportunityExists = opportunityRepository.existsById(opportunityId);

        if (!opportunityExists) {
            throw new ResourceNotFoundException("Oportunidad no encontrada");
        }

        return activityRepository.findByOpportunityId(opportunityId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<ActivityResponse> findPendingActivities() {
        return activityRepository.findByCompletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ActivityResponse update(Long id, ActivityRequest request) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actividad no encontrada"));

        Customer customer = null;
        Opportunity opportunity = null;

        if (request.getCustomerId() != null) {
            customer = customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        }

        if (request.getOpportunityId() != null) {
            opportunity = opportunityRepository.findById(request.getOpportunityId())
                    .orElseThrow(() -> new ResourceNotFoundException("Oportunidad no encontrada"));
        }

        activity.setType(request.getType());
        activity.setSubject(request.getSubject());
        activity.setDescription(request.getDescription());
        activity.setActivityDate(request.getActivityDate());
        activity.setCompleted(request.getCompleted() != null ? request.getCompleted() : activity.getCompleted());
        activity.setCustomer(customer);
        activity.setOpportunity(opportunity);

        Activity updatedActivity = activityRepository.save(activity);

        return mapToResponse(updatedActivity);
    }

    public ActivityResponse complete(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actividad no encontrada"));

        activity.setCompleted(true);

        Activity completedActivity = activityRepository.save(activity);

        return mapToResponse(completedActivity);
    }

    public void delete(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actividad no encontrada"));

        activityRepository.delete(activity);
    }

    private ActivityResponse mapToResponse(Activity activity) {
        return ActivityResponse.builder()
                .id(activity.getId())
                .type(activity.getType())
                .subject(activity.getSubject())
                .description(activity.getDescription())
                .activityDate(activity.getActivityDate())
                .completed(activity.getCompleted())
                .customerId(activity.getCustomer() != null ? activity.getCustomer().getId() : null)
                .customerName(activity.getCustomer() != null ? activity.getCustomer().getCompanyName() : null)
                .opportunityId(activity.getOpportunity() != null ? activity.getOpportunity().getId() : null)
                .opportunityTitle(activity.getOpportunity() != null ? activity.getOpportunity().getTitle() : null)
                .createdAt(activity.getCreatedAt())
                .updatedAt(activity.getUpdatedAt())
                .build();
    }
}