package com.brayan.salesflow.dto;

import com.brayan.salesflow.entity.OpportunityStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpportunityResponse {

    private Long id;
    private String title;
    private String description;
    private BigDecimal estimatedAmount;
    private OpportunityStatus status;
    private LocalDate expectedCloseDate;
    private Long customerId;
    private String customerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}